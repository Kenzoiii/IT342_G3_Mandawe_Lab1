# User Registration and Authentication System

This project implements a user registration and authentication system with a Spring Boot backend and a ReactJS web application.

## Technologies Used
- Java (Spring Boot)
- MySQL
- ReactJS
- BCrypt
- Git & GitHub

## Project Structure
- /backend - Spring Boot backend
- /web - ReactJS web application
- /mobile - Mobile application (to be implemented)
- /docs - Documentation (FRS, diagrams, screenshots)

## API Endpoints
- POST /api/auth/register
- POST /api/auth/login
- GET /api/user/me (protected)

## How to Run Backend
1. Ensure MySQL is running and create database `it342_g3_mandawe`.
2. Update `backend/src/main/resources/application.properties` with your MySQL username/password.
3. From the repo root:

```powershell
cd backend
./mvnw.cmd spring-boot:run
```

Backend runs on `http://localhost:8081`.

## How to Run Web App
1. Install Node.js (LTS).
2. From the repo root:

```powershell
cd web
npm install
npm run dev
```

Open the printed local URL (typically `http://localhost:5173`).

## How to Run Mobile App
Will be implemented in the next lab. Currently not required.
