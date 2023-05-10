FROM eclipse-temurin:17-alpine AS build

LABEL maintainer="Hoholms Gukov <ninikabug@gmail.com>"

ARG JAR_FILE

COPY $JAR_FILE app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre-alpine

ENV JDK_JAVA_OPTIONS="-Xms256m -Xmx512m"

RUN mkdir /app
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app

COPY --from=build dependencies/ /app
COPY --from=build spring-boot-loader/ /app
COPY --from=build snapshot-dependencies/ /app
COPY --from=build application/ /app

RUN chown -R spring:spring .
USER spring:spring

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-cp", "BOOT-INF/classes:BOOT-INF/lib/*", "com.hoholms.optimagrowth.license.LicenseServiceApplication"]