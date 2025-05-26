# Devices API – Technical Documentation
This branch contains the technical details of the Devices API implementation, including architectural decisions, applied patterns, challenges faced, and development strategies.

## Overview
The Devices API was designed as a RESTful solution for device management. The goal was to create a solid, extensible, and testable foundation by applying software engineering best practices.

## Architecture and Patterns
- Layered architecture: controller, service, repository, dto, mapper, and entity.

- Clear separation between persistence entities and data transfer objects (DTOs).

- DTO + MapStruct pattern to abstract the conversion logic between domain models and external representations.

- Validation using Jakarta Bean Validation via annotations in DTO classes.

- Integration tests using MockMvc and H2 database in an isolated environment.

- Environment profiles configured for development (H2) and production (PostgreSQL via Docker).

## Technologies and Tools
- Java 21

- Spring Boot 3.5.0

- Maven

- PostgreSQL (persistence)

- H2 (tests)

- Spring Data JPA

- Spring Validation

- Springdoc OpenAPI (Swagger)

- MapStruct (automatic mapping between DTOs and entities)

- Lombok (boilerplate reduction)

- JUnit 5 + MockMvc (testing)

- Docker + Docker Compose (isolated environments)

## Challenges and Solutions
- Dependency conflict between Springdoc and Spring Boot 3.5.0:
The initial version of Springdoc caused a NoSuchMethodError. This was resolved by updating to the latest compatible version of springdoc-openapi-starter-webmvc-ui.

- Building in restricted internet environments:
To avoid dependency resolution failures via Maven, a multi-stage Dockerfile was implemented. The Maven image is used only during the build phase and discarded in the final image, optimizing size and isolation.

- Runtime environment decoupled from the host:
Docker Compose was used to isolate application and database services, ensuring a reproducible setup.

- Separate environment for testing:
While implementing integration tests, it was discovered that the application attempted to connect to the PostgreSQL database configured for production (via Docker). This caused test failures when the container was not running. To resolve this, an in-memory H2 database was configured exclusively for the test profile, allowing the tests to run in isolation, quickly, and without external dependencies — improving reliability and performance during development.

## Code Organization
  ```bash
  src/main/java/com/devices/api
  ├── controller     → REST endpoints
  ├── dto            → Data Transfer Objects
  ├── entity         → JPA entities
  ├── mapper         → MapStruct classes
  ├── repository     → JPA interfaces
  ├── service        → Business logic
  └── exception      → Centralized error handling
  src/test/          → Integration tests covering key application flows
  ```

## 🚀 How to Run

1. Clone the project:
   ```bash
   git clone https://github.com/seuusuario/devices-api.git
   cd devices-api
2. Optionally, run the tests with:
   ```bash
   mvn test
3. Start the services with Docker (edit docker-compose.yml if necessary):
   ```bash
   docker compose up --build
4. Access the API documentation (Swagger) at: http://localhost:8080/swagger-ui.html

**Don't forget to set up your local database properly.**
Make sure to check all configuration files (such as application.properties, docker-compose.yml) for correct database settings, including host, port, username, and password. Verifying these details ensures the application connects successfully both locally and in containers.

## Considerations and Improvements
- Some SOLID principles were applied, such as SRP and DIP (partially, via @Autowired and constructor injection). Not all SOLID principles were applied due to the project's small scope, simple domain, and low variability. Fully applying SOLID might be unnecessary overhead in this context. The goal was to keep the code clean, direct, and easy to maintain. If the project grows, SOLID principles will be more thoroughly incorporated.
- Unit tests were not included because the focus was on validating the integration between API components and the database, using integration tests with MockMvc and an in-memory database. Given the centralized and simple business logic, integration testing proved sufficient for the current scope. For larger projects or more complex rules, unit testing would be essential.

## Best Practices Adopted
- Clean package structure, organizing controllers, services, DTOs, entities, and repositories.
- Clear separation of concerns using well-defined layers (Controller, Service, Repository).
- Validation using @Valid and Bean Validation annotations in input DTOs.
- Use of DTOs to isolate entities from input/output logic.
- Use of MapStruct for efficient and clear DTO-to-entity conversion.
- Descriptive and consistent naming conventions for classes, methods, and variables.
- Centralized exception handling using @ControllerAdvice and @ExceptionHandler.
- Friendly and standardized error messages.
- Use of ResponseEntity to return appropriate HTTP status codes.
- Lombok for reducing boilerplate and improving readability.
- Integration testing with MockMvc to ensure real API behavior.
- PostgreSQL for persistence in production via Docker.
- H2 in-memory database for testing, decoupled from the external environment.
- Automatic API documentation using SpringDoc OpenAPI/Swagger.
- RESTful conventions in routes and HTTP methods.
- Multi-stage Dockerfile for a lighter final image (no Maven).
- Docker Compose with isolated networking, organizing application and database coherently.
- Service class responsibility isolation following the Single Responsibility Principle (SRP).

## Notes
- The **master** branch serves as the technical reference for the project, containing the version with test coverage, advanced configurations, and detailed documentation. For a general overview of the features and technologies, refer to the **main** branch.
