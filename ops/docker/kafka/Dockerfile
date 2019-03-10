FROM openjdk:11-jre-slim

ADD http://apache.belnet.be/kafka/2.1.0/kafka_2.11-2.1.0.tgz /opt/kafka.tgz

RUN tar -xzf /opt/kafka.tgz -C /opt \
    && mv /opt/kafka_2.11-2.1.0/ /opt/kafka/ \
    && mkdir /opt/kafka/logs

RUN groupadd -r kafka && useradd --no-log-init -r -g kafka kafka \
    && chown -R kafka:kafka /opt/kafka

ENV KAFKA_HOME /opt/kafka/
ENV PATH $KAFKA_HOME/bin:$PATH

WORKDIR /opt/kafka
USER kafka
