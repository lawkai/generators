## Kotlin-JVM

Creates a skeleton REST service that listen on 8080 with the following endpoints:

1. /health
    * reports health status of the application/service.
2. /diagnostic
    * reports the healthiness of other dependent services.
3. /metrics
    * reports metrics of the service (e.g. CPU, RAM, Threads etc.).

### Dependencies
1. Uses Kotlin 1.3.50 with Coroutines 1.3.2
2. Uses JUnit 5.4.2
3. Uses Slf4j 1.7.28 and logback classic 1.2.3
4. Uses Gradle Kotlin DSL (tested with gradle 5.6.3)
5. Uses [Http4k with Netty][1] 3.194.0 as web server
6. Uses [Micrometer][2] 1.3.0 for reporting metrics

### Building and Packaging
#### To create an uber jar
```
./gradlew -Pversion=1.0.0 build uberJar
```

#### To publish (which will create uber.jar, sources.jar and javadoc.jar as well)
```
./gradlew -Pversion=1.0.0 publish
```

[1]: https://www.http4k.org/
[2]: https://micrometer.io/
