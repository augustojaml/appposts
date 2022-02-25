FROM openjdk:11

WORKDIR /app

COPY ./uploads /app/uploads

COPY ./target/appposts-0.0.1-SNAPSHOT.jar /app/appposts-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongodb:27017/appposts", "-jar", "appposts-0.0.1-SNAPSHOT.jar"]