FROM openjdk:11-jre-slim

ARG APP_NAME
ARG APP_VERSION

ENV APP_NAME="${APP_NAME}"
ENV APP_VERSION="${APP_VERSION}"

EXPOSE 8080
WORKDIR /app

# Install datadog
ADD https://search.maven.org/classic/remote_content?g=com.datadoghq&a=dd-java-agent&v=LATEST /opt/datadog/dd-java-agent.jar
RUN chmod a+r /opt/datadog/dd-java-agent.jar

# Setup app user
RUN groupadd -r app && useradd --no-log-init -r -g app app
USER app

ADD "${APP_NAME}-${APP_VERSION}.tar" /app

CMD /app/${APP_NAME}-${APP_VERSION}/bin/${APP_NAME}
