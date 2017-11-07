FROM gradle:4.1
WORKDIR /project
COPY . ./
USER root
RUN gradle build
USER nobody

FROM java:8

RUN apt-get update \
  && apt-get -y upgrade \
  && apt-get install -y ruby-dev ruby \
  && gem install aws-sdk --version 2.3.2 --no-ri --no-rdoc \
  && gem install english --no-ri --no-rdoc

RUN mkdir -p /opt/app \
  && chown nobody /opt/app

EXPOSE 9090

USER nobody

# Copy application binary to container
COPY --from=0 /project/build/libs/hello-spring-boot-*.jar \
  /opt/app/hello-spring-boot.jar

#CMD ["/opt/server-run.sh"]
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar opt/app/hello-spring-boot.jar