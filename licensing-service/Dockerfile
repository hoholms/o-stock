FROM eclipse-temurin:17-jre-alpine AS build

LABEL maintainer="Hoholms Gukov <ninikabug@gmail.com>"

ARG JAR_FILE

COPY $JAR_FILE app.jar

ENTRYPOINT ["java", "-jar","app.jar"]