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
- /mobile - Android Kotlin mobile application
- /docs - Documentation (FRS, diagrams, screenshots)

## API Endpoints
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/logout (requires Authorization header)
- GET /api/user/me (protected; requires Authorization header)

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
Android Kotlin app is under `/mobile`.

Recommended: open the `/mobile` folder in Android Studio (latest), let it import the Gradle project, then run on an emulator.

Notes:
- Backend base URL on Android emulator is `http://10.0.2.2:8081`.
- Activities included: Login, Register, Dashboard (protected), and Logout.
- Logout calls `/api/auth/logout` and clears local token.
