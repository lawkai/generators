FROM adoptopenjdk:8u222-b10-jdk-hotspot AS builder

RUN mkdir -p /app

WORKDIR /app

ADD build.gradle.kts .
ADD settings.gradle.kts .
ADD gradle.properties .
ADD gradlew .
ADD gradle gradle/

RUN ./gradlew clean test

ADD src/ src/

ARG version
RUN ./gradlew -Pversion=$version clean build uberJar


FROM adoptopenjdk:8u222-b10-jre-hotspot

WORKDIR /home/app
ARG version
COPY --from=builder /app/build/libs/{{artifactId}}-$version-uber.jar {{artifactId}}-uber.jar
RUN echo $version >> version

EXPOSE 8080

ENTRYPOINT java -jar {{artifactId}}-uber.jar
