FROM eclipse-temurin:17-jdk

COPY target/product-service-1.0-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]