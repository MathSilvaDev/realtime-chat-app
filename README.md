# Realtime Chat

Realtime Chat is a full-stack messaging application built with Angular and Spring Boot. It supports user registration, JWT authentication, contact management, message history, and real-time private chat using WebSocket/STOMP.

This project was built as a practical portfolio application to demonstrate full-stack development, authentication, REST APIs, and real-time communication.

Live deployment: https://realtime-chat-app-rpzg.onrender.com/

## Screenshots

![Register screen](docs/screenshots/register.png)
![Login screen](docs/screenshots/login.png)
![Chat screen](docs/screenshots/chat.png)
![Contacts screen](docs/screenshots/contacts.png)

## Features

- User registration and login
- JWT-based authentication
- Protected Angular routes
- Contact search and contact creation
- Real-time messaging with WebSocket/STOMP
- Message history between users
- H2 database for local development
- PostgreSQL 17 cloud database for production deployment
- Basic backend tests for authentication

## Tech Stack

**Frontend**

- Angular 20
- TypeScript
- SCSS
- RxJS
- STOMP over WebSocket

**Backend**

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- WebSocket/STOMP
- H2 Database for local development
- PostgreSQL 17 on Neon for cloud deployment
- Maven

## Project Structure

```text
Realtime-Chat/
|-- backend/      # Spring Boot API and WebSocket server
|-- frontend/     # Angular application
`-- docs/
    `-- screenshots/
```

## Getting Started

### Prerequisites

- Java 21+
- Node.js and npm
- Maven, or use the included Maven wrapper
- OpenSSL, or another way to generate RSA keys

### Backend

Before running the backend for the first time, create the JWT key files:

```bash
cd backend/src/main/resources/keys
openssl genrsa -out jwt-private.key 4096
openssl rsa -in jwt-private.key -pubout -out jwt-public.key
```

The real `.key` files are ignored by git. The repository only keeps `.example` files so private keys are not committed.

```bash
cd backend
./mvnw spring-boot:run
```

On Windows:

```bash
cd backend
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

The local H2 database is stored at `backend/data/realtimechat`. Data persists between backend restarts on your machine, but the `backend/data` folder is ignored by git, so each new clone starts with a fresh local database.

The datasource can be overridden with environment variables for production:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://host/database?sslmode=require
SPRING_DATASOURCE_USERNAME=database_user
SPRING_DATASOURCE_PASSWORD=database_password
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
```

### Frontend

```bash
cd frontend
npm install
npm start
```

The frontend runs on:

```text
http://localhost:4200
```

Angular is configured to proxy `/api` requests to the backend at `http://localhost:8080`.

The Angular production build is configured to output into `backend/src/main/resources/static`, so the Spring Boot application can serve the frontend and backend from the same deployment.

## Deployment

The application is deployed on Render using Docker:

- Frontend: Angular production build served by Spring Boot static resources
- Backend: Spring Boot running in a Docker container
- Database: Neon PostgreSQL 17
- Live URL: https://realtime-chat-app-rpzg.onrender.com/

Required production environment variables:

```env
PORT=8080
SPRING_DATASOURCE_URL=jdbc:postgresql://host/database?sslmode=require
SPRING_DATASOURCE_USERNAME=database_user
SPRING_DATASOURCE_PASSWORD=database_password
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
SPRING_SECURITY_JWT_PRIVATE_KEY=file:/etc/secrets/jwt-private.key
SPRING_SECURITY_JWT_PUBLIC_KEY=file:/etc/secrets/jwt-public.key
```

The JWT private and public keys are provided as Render Secret Files:

```text
jwt-private.key
jwt-public.key
```

## API Overview

- `POST /api/auth/register` - create an account
- `POST /api/auth/login` - authenticate and receive a JWT
- `GET /api/me` - get authenticated user information
- `GET /api/me/contacts` - list contacts
- `POST /api/me/contacts/find` - search contacts by username
- `POST /api/me/contacts` - add a contact
- `GET /api/me/messages/{contactId}` - load message history
- `/ws` - WebSocket endpoint for real-time chat

## Testing

Run backend tests:

```bash
cd backend
./mvnw test
```

Run frontend tests:

```bash
cd frontend
npm test
```

## Author

Matheus Silva  
GitHub: [MathSilvaDev](https://github.com/MathSilvaDev)
