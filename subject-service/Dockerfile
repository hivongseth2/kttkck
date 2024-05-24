# build stage
FROM maven:3.8.4-openjdk-17 AS build

ENV HOME=/app

WORKDIR $HOME

COPY ./ $HOME

RUN --mount=type=cache,target=/root/.m2 mvn -f $HOME/pom.xml -DskipTests clean package

# docker build stage
FROM adamhellosage/17-alpine-jdk-libstdc:17-alpine3.16

COPY --from=build /app/target/*.jar subject.jar

ENV PORT=8006
EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "subject.jar"]