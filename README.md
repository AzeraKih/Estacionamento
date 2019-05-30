# Estacionamento

<p>You need to configure Build path whit: </p>
<p>JSON JAVA - https://github.com/stleary/JSON-java</p>
<p>ORACLE OJDC8 - https://www.oracle.com/technetwork/database/application-development/jdbc/downloads/jdbc-ucp-183-5013470.html</p>
<p>Eclipse link 2.5.0</p>

<p>Server: Tomcat 7.0</p>

<p>After clone the project you need to configure your persistence.xml in folder <i>src/META-INF/persistence.xml</i>, with te following content:</p>

```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="PU_ESTACIONAMENTO" transaction-type="RESOURCE_LOCAL">
      <class>br.com.mvc.model.Estadia</class>
      <class>br.com.mvc.model.Marca</class>
      <class>br.com.mvc.model.Modelo</class>
      <class>br.com.mvc.model.Proprietario</class>
      <class>br.com.mvc.model.TabelaTipo</class>
      <class>br.com.mvc.model.TipoEstadia</class>
      <class>br.com.mvc.model.Veiculo</class>
      <properties>
        <property name="show_sql" value="true"/>
        <property name="javax.persistence.schema-generation.database.action" value="none"/>
        <property name="javax.persistence.jdbc.url" value="jdbc:oracle:thin:@HOST:1521:SID"/>
        <property name="javax.persistence.jdbc.password" value="PWD"/>
        <property name="javax.persistence.jdbc.driver" value="oracle.jdbc.OracleDriver"/>
        <property name="javax.persistence.jdbc.user" value="OWNER-DATABASE"/>
        <property name="javax.persistence.schema-generation.create-database-schemas" value="false"/>
        <property name="eclipselink.id-validation" value="NONE"/> 
      </properties>
    </persistence-unit>
  </persistence>
```
