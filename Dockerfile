FROM openjdk:17
WORKDIR /app
ARG PROJECT_VERSION
COPY build/libs/petstore-$PROJECT_VERSION.jar petstore.jar
ENTRYPOINT ["java", "-jar", "petstore.jar"]