# UTILIDADES API REDMINE

Desde este servicio tenemos acceso a varias utilidades relacionadas con la integración a distintos Redmines a través de la API Java de Redmine

1. Imputar horas de distintos usuarios en Gesproy. Se explica con más detalle en la sección 'Imputator Gesproy'
2. Añadir una lista de peticiones como peticiones relacionadas con una en concreto, también en Gesproy. Se explica con más detalle en la sección 'Relacionator Gesproy'
3. Estimar peticiones en Gesproy. Se explica con más detalle en la sección 'Estimator Gesproy'
4. Imputar horas en el Redmine de CTEICU (Redmine SETA). Se explica con más detalle en la sección 'Imputator SETA'

# IMPUTATOR GESPROY

Servicio para la imputación de horas en Gesproy desde una hoja excel. Depende de un servicio de cloud config que es de donde lee la configuración para conectarse a Gesproy.
Por defecto se ejecuta en el puerto 8088

1. Una vez arrancado ir a la URL http://localhost:8088 y seleccionar la opción 'Imputaciones Gesproy' o bien ir directamente a http://localhost:8088/gesproy/indexindv
2. Pinchar en Seleccionar archivo y elegir la hoja de cálculo con la hoja IMPUTACIONES en el formato adecuado. En la siguiente hoja hay un [ejemplo](src/test/resources/imputacion.modelo.xlsx)
3. Pichar en el botón imputar
4. Mirar en el log

## Requisitos previos

1. Tener acceso por red a Gesproy (Redmine de educamosCLM). Para ello habrá que estar dentro de la red del organismo o tener VPN de la JCCM para acceder desde fuera

## Estructura de la hoja excel para imputaciones

La hoja excel debe tener una hoja llamada ESTIMACIONES que contiene una tabla con cinco columnas:

1. PETICION. Número de la HU/tarea en Gesproy en la que se va a imputar
2. PERSONA. Persona para la que se hace la imputación. Contiene el id del ldap (id de Gesproy) de la persona. El mapeo entre este id y las personas del equipo lo tenéis en la siguiente hoja de google: https://docs.google.com/spreadsheets/d/1hDE1l6Nha3siXm0cUS1Zgj6Ey0js-YiubtF2CmFI-Bk/edit#gid=1820605664
3. FECHA. Fecha de la imputación en formato DD/MM/AAAA
4. HORAS. Horas a imputar. Para los decimales utilizamos coma
5. ACTIVIDAD. Identificador de la actividad para la imputación. La relación entre estos códigos y la descripción de la actividad la tenemos en la sección 'Correspondencia de códigos de actividades'
6. COMENTARIO. Es el único campo opcional. Ahí se pone lo que va a salir de comentario en la imputación

## Correspondencia de códigos de actividades

| Actividad  | Código |
| ------------- | ------------- |
| Desarrollo | 9 |
| Analisis | 16 |
| Actas e informes | 31 |
| Coordinación y planificación | 34 |
| Definición de propuestas | 15 |
| Deteccion del error | 13 |
| Diseño | 8 |
| Formación | 29 |
| Documentación | 10 |
| Estudios | 14 |
| Pruebas | 12 |
| Reunión | 30 |
| Tratamiento directo de datos | 17 |

## TODOs

1. Mostrar informe de ejecución en el navegador
2. Cerrar una versión inicial del README.MD

# RELACIONATOR GESPROY
Servicio para relacionar una lista de peticiones (a través de sus ids separados por comas) con una petición determinada.

1. Una vez arrancado ir a la URL http://localhost:8088 y seleccionar la opción 'Imputaciones Gesproy' o bien ir directamente a http://localhost:8088/gesproy/indexindv
2. Poner el id de la petición en donde se van a relacionar la lista de peticiones
3. Poner la lista de ids de peticiones que se relacionan con la anterior, separadas por comas
4. Pinchar en el botón relacionar
5. Mirar en el log

## Requisitos previos

1. Tener acceso por red a Gesproy (Redmine de educamosCLM). Para ello habrá que estar dentro de la red del organismo o tener VPN de la JCCM para acceder desde fuera

## TODOs

1. Mostrar informe de ejecución en el navegador
2. Cerrar una versión inicial del README.MD

# ESTIMATOR GESPROY
Servicio para realizar estimaciones en bloque, en base a la información que se encuentra en una hoja excel.

1. Una vez arrancado ir a la URL http://localhost:8088 y seleccionar la opción 'Estimaciones Gesproy' o bien ir directamente a http://localhost:8088/gesproy/indexest
2. Pinchar en Seleccionar archivo y elegir la hoja de cálculo con la hoja IMPUTACIONES en el formato adecuado
3. Poner la lista de ids de peticiones que se relacionan con la anterior, separadas por comas
3. Pinchar en el botón estimar
4. Mirar en el log

## Requisitos previos

1. Tener acceso por red a Gesproy (Redmine de educamosCLM). Para ello habrá que estar dentro de la red del organismo o tener VPN de la JCCM para acceder desde fuera

## TODOs

1. Mostrar informe de ejecución en el navegador
2. Poner el formato de la hoja excel para estimaciones

# IMPUTATOR SETA

Servicio para la imputación de horas en SETA desde una hoja excel. Por defecto se ejecuta en el puerto 8088

1. Una vez arrancado ir a la URL http://localhost:8088 y seleccionar la opción 'Imputaciones SETA - CTEICU' o bien ir directamente a http://localhost:8088/imputaciones/index
2. Elegir el Redmine destino para las imputaciones. Aunque se muestre un desplegable con todos los Redmines registrados en la herramienta, en este caso siempre seleccionaremos el Redmine SETA.
3. Pinchar en Seleccionar archivo y elegir la hoja de cálculo con la hoja IMPUTACIONES en el formato adecuado. En la hoja src/test/resources/Calculadora12.xlsx hay un ejemplo
3. Pichar en el botón imputar
4. Mirar en el log

