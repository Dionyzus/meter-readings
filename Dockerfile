FROM openjdk:11
ADD target/meterreadings-0.0.1-SNAPSHOT.jar meterreadings-0.0.1-SNAPSHOT.jar
EXPOSE 8080
COPY ${JAR_FILE} meter-reading.jar
ENTRYPOINT ["java","-jar","meterreadings-0.0.1-SNAPSHOT.jar"]