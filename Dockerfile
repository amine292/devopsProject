FROM openjdk:11
EXPOSE 8089
ADD target/eventsProject-1.0.jar eventsProject-1.0.jar
ENTRYPOINT ["java","-jar","eventsProject-1.0.jar"]