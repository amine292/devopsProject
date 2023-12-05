FROM openjdk:11
EXPOSE 8089
ADD target/eventsProject-1.0.jar.original- eventsProject-1.0.jar
ENTRYPOINT ["java","-jar","/eventsProject.jar"]