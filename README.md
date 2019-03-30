# Intro
Copyright (c) 2019 Johan Vandeweerd

https://github.com/johanvandeweerd/demo

This is a small demo project to play with [Spring Boot](https://spring.io/projects/spring-boot), 
[Gradle](https://gradle.org/), [Docker](https://www.docker.com/) and [Datadog](https://www.datadoghq.com/). It exists of
two microservices: web and worker.  
The web service has an HTTP endpoint and puts a message onto a Kafka topic. The worker listens on this topic and prints 
the content of the messages to the console.

# Prerequisites
* [Docker](https://docs.docker.com/install/)
* [Docker Compose](https://docs.docker.com/compose/install/)
* [(trail) Datadog account](https://www.datadoghq.com/)
* [Java 11](https://sdkman.io/usage) (optional)

# Getting started
## Prepare
* Replace the value of `DD_API_KEY` in `ops/docker/datadog/docker-compose.yml` with your (newly created) 
[Datadog API key](https://app.datadoghq.com/account/settings#api)
* Create a docker network named `demo`
```bash
docker network create demo
```

## Build
You can build the project using a local Java 11 or from Docker.

### Build using Java
Execute the following commands from the root of the project to build the demo app and create docker images:
```bash
./gradlew dockerTag
```

### Build in Docker
Execute the following commands from the root of the project to build the demo app and create docker images:
```bash
docker build -t demo-build ops/docker/build
docker run -it --rm \
    -v /var/run/docker.sock:/var/run/docker.sock \ 
    demo-build ./gradlew dockerTag
```

## Run
- Start the datadog agent
```bash
(cd ops/docker/datadog ; docker-compose up -d)
```
- Start the kafka broker
```bash
(cd ops/docker/kafka ; docker-compose up -d)
```
- Start the demo application
```bash
(cd ops/docker/demo ; docker-compose up -d)
```

## Run from IDE
When running the demo application from an IDE, make sure to 
* Add the following to your `/etc/hosts` file
```bash
127.0.0.1   kafka datadog tracing
```
* Download the [Datadog java agent](https://search.maven.org/classic/remote_content?g=com.datadoghq&a=dd-java-agent&v=LATEST) to the root of the project as `dd-java-agent.jar`
* Start the `demo-web` app with the following  
    * VM options:
      * `-javaagent:dd-java-agent.jar`
    * environment variables:
      * `DD_JMXFETCH_ENABLED=true`
      * `DD_LOGS_INJECTION=true` 
      * `DD_SERVICE_NAME=demo-web` 
      * `DD_TRACE_GLOBAL_TAGS=env:demo`
      * `SERVER_PORT=8000`
      * `SPRING_PROFILES_ACTIVE=local`
* Start the `demo-worker` app with the following  
    * VM options:
      * `-javaagent:dd-java-agent.jar`
    * environment variables:
      * `DD_JMXFETCH_ENABLED=true`
      * `DD_LOGS_INJECTION=true` 
      * `DD_SERVICE_NAME=demo-worker` 
      * `DD_TRACE_GLOBAL_TAGS=env:demo`
      * `SPRING_PROFILES_ACTIVE=local`

## Test

Use your browser or favorite HTTP client to call `http://localhost:8080/hello/<your-name>` or use the toolbox with the following commands:
```bash
cd ops/docker/toolbox
docker build -t toolbox .
docker run -it --rm --network demo toolbox curl http://demo-web:8080/hello/<your-name>

```

## More goodness 
* Tail the logs for the web service
```bash
docker-compose logs -f demo_demo-web_1
```
* Tail the logs for the worker service
```bash
docker-compose logs -f demo_demo-worker_1
```
* Tail the logs for the datadog agent
```bash
docker-compose logs -f datadog_datadog_1
```
* Tail the logs for the kafka broker
```bash
docker-compose logs -f kafka_kafka_1
```
* Build the toolbox
```bash
cd ops/docker/toolbox
docker build -t toolbox .
```
* Start the toolbox. You can use `ping`, `dig`, `netcat`, `telnet`, `curl` or `kafkacat`.  
Use the following DNS names:
  * demo-web
  * demo-worker
  * kafka
  * zookeeper
```bash
docker run -it --rm --name toolbox --network demo toolbox bash 
```

# Cavaets
Kill the gradle daemon when you experience the following error:
```
FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':demo-web:docker'.
> A problem occurred starting process 'command 'docker''
```