## Requisitos previos

1. Tener acceso por red al SETA (Redmine de CECEU). Para ello habrá que estar dentro de la red de la Consejería o tener VPN para acceder desde fuera (túnel Catalana o vpn de la propia Consejería)

## Para cuando cambian el certificado https de SETA

Cuando se produce el error “PKIX path building failed” and “unable to find valid certification path to requested target” al intentar hacer las imputaciones hay que meter el nuevo certificado de SETA en el keystore de Java

1. Descargar el certificado pinchando en el candado que está justo a la izquierda de la URL del navegador (en formato X.509 .cer)
2. Localizar el fichero cacerts de la máquina virtual java que ejecuta el programa (C:\java\jdk8_211\jre\lib\security\cacerts por ejemplo)
3. Importar el certificado en dicho fichero:

```shell
keytool -import -alias setaYYYYMM -keystore C:\java\jdk8_211\jre\lib\security\cacerts -file certificadodescargado.cer -storepass changeit -noprompt
```

4. Comprobar que el certificado está importado:

```shell
keytool -list -v -alias setaYYYYMM -storepass changeit
```

El almacén de certificados que usa la aplicación debe colocarse en src/main/resources.

Página de referencia (en inglés): [“PKIX path building failed”](https://stackoverflow.com/questions/21076179/pkix-path-building-failed-and-unable-to-find-valid-certification-path-to-requ)

## TODOs

1. Mostrar informe de ejecución en el navegador


# A PARTIR DE AQUÍ NO VALE!

Todo lo siguiente no vale. Se deja por tener una referencia para modificarlo y pasarlo al caso de este proyecto

## Ejecución de la imagen

```shell
docker run -d -e entorno=I -e DOCKER_TIMEZONE=Europe/Madrid -p 8080:8080 --name yisas-gprdata --rm yisasthemanuel/gprdata:1.4.3.RELEASE
```

** Banner generado con la fuente alligator2 (https://devops.datenkollektiv.de/banner.txt/index.html)

Lo siguente es genérico y hay que adaptarlo

## Introducción

Proyecto básico que modela las características básicas de ejecución, compilación, empaquetado y monitorización, entre otros.

## Desarrollo

## Prerequisitos

* Un IDE con soporte al proyecto Lombok (<https://projectlombok.org/>): Eclipse, IntelliJ, Visual Studio Code.
* La JVM OpenJ9 instalada (<https://adoptopenjdk.net/installation.html#linux-pkg>, <https://adoptopenjdk.net/releases.html?variant=openjdk8&jvmVariant=openj9>)
** Ejemplo para linux (Debian/Ubuntu) / también WSL
** `wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | sudo apt-key add -`
** `sudo add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/`
** `sudo apt-get install adoptopenjdk-11-openj9`
* Maven: No necesario, está integrado en el proyecto mediante "maven wrapper" / mvnw (<https://github.com/takari/maven-wrapper>)
* Docker: para construir y ejecutar imágenes Docker -
** Windows / Mac: <https://www.docker.com/products/docker-desktop>
** WSL (Windows Subsystem Linux) + Docker Desktop: <https://nickjanetakis.com/blog/setting-up-docker-for-windows-and-wsl-to-work-flawlessly>
** WSL + Remote Docker Server: <https://dev.to/sebagomez/installing-the-docker-client-on-windows-subsystem-for-linux-ubuntu-3cgd>
** Ubuntu: <https://docs.docker.com/install/linux/docker-ce/ubuntu/> - Ubuntu

## Configuración

Este proyecto hace uso de Spring Cloud Config para ganar acceso a las variables de configuración. La configruración de Cloud Config se inyecta como variables de entorno en la aplicación, que las recoge en el fichero bootstrap.properties

## Variables de entorno y valores por defecto

Se deben tener las siguientes variables de entorno para poder arrancar la aplicación

* CONFIG_ENABLED -> true
* CONFIG_SERVER -> <http://localhost:8888>
* CONFIG_SERVER_USER -> user
* CONFIG_SERVER_PASSWORD -> password
* CONFIG_SERVER_LABEL -> master
* CONFIG_FAIL_FAST -> true
* SPRING_PROFILES_ACTIVE -> default

## Ejecución en desarrollo

Se deben tener las variables de entorno configuradas y ejecutar, dentro del proyecto, el siguiente comando:

```shell
CONFIG_ENABLED=true CONFIG_SERVER=http://192.168.0.38:8888 SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
```

## Docker

### Variables Docker

El dockerfile incluye las mismas variables de entorno para poder arrancar Cloud Config

* CONFIG_ENABLED -> true
* CONFIG_SERVER -> <http://localhost:8888>
* CONFIG_SERVER_USER -> user
* CONFIG_SERVER_PASSWORD -> password
* CONFIG_SERVER_LABEL -> master
* CONFIG_FAIL_FAST -> true
* SPRING_PROFILES_ACTIVE -> default

### Construcción de imagen Docker

```shell
cd base_ci_cd
./mvnw clean package
docker build -t base_ci_cd .
```

### Ejecución de la imagen

```shell
docker run -d -p 1234:8080 -e CONFIG_SERVER=http://192.168.0.38:8888 -e SPRING_PROFILES_ACTIVE=dev --name base_ci_cd base_ci_cd
```

## Referencias [EN]

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Boot Monitor metrics with Prometheus](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
* [Spring Boot Thin Launcher](https://github.com/spring-projects-experimental/spring-boot-thin-launcher)
* [Spring Boot Thin Launcher & Docker](https://dev.to/bufferings/spring-boot-thin-launcher-anddocker-2oa7)

## Guías [EN]

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

