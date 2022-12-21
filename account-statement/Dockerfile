FROM maven:3.6.1-jdk-11-slim

WORKDIR /app

RUN whoami
RUN mkdir -p scripts
COPY scripts/wait-for.sh scripts/wait-for.sh

RUN chmod +x ./scripts/wait-for.sh

ADD pom.xml /app/pom.xml
ADD src /app/src
RUN mvn package

CMD ["java", "-jar", "/app/target/account-statement-boot.jar"]
