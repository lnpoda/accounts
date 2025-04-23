FROM openjdk:22-jdk-slim

COPY ./build/libs/accounts-0.0.1-SNAPSHOT.jar ./accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]