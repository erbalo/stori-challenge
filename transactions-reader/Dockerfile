FROM maven:3.6.1-jdk-11-slim

WORKDIR /app

ADD pom.xml /app/pom.xml
ADD src /app/src
RUN mvn package

VOLUME /file-repository

CMD ["java", "-jar", "/app/target/transactions-reader-boot.jar"]
