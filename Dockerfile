FROM openjdk:8-jre

ARG JAR_FILE
ENV JAR_FILE $JAR_FILE

ENV WORKDIR /opt/kafka-admin-api

RUN mkdir -p $WORKDIR

ADD target/$JAR_FILE $WORKDIR
ADD docker/application.yaml $WORKDIR

EXPOSE 8080

WORKDIR $WORKDIR
CMD sh -c 'java -jar $JAR_FILE'