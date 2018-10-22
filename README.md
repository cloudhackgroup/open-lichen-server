# Open Lichen Server
Los liquenes son un buen bioindicador para estimar la calidad del aire

##Global (Alfa) version
Se encuentra la version actual corriendo en un cloudhost en Heroku

`https://openlichen.herokuapp.com`

##Standalone

###Warm up
Es un servidor API-REST basado en `Spring Boot 2.0.6`, y como motor de base
de datos `MongoDB`, para poder correrlo en su entorno primero es necesario 
indicarle la ubicacion de la base de datos

Estas configuraciones estan en el `application.properties` ubicado en 
(src/main/resources/)

las configuraciones pueden ser escritas conforme al apendice de sintaxis de
los archivos de configuracion en :

https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html#common-application-properties

Luego basta con setear como ejecutable el `gradlew` con `$ chmod +x gradlew`  
y para correr el servidor
`$ ./gradlew bootRun`







