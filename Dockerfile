FROM openjdk:17-oracle
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} quotation-management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","quotation-management-0.0.1-SNAPSHOT.jar"]