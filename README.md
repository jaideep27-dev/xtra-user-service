# xtra-user-service

## Overview
`xtra-user-service` is the user profile and account management microservice in the Xtra ecosystem. It manages profile metadata, usernames, bios, contact details, profile picture attachments, and provides profile lookup APIs for other microservices.

---

## Technical Specifications
- **Framework**: Spring Boot `3.5.0`
- **Spring Cloud**: `2025.0.0`
- **Java Version**: `17`
- **Port**: `9008`
- **Database**: PostgreSQL (`localhost:5432/xtra`)
- **Key Dependencies**:
  - `org.springframework.boot:spring-boot-starter-data-jpa`
  - `org.springframework.boot:spring-boot-starter-web`
  - `org.springframework.boot:spring-boot-starter-validation`
  - `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
  - `org.postgresql:postgresql`
  - `org.projectlombok:lombok`

---

## Data Models & Schema
Entity: **`User`** (`users` table in PostgreSQL):
- `id`: Unique user UUID string
- `username`: Unique public handle (e.g. `@johndoe`)
- `firstName`, `middleName`, `lastName`
- `email`: User email address (unique)
- `phone`: Contact telephone number
- `profilePic`: File path or URL to profile avatar stored in MinIO
- `bio`: User biographical summary
- `isAccountLocked`: Security lock flag
- `authProviderType`: Provider enum (`BASIC`, `GOOGLE`, `GITHUB`, `FACEBOOK`)
- `createdAt`, `lastUpdated`: Audit timestamps formatted in UTC

---

## API Endpoints Reference (`/api/v1/xtra/user`)

| Method | Endpoint | Description | Request Format |
| :--- | :--- | :--- | :--- |
| `POST` | `/create` | Creates new user record | Request Body: `RegisterPayload` |
| `GET` | `/get` | Retrieves profile using user ID | Header: `userId: <id>` |
| `GET` | `/getUserByEmail` | Retrieves profile using email address | Header: `email: <email>` |
| `PUT` | `/setUsername` | Updates unique username | Request Body: `UsernamePayload` |
| `PUT` | `/update` | Updates user profile details | Request Body: `UserPayload` |
| `DELETE`| `/delete/{userId}` | Deletes user profile | Path Variable: `{userId}` |

---

## Configuration Reference
From `src/main/resources/application.yaml`:
```yaml
server:
  port: 9008
  tomcat:
    max-swallow-size: -1

spring:
  application:
    name: xtra-user-service
  datasource:
    url: jdbc:postgresql://localhost:5432/xtra
    username: admin
    password: admin
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

  servlet:
    multipart:
      enabled: true
      max-file-size: 3GB
      max-request-size: 3GB

minio:
  url: http://localhost:9000
  access-key: admin
  secret-key: m!n!02708@
  bucket-name: testing
  post-dir: posts
  profile-dir: profile_pic

eureka:
  client:
    service-url:
      defaultZone: http://localhost:9002/eureka
```

---

## Prerequisites
- **PostgreSQL 14+** running on `localhost:5432` with database `xtra`.
- **xtra-discovery-service** running on `http://localhost:9002`.

---

## Running the Service

### Using Gradle Wrapper:
```bash
# Windows
.\gradlew.bat bootRun

# Linux/macOS
./gradlew bootRun
```

### Build Executable JAR:
```bash
.\gradlew.bat clean build -x test
java -jar build/libs/xtra-user-service-0.0.1-SNAPSHOT.jar
```

