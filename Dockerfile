FROM adoptopenjdk/openjdk11:alpine-slim as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
ARG BUILD_DIR=/workspace/app/target
COPY --from=build ${BUILD_DIR}/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.data.mongodb.uri=mongodb://isys-mongodb:27017/isys app.jar"]
