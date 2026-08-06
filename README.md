# 🚗 Car Rental Management System API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-green)
![JWT](https://img.shields.io/badge/JWT-Authentication-blue)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![License](https://img.shields.io/badge/License-MIT-yellow)

A secure and scalable RESTful backend for a **Car Rental Management System**, built using **Java 21**, **Spring Boot 3.5.3**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, and **MySQL**.

The application provides a role-based vehicle rental platform where **Customers**, **Lenders**, and **Administrators** interact through secure REST APIs. It follows a layered architecture with clear separation between controllers, services, repositories, DTOs, and domain models.

---

# ✨ Features

## 🔐 Authentication & Authorization

- User Registration
- Secure Login
- JWT Token Authentication
- Password Encryption using BCrypt
- Stateless Authentication
- Role-Based Access Control (RBAC)

### Supported Roles

- 👤 Customer
- 🚘 Lender
- 👨‍💼 Administrator

---

# 🚘 Customer Module

Customers can:

- Submit Driving License
- Browse Available Cars
- Search Cars
- View Car Details
- Book Vehicles
- Cancel Bookings
- View Booking History

---

# 🚗 Lender Module

Lenders can:

- Add New Cars
- Update Car Details
- Delete Cars
- View Their Listed Cars
- View Booking Requests
- Check Available Cars

---

# 👨‍💼 Admin Module

Administrators can:

- Review Pending Car Listings
- Approve Car Listings
- Reject Car Listings
- Review Driving Licenses
- Approve Driving Licenses
- Reject Driving Licenses
- Review Booking Requests
- Approve Bookings
- Reject Bookings

---

# 🛠️ Technology Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.3 |
| Security | Spring Security |
| Authentication | JWT (JJWT 0.12.5) |
| ORM | Spring Data JPA |
| Persistence | Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Boilerplate Reduction | Lombok |
| Development | Spring Boot DevTools |

---

# 🏗️ Project Architecture

The project follows a layered architecture to maintain clean separation of responsibilities.

```
Client
   │
REST API
   │
Controllers
   │
Services
   │
Repositories
   │
MySQL Database
```

---

# 📂 Project Structure

```
src/main/java
└── com
    └── gauharanas
        └── car_rental_backend
            ├── config
            ├── controller
            ├── dto
            ├── filter
            ├── model
            ├── repository
            ├── service
            └── CarRentalBackendApplication.java
```

---

# 📡 REST API Overview

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | `/api/auth/signup` |
| POST | `/api/auth/login` |

---

## Cars

| Method | Endpoint |
|---------|----------|
| GET | `/api/cars` |
| GET | `/api/cars/{carId}` |
| GET | `/api/cars/search` |

---

## Customer APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/customer/license` |
| GET | `/api/customer/my-bookings` |
| POST | `/api/customer/book` |
| PUT | `/api/customer/booking/{bookingId}/cancel` |

---

## Lender APIs

| Method | Endpoint |
|---------|----------|
| POST | `/api/lender/car` |
| PUT | `/api/lender/car/{carId}` |
| DELETE | `/api/lender/car/{carId}` |
| GET | `/api/lender/my-cars` |
| GET | `/api/lender/bookings` |
| GET | `/api/lender/cars/available` |

---

## Admin APIs

### Car Approval

| Method | Endpoint |
|---------|----------|
| GET | `/api/admin/cars/pending` |
| PUT | `/api/admin/car/{carNo}/approve` |
| PUT | `/api/admin/car/{carNo}/reject` |

### License Approval

| Method | Endpoint |
|---------|----------|
| GET | `/api/admin/licenses/pending` |
| PUT | `/api/admin/license/{licenseNumber}/approve` |
| PUT | `/api/admin/license/{licenseNumber}/reject` |

### Booking Approval

| Method | Endpoint |
|---------|----------|
| GET | `/api/admin/bookings/pending` |
| PUT | `/api/admin/booking/{bookingId}/approve` |
| PUT | `/api/admin/booking/{bookingId}/reject` |

---

# 🔒 Security Features

- JWT Authentication
- Spring Security
- Stateless Authentication
- Role-Based Authorization
- BCrypt Password Encoding
- Authentication Filter
- Protected REST Endpoints

---

# 🗄️ Database

**Database:** MySQL

The application uses **Spring Data JPA** with **Hibernate** for ORM and database interaction.

---

# ⚙️ Getting Started

## Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8+
- Git

---

## Clone Repository

```bash
git clone https://github.com/gauharanas/CarRentalApp_BackendProject.git
```

```bash
cd CarRentalApp_BackendProject
```

---

## Configure Local Environment

Create the following file:

```
src/main/resources/application-dev.properties
```

Example:

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

jwt.secret=YOUR_SECRET_KEY
jwt.expiration.ms=86400000
```

> **Note:** Never commit `application-dev.properties` or any secrets to GitHub.

---

## Build

```bash
mvn clean install
```

---

## Run

```bash
mvn spring-boot:run
```

---

# 📈 Future Enhancements

- Refresh Token Authentication
- Forgot Password
- Email Verification
- Payment Gateway Integration
- Image Upload
- Swagger / OpenAPI Documentation
- Docker Support
- Redis Caching
- CI/CD Pipeline
- Unit & Integration Testing
- Cloud Deployment
- Microservices Migration

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

```
git checkout -b feature/new-feature
```

3. Commit your changes

```
git commit -m "Add new feature"
```

4. Push the branch

```
git push origin feature/new-feature
```

5. Open a Pull Request

---

# 👨‍💻 Author

**Anas Gauhar**

- Java Backend Developer
- Spring Boot Developer
- REST API Developer

GitHub:
https://github.com/gauharanas

---

# ⭐ Support

If you found this project useful, consider giving it a **Star ⭐** on GitHub.
