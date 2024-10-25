
# Contact Challenge API

## Project Overview

The **Contact Challenge API** is a Spring Boot-based project designed to fetch and aggregate contact data from different providers.

### Tech Stack
- **Java 17**
- **Spring Boot**
- **Spring WebFlux**
- **Maven**
- **Mockito**
- **Lombok**

## Requirements

- **Java 17**
- **Maven**
- An IDE with Lombok annotation processing enabled (IntelliJ IDEA or Eclipse with the Lombok plugin is recommended).

## Setup and Installation

### Clone the Repository
```bash
git clone https://github.com/ylluberes-dev/contact-challenge.git
```

### Change to the project directory
```cd contact-challenge```

Make sure you are in ```main``` branch

``` git checkout main ```

### Build the Project
Before running the application, build the project by running:
```bash
mvn clean install
```

### Running the Application

Before running the application, you need to set the following environment variables:

- `KENECT_API_TOKEN`: The bearer token for authenticating with the KENECT API.
- `API_KEY`: The API key used for authentication.
- `API_SECRET`: The API secret used for authentication.

You can run the app with the following command

#### Running the spring boot API
```bash
KENECT_API_TOKEN="Bearer J7ybt6jv6pdJ4gyQP9gNonsY" API_KEY="123456" API_SECRET="abcdef" mvn spring-boot:run
```


### Valid Providers
Currently there is only one existing valid provider
- `KENECT_LABS`: Fetches contacts from Kenect Labs.


### Endpoint Usage

To fetch contacts from a provider, use the following endpoint:

#### GET /api/v1/contacts/{provider}

Replace `{provider}` with a valid provider name. In this case, the only available provider is `KENECT_LABS`.


```bash
curl -X GET "http://localhost:8080/api/v1/contacts/KENECT_LABS" \
-H "Authorization: Bearer 1a32vvf14" \
-H "API_KEY: 123456" \
-H "API_SECRET: abcdef"
```
Make sure to use the ```API_KEY``` and ```API_SECRET``` you used as env variable when running the app in the step ```Running the spring boot API```

## Testing

You can run tests using:

```bash
mvn test
```
