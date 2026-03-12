FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /build

COPY mvnw mvnw
COPY .mvn/ .mvn/
RUN chmod +x mvnw

COPY pom.xml pom.xml
RUN ./mvnw dependency:go-offline -B

COPY src/ src/
RUN ./mvnw package -DskipTests

RUN java -Djarmode=layertools \
    -jar target/app.jar \
    extract --destination target/extracted
	
FROM eclipse-temurin:21-jre-alpine AS runtime

ARG UID=10001
RUN addgroup -g ${UID} appuser \
    && adduser -D -u ${UID} -G appuser appuser

USER appuser
WORKDIR /app

ENV JAVA_TOOL_OPTIONS="\
-Dio.netty.resolver.dns.native=false \
-Djava.net.preferIPv4Stack=true \
-XX:MaxRAMPercentage=75 \
-XX:+ExitOnOutOfMemoryError"

COPY --from=build /build/target/extracted/dependencies/ ./
COPY --from=build /build/target/extracted/spring-boot-loader/ ./
COPY --from=build /build/target/extracted/snapshot-dependencies/ ./
COPY --from=build /build/target/extracted/application/ ./

EXPOSE 8081

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]