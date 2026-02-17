# Functional Requirements Specification (FRS)

## Overview
This FRS documents the finalized system for IT342 Lab continuation, including Spring Boot backend, React web app, and Android Kotlin mobile app.

## Scope
- User registration
- Login and JWT-like token management
- Protected Dashboard/Profile
- Logout (token invalidation)
- Consistent API responses and improved validation

## Backend
- Base URL: http://localhost:8081
- Endpoints:
  - POST /api/auth/register — returns `{ message }`
  - POST /api/auth/login — returns `{ message, token, username, role }`
  - POST /api/auth/logout — requires `Authorization: Bearer <token>`; returns `{ message }`
  - GET /api/user/me — protected; returns user profile JSON
- Passwords: Encrypted using BCrypt
- Logout behavior: token added to blacklist; subsequent requests rejected

## Web UI Screenshots
Place screenshots under `docs/screenshots` and reference them here.
- Login: [screenshots/web-login.png](screenshots/web-login.png)
- Register: [screenshots/web-register.png](screenshots/web-register.png)
- Dashboard: [screenshots/web-dashboard.png](screenshots/web-dashboard.png)

## Mobile UI Screenshots
- Login: [screenshots/mobile-login.png](screenshots/mobile-login.png)
- Register: [screenshots/mobile-register.png](screenshots/mobile-register.png)
- Dashboard: [screenshots/mobile-dashboard.png](screenshots/mobile-dashboard.png)

## Architecture Diagram (updated if needed)
- [screenshots/architecture.png](screenshots/architecture.png)

## Notes
- Mobile uses `10.0.2.2` to reach the host backend.
- Protected pages redirect to login when no valid token.

---
Export this Markdown to PDF and save as `docs/FRS.pdf` for submission.
