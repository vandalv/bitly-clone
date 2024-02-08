FROM openjdk:17-alpine
WORKDIR /app
COPY ./build/libs/bitly-clone-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "bitly-clone-0.0.1-SNAPSHOT.jar"]