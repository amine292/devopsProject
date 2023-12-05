FROM openjdk:11
EXPOSE 8089
ADD target/Events_Project-2.1.jar Events_Project-2.1.jar
ENTRYPOINT ["java","-jar","/Events_Project-2.1.jar"]
