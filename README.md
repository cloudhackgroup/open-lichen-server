# Open Lichen Server
Los liquenes son un buen bioindicador para estimar la calidad del aire

## Global (Alfa) version
Se encuentra la version actual corriendo en un cloudhost en Heroku

`https://openlichen.herokuapp.com`

## Standalone

### Warm up
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


### Usage

Una vez el servidor ha arrancado las siguientes son las llamadas de la API

#### Api-v2 
El nuevo modelo propuesto refleja :
* Reporte = Un host (arbol, piedra, etc)
* Sample = Unico sample, donde se indican las especies
* Especie = especie en dicho sample

Basicamente las llamadas ahora se realizan a `/{function}v2` y el modelo
ahora cambio a :
```
 {
    "reportId": String - random(),
    "lat": double,
    "lng": double,
    "datetime": int,
    "samples":{ // SAMPLE UNICO !!!
        "Species 1": {
          "milimetersCovered": int,
          "tilesCovered": int
        },
        "Species 2": {
          "milimetersCovered": int,
          "tilesCovered": int
        },
        ...
      }
  }
```
Esta diferencia realiza que cada reporte es equivalente a 1 host (arbol, pidera, etc) 
luego a implementar los calculos, deberan realizarse en un area determinada, provista
por el usuario

#### Api-v1
El modelo propuesto refleja :
* Reporte = Una superficie 
* Sample = Un host (arbol, piedra, etc)
* Especie = especie en dicho sample

- POST  `/report` : esto guarda un nuevo reporte, el modelo del reporte viene
json con el siguiente formato

```
 {
    "reportId": String - random(),
    "lat": double,
    "lng": double,
    "datetime": int,
    "samples": [
      { //Sample 1
        "Species 1": {
          "milimetersCovered": int,
          "tilesCovered": int
        },
        "Species 2": {
          "milimetersCovered": int,
          "tilesCovered": int
        }
        ...
      }, 
      { //Sample 2
        "Species n": {
          "milimetersCovered": int,
          "tilesCovered": int
        }
        ...
      }
    ]
  }
```
Respuesta : Los indices actualizados de esas muestras
```
{
  iapf : int,
  iapq : int
}
```
 - GET `/report?last=:n` : Obtiene los ultimos n reportes subidos donde n
es un numero menor a 1000. Las trae con el mismo formate del reporte.

 - GET `/atmos` : Obtiene los ultimos indices de IAP (segun el metodo Q y F)
 con el mismo formato de respuesta que el `/report`





