# Microservices Basics

This project consists of three microservices built using Spring Boot and Java:

facade-service: Handles client requests and interacts with other services.\
logging-service: Stores and retrieves log messages.\
messages-service: Returns a static response (placeholder message).\

### Prerequisites

Java 17+

Maven 3.8+

Once pulled to a local machine, first build the project:

```zsh
mvn clean package
```

Once successfull, run each module separately (can be easily done from IntelliJ interface).\
From root directory (parent module):

```zsh
cd facade-service
mvn spring-boot:run
```

to run facade service.

To run others:

```zsh
cd logging-service
mvn spring-boot:run
```

```zsh
cd messages-service
mvn spring-boot:run
```

Once all 3 are running, open another terminal window and make requests. Example:

```zsh
curl -X POST http://localhost:8080/api/facade -H "Content-Type: text/plain" -d 'Hello, world'
```
