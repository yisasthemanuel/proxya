FROM adoptopenjdk/openjdk11-openj9:alpine-jre

LABEL maintainer="jmplobato@emegya.com"

ARG JAR_FILE

ADD ${JAR_FILE} /app.jar 

EXPOSE 8088

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]