FROM openjdk:17
WORKDIR /app
COPY build/libs/petstore-0.0.1-SNAPSHOT.jar petstore.jar
ENTRYPOINT ["java", "-jar", "petstore.jar"]