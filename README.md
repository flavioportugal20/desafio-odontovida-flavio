# Desafio Odontovida

### 1) Você deverar construir um [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) utilizando a seguinte estrutura:
  ```
  CREATE TABLE funcionario (
    idFuncionario INT(11) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    dataNascimento DATE NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    salario DOUBLE(10, 2) NOT NULL,
    PRIMARY KEY (idFuncionario)
  )
  ```
  ###### Regras para o CRUD
  - Campo nome não pode conter números
  - Todos os funcionários deve ter uma idade mínima de 18 anos
  - Campo CPF deve seguir o seguinte padrão "000.000.000-00" e deve ser validado.
  - Campo Salário não deve conter um valor negativo.
  
  ###### Utilizando as seguintes Techs
  - **IDE**: [Eclipse IDE](https://www.eclipse.org/downloads/packages/release/2020-06/r/eclipse-ide-enterprise-java-developers)
  - **Linguagem**: [Java 1.8](https://www.oracle.com/java/technologies/java-se-glance.html)
  - **Banco de Dados**: [MariaDB 5.5.41](https://mariadb.org/)
  - **ORM**: [hibernate 5.2.4.Final](https://hibernate.org/orm/)
  - **Biblioteca de Componentes**: [Primefaces 6.0](https://www.primefaces.org/showcase/)
  - **Servidor de Aplicação**: [Wildfly 15](https://www.wildfly.org/)

### 2) Documente seu projeto 

### 3) Publique seu projeto em sua conta do Github e envie o link para o email sistemas.odontovida@gmail.com

### DICAS E RECURSOS

Nesse repositorio contêm um projeto Default. 
para iniciar basta adicionar o driver do mysql no wildfly e configurar o DataSource no arquivo de configuração do servidor de aplicação ```wildfly-15.0.1.Final\standalone\configuration\standalone.xml```
Exemplo:
```
<datasource jta="true" jndi-name="java:jboss/datasources/Connection-DS" pool-name="Connection" enabled="true" use-ccm="false">
  <connection-url>jdbc:mysql://localhost:3306/NOME_DO_SCHEMA?autoReconnect=true</connection-url>
  <driver-class>com.mysql.jdbc.Driver</driver-class>
  <driver>mysql</driver>
  <security>
    <user-name>NOME_DO_USUARIO</user-name>
    <password>SENHA_DO_USUARIO</password>
  </security>
  <validation>
    <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
    <background-validation>true</background-validation>
    <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
  </validation>
</datasource>
```


