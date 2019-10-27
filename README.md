# Exemplo usando ReactJS + react-google-maps + Spring Boot + PostgreSQL + Swagger



## Installation ReactJS

Seguem os passos para deploy local do React

Passo 1
```bash
cd .\itau\itau-ui\
```

Paso 2
```bash
npm install
```

Passo 3

Crie na raiz um arquivo chamado: .env.local, para adicionar a KEY do Google, preencha com o seguinte:
```bash
REACT_APP_GOOGLE_KEY={{SUA_KEY}}
```

Passo 4
```bash
npm start
```

## Installation Spring Boot

Passo 1
```bash
cd .\itau\
```

Passo 2
```bash
mvn clean install -DskipTests
```

Passo 3

Alterar o arquivo application-dev.properties com as informações do seu banco de dados.
```bash
spring.profiles.active=dev
spring.jpa.database=
spring.datasource.platform=
spring.datasource.url=jdbc:postgresql://localhost:5432/xxxx
spring.datasource.username=xxxx
spring.datasource.password=xxxx
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

```

Passo 4
```bash
cd .\itau\itau-config\
```

Passo 5
```bash
mvn spring-boot:run -DskipTests
```

## Usage

```python
import foobar

foobar.pluralize('word') # returns 'words'
foobar.pluralize('goose') # returns 'geese'
foobar.singularize('phenomena') # returns 'phenomenon'
```
## Swagger

http://localhost:8080/swagger-ui.html


## Cobertura e Testes unitários

![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/coverage_junit.png?raw=true "Cobertura de testes e Testes unitários")

Please make sure to update tests as appropriate.

## Evidências
Chrome
![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/browser_chrome.png?raw=true "Browser Chrome")

Edge
![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/browser_edge.png?raw=true "Browser Edge")

Android
![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/browser_chrome_android.jpg?raw=true "Browser Chrome Android")

## Tabelas
TB_AGENCY
![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/tb_agency.png?raw=true "Browser Chrome")

TB_COLLECT
![Alt text](https://github.com/ribasbarros/itau/blob/master/itau-doc/tb_collect.png?raw=true "Browser Chrome")
