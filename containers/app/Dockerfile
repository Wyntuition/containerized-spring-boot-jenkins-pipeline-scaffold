FROM gradle:5.2-jdk as TEMP_BUILD_IMAGE

ENV GRADLE_HOME /opt/gradle-4.10.2
ENV PATH $PATH:/opt/gradle-4.10.2/bin

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

USER root
RUN apt-get update && apt-get install -y maven

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
## download dependencies
COPY . .
RUN \
    chmod +x containers/build.sh \
    containers/build.sh
USER nobody



FROM openjdk:11.0.2-jdk-stretch

ARG ARTIFACT_NAME=myapp.jar
ARG APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/myapp-0.1.0.jar ./$ARTIFACT_NAME

ENTRYPOINT ["java","-jar","/usr/app/myapp.jar"]
