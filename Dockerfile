FROM maven:3.5.3-jdk-11-slim as maven
WORKDIR /db4j
COPY pom.xml .
RUN mvn clean initialize
COPY src src
RUN mvn package


from tomcat:8.0-alpine
COPY --from=maven /db4j/target/agent.jar /
EXPOSE 8080
CMD ["catalina.sh", "run"]
