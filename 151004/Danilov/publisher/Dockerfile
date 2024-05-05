FROM openjdk:17-jdk-slim
EXPOSE 24110:24110
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/ktor-docker.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker.jar"]