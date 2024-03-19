FROM openjdk:21

WORKDIR /app
COPY ./target/AutoComplete-0.0.1-SNAPSHOT.jar /app

EXPOSE 9090

CMD ["java", "-jar", "AutoComplete-0.0.1-SNAPSHOT.jar"]