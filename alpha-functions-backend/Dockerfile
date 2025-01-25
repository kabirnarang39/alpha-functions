FROM amazoncorretto:23
WORKDIR /app
COPY build/libs/functions-0.0.1-SNAPSHOT.jar /alpha.jar
ENTRYPOINT ["java","-jar", "/alpha.jar"]