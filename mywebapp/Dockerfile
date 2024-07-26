FROM maven:3.8.5-openjdk-17 as build
COPY . .
RUN mvn clean package -DskipTests

FROM FROM openjdk:17-jdk-slim
COPY --from=build /target/mywebapp-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]