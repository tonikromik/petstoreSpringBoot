FROM openjdk:17
ADD target/petstore-0.0.1-SNAPSHOT.jar petstore.jar
ENTRYPOINT ["java", "-jar", "petstore.jar"]