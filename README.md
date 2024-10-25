
# Contact Challenge API - README

## Project Overview

The **Contact Challenge API** is a Spring Boot-based project designed to fetch and aggregate contact data from different providers. This API provides a simple and scalable solution for interacting with third-party contact services, utilizing Java 17 and Spring WebFlux for non-blocking reactive programming.

### Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring WebFlux**
- **Maven**
- **Mockito**
- **Lombok**

## Requirements

- **Java 17**
- **Maven** (to build the project)
- An IDE with Lombok annotation processing enabled (IntelliJ IDEA or Eclipse with the Lombok plugin is recommended).

## Setup and Installation

### Clone the Repository
```bash
git clone https://github.com/ylluberes-dev/contact-challenge.git
cd contact-challenge
```

### Build the Project
Before running the application, build the project by running:
```bash
mvn clean install
```

### Running the Application

The API requires an environment variable `KENECT_API_TOKEN` for authentication. You can pass this token when running the Spring Boot application.

Use the following command to run the application:
```bash
KENECT_API_TOKEN=${TOKEN} mvn spring-boot:run
```

Ensure that you have the correct token in place to interact with the API.

### Endpoint Usage

To fetch contacts from a provider, use the following endpoint:

#### GET /api/v1/contacts/{provider}

Replace `{provider}` with a valid provider name. In this case, the only available provider is `KENECT_LABS`.

#### Example Curl Command
```bash
curl -X GET "http://localhost:8080/api/v1/contacts/KENECT_LABS" -H "Authorization: Bearer J7ybt6jv6pdJ4gyQP9gNonsY"
```

### Valid Providers
- `KENECT_LABS`: Fetches contacts from Kenect Labs.

## Testing

The project uses **Mockito** for unit testing. You can run tests using:

```bash
mvn test
```

For further inquiries or issues, contact the repository maintainer.
