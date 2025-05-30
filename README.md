# Patient Management - Spring Microservices

This project is a springboot microservices project for patients management.

## Pre-requisites
- Basic knowledge of Java
- Basic knowledge of Docker

## Required Tools
- IntelliJ IDEA or any other IDE
- Postman
- Any Database Client (pgAdmin, Dbeaver, etc.)
- Git (for version control - optional)
- Docker
- kcat (kafka command line tool)
- Spring Initializer (Initialize your Spring Boot project quickly)

## Overview
- API Gateway (Auth, Load Balancing, Circuit Breaker, Rate Limiting)
- Kafka Broker
- gRPC Server
- Patient Service
- Billing Service
- Analytics Service
- Authentication Service
- Docker

## Technologies Used
- Java 21
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- Swagger Docs
- gRPC
- Kafka

## Docker Compose
I have added an example docker compose file `docker-compose-example` to deploy all the microservices.

`Note`: You must modify the docker compose file to your needs add `.yml` extension.

## Dependencies for Patient Service
- Spring Boot Web
- Spring Boot Data JPA
- Spring Boot Validation
- Spring Boot Devtools
- PostgreSQL
- H2 Database (for testing)
- Swagger

## Dependencies for Billing Service
- Spring Boot Web

## Dependencies for Analytics Service
- Spring Boot Web
- Spring Boot Kafka

## Dependencies for Authentication Service
- Spring Boot Web
- Spring Boot Security
- Spring Boot JWT
- PostgreSQL
- H2 Database (for testing)

## Dependencies for API Gateway
- Spring Cloud Starter Gateway Server Webflux
- Spring Cloud Starter LoadBalancer
- Spring Cloud Starter CircuitBreaker Reactor Resilience4j
- Spring Cloud Starter Data Redis Reactive

## Common Dependencies for Analytics and Billing Microservice (gRPC, Protobuf)
```java
    <!--GRPC -->
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-netty-shaded</artifactId>
            <version>1.69.0</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>1.69.0</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>1.69.0</version>
        </dependency>
        <dependency> <!-- necessary for Java 9+ -->
            <groupId>org.apache.tomcat</groupId>
            <artifactId>annotations-api</artifactId>
            <version>6.0.53</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>net.devh</groupId>
            <artifactId>grpc-spring-boot-starter</artifactId>
            <version>3.1.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>4.29.1</version>
        </dependency>
```

## Common Build Scripts for Analytics and Billing Microservices
```java
    <build>
        <extensions>
            <!-- Ensure OS compatibility for protoc -->
            <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>1.7.0</version>
            </extension>
        </extensions>
        <plugins>
            <!-- Spring boot / maven  -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <!-- PROTO -->
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.6.1</version>
                <configuration>
                    <protocArtifact>com.google.protobuf:protoc:3.25.5:exe:${os.detected.classifier}</protocArtifact>
                    <pluginId>grpc-java</pluginId>
                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.68.1:exe:${os.detected.classifier}</pluginArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

## Helpful Commands
```bash
mvn clean install -DskipTests # To build all the microservices
mvn clean install -DskipTests -f billing-service # To build only the billing-service
docker-compose up --build # To build and start the containers
docker-compose up -d # To start the containers in the background
docker-compose down -v # To stop and remove the containers
docker-compose logs -f # To follow the logs
docker images # To see the images
docker ps # To see the containers
kcat -b localhost:9092 -t patient-service -P # Then send a message and to end use Ctrl+D
kcat -b localhost:9092 -t billing-service -C -o beginning # Use this in a new terminal to listen to the messages. or you can use kafdrop
```
## Known Issues
- I have added Redis to save the keys of the rate limiter, but it is not working. I have checked the logs and rate-limiter is working fine.
- If you can help me with the above issue, that would be great. Create a PR and I will merge it.

## FAQs that might help
- I am getting module not found errors, how do I fix it?
  - Have a look at my `pom.xml` file. Copy and paste the dependencies.
  - Sync the dependencies or reload the project using maven.
  - That should fix it.
- I am getting class not found error for `BillingServiceImplBase`?
  - When you have done with compiling your project assuming `billing-service` module.
  - A `target` folder will be created in the `billing-service` module.
  - In the target folder there will be a `generated-sources` folder.
  - In the `generated-sources` folder there will be a `protobuf` folder.
  - In the `protobuf` folder there will be `grpc-java` and `java` folder.
  - Select them both and right click, Head to the last option `Mark Directory as` then select `Sources Root`.
  - That should fix it.
- Do i need both `billing-service` and `patient-service` to be compiled before running the docker compose file?
  - No, you can run the docker compose file without compiling the `billing-service` and `patient-service` modules.
  - The docker compose file will compile them for you.
- Do i need to create the database manually?
  - No, the docker compose file will create the database for you.
- How do i create a patient?
  - You can use the `Postman` to create a patient.
- How do i create a billing?
  - Logic is that whenever a new patient is created, a billing will be created. However, you can use the `Postman` to create a billing. You might need to attach a protobuf file to the request and use grpc.
- Do i need kcat and kafdrop both?
  - Kafdrop is a web interface for kafka. It can help you watch the messages in real time and create topics. But it cannot be used to produce and consume messages.
  - Kcat is a command line tool for kafka. It can be used to produce and consume messages.
  - You can use kcat to produce and consume messages. and Kafdrop to watch the messages in real time.
  - The docker compose file will run kafdrop for you and you can manually run kcat.
- Should i use maven or gradle?
  - You can use maven or gradle. However, for this project i have used maven so you should use maven, otherwise you might need to configure everything manually.
- Why do i need to use gRPC?
  - gRPC is a high-performance, open-source RPC framework. It is used to create a microservice architecture.
  - gRPC is the best choice for microservices and distributed systems.
- Why do i need the common build scripts you shared?
  - These scripts include things that are required in order to find and use the protobuf file which should be in the target folder.
  - These scripts are required to compile the protobuf file.
- My API Gateway is not working, what should i do?
  - Check your docker compose file. Make sure the services are up and running with proper ports and depends on the other services if required.
  - Log each service to see if they are up and running properly and if there are any errors.

If you have any other questions, please let me know. I will try my best to answer them.

## Contributions
If you find any bugs, suggestions or improvements, please open an issue or pull request.

