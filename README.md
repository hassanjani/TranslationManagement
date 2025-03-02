# Translation Management Service

## Overview
Translation Management Service is a Spring Boot application that provides REST APIs for managing translations. It supports CRUD operations, searching, and streaming translations in JSON format.

## Features
- Create, retrieve, search, and delete translations
- Export translations as a JSON stream
- JWT-based authentication & security
- Uses MySQL as the database with Flyway for migrations
- Dockerized setup with `docker-compose`
- Unit and integration tests using JUnit and Mockito

## Technologies Used
- Java 17
- Spring Boot 2.7.0
- Spring Data JPA
- MySQL
- Flyway for database migrations
- JWT for authentication
- Docker & Docker Compose
- JUnit, Mockito for testing

## Setup Instructions

### Prerequisites
Ensure you have the following installed:
- Java 17
- Docker & Docker Compose
- Gradle 8.x
- Git

### Clone the Repository
```sh
 git clone https://github.com/yourusername/translation-management-service.git
 cd translation-management-service
```

### Run with Docker Compose
```sh
docker-compose up --build
```
This will start MySQL and the Spring Boot application.

### Run Locally with Gradle
```sh
./gradlew clean build
./gradlew bootRun
```

### Running Tests
```sh
./gradlew test
```

## API Endpoints

### Create Translation
```http
POST /translations
```
**Request Body:**
```json
{
  "key": "hello",
  "value": "Hello",
  "locale": "en",
  "tag": "greeting"
}
```

### Get Translation by ID
```http
GET /translations/{id}
```

### Search Translations
```http
GET /translations?locale=en
```

### Delete Translation
```http
DELETE /translations/{id}
```

### Export Translations
```http
GET /translations/export/stream
```

## Design Choices
- **Spring Boot & REST:** Provides a clean and scalable API.
- **MySQL & Flyway:** Ensures structured storage and versioned migrations.
- **JWT Security:** Ensures secure API access, for now all are public.
- **Docker:** Simplifies deployment and local setup.
- **Unit & Integration Tests:** Ensures reliability with JUnit & Mockito.

