# spring-batch-postgresql

Este projeto utiliza como base o exemplo fornecido pela documentação oficial do Spring, "Getting Started Batch Service".

https://spring.io/guides/gs/batch-processing/#initial

* Pré-requisitos:
- Conectividade com uma base de dados PostgreSQL;

Para facilitar a instalação e configuração, foi utilizado a aplicação para MACOS chamada PostgreApp (https://postgresapp.com/).

Siga os passos de instalação e depois configure corretamente o arquivo "application.properties", com os detalhes de autenticação para conectar com a instância da base de dados no seu projeto.

```
spring.datasource.initialization-mode=always
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/database
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false
```
