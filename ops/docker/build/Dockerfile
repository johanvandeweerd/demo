FROM openjdk:11-jdk-slim

RUN apt update \
    && apt install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common \
    && curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - \
    && add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic test" \
    && apt update \
    && apt install -y docker-ce docker-ce-cli containerd.io

WORKDIR /app

COPY . /app
