package br.com.itau.sg2.ouvidoria.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.itau.integrador.dataprev.dto.Ocorrencia;

public class OcorrenciaItemReader implements ItemReader<Ocorrencia>{

    private final String apiUrl;
    private final RestTemplate restTemplate;
    private int referenceNextIndex;
    private List<Ocorrencia> data;
    
    public OcorrenciaItemReader(final String apiUrl, final RestTemplate restTemplate){
    	this.apiUrl = apiUrl;
    	this.restTemplate = restTemplate;
    }
    
    private List<Ocorrencia> fetchOcorrenciaDataFromDataprev() {
    	ResponseEntity<List<Ocorrencia>> response = restTemplate.exchange(
    			  this.apiUrl,
    			  HttpMethod.GET,
    			  null,
    			  new ParameterizedTypeReference<List<Ocorrencia>>(){});
        List<Ocorrencia> ocorrencias = response.getBody();
        return ocorrencias;
    }
    
	@Override
	public Ocorrencia read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(data == null){
		 data = fetchOcorrenciaDataFromDataprev();
		}
		if(referenceNextIndex < data.size()){
			final Ocorrencia ocorrencia = data.get(referenceNextIndex);
			referenceNextIndex++;
			return ocorrencia;
		}
		return null;
	}

}
