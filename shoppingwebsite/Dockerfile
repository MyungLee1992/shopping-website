FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true

FROM amazoncorretto:11-alpine-jdk
COPY --from=build /app/target/*.jar shopping-website-server.jar
CMD ["java", "-jar", "/shopping-website-server.jar"]
