# Zap Zup - Application
## Introdução
Este projeto foi criado como um desafio da iniciativa MasterDevs. 
Tem por objetivo criar um sistema de chat por mensagens.

# Documentação
Para acessar a documentação, suba o projeto e acesse a url:
```
http://localhost:8080/zapzupapplication/swagger-ui.html
```

## Ambiente de desenvolvimento
Tecnologias utilizadas:
* Java 11
* Kotlin
* Maven
* JUnit
* Mockito
* Swagger
* Liquibase
* Docker
* Docker Compose
* PostgreSql
* Github Actions
 
Antes de rodar o projeto no ambiente de desenvolvimento, é necessário executar o seguinte comando
para instalar as dependencias necessárias:
```
mvn clean install
```
## Build
Todos os comandos listados a seguir, devem ser executados a partir da raíz do projeto.

Para compilar o projeto, utilize:
```
mvn build
```

Para a execução dos testes, utilize:
```
mvn test
```

Para execução da aplicação, utilize os seguintes comandos respectivamente:
```
docker-compose -f resource/docker-compose.yml up
```

```
mvn spring-boot:run
```
.
