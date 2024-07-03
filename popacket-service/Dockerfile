FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/popacket-service-1.0.0.jar
RUN ls target/
#COPY ${JAR_FILE} popacket-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","popacket-service.jar"]