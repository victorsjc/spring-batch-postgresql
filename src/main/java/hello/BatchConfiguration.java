package hello;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import br.com.thinkidea.integrador.dataprev.dto.Ocorrencia;
import br.com.thinkidea.ouvidoria.reader.OcorrenciaItemReader;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${api.url}")
    private String apiUrl;
    
    @Bean
    public ItemReader<Ocorrencia> readerOcorrencia(final RestTemplate restTemplate)
    {
    	return new OcorrenciaItemReader(this.apiUrl,restTemplate);
    }
    
    @Bean
    public RestTemplate restTemplate(){
    	return new RestTemplate();
    }
    
    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
            .delimited()
            .names(new String[]{"firstName", "lastName"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }})
            .build();
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    /*@Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }*/

    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
    // end::jobstep[]
    @Bean
    public JdbcBatchItemWriter<Ocorrencia> writerOcorrencia(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Ocorrencia>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO ocorrencias (id, descricao, data_criacao) VALUES (:id, :descricao, :data_criacao)")
            .dataSource(dataSource)
            .build();
    }
    
    @Bean
    public Job importOcorrenciasJob(JobCompletionNotificationListener listener, Step step0) {
        return jobBuilderFactory.get("importOcorrenciaJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step0)
            .end()
            .build();
    }
    
    @Bean
    public Step step0(final RestTemplate restTemplate, final JdbcBatchItemWriter<Ocorrencia> writer0) {
        return stepBuilderFactory.get("step0")
            .<Ocorrencia, Ocorrencia> chunk(5)
            .reader(readerOcorrencia(restTemplate))
            .writer(writer0)
            .build();
    }
}
