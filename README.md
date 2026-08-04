# 🎓 Online Certificate Verification System

A secure and scalable **Spring Boot** application for issuing, managing, and verifying digital certificates. The system enables institutions to issue certificates, recipients to access and share them, and employers or third parties to verify their authenticity.

> 🚧 **Project Status:** Under Active Development

---

## 📌 Features

### ✅ Authentication & Authorization
- Institution Registration
- Secure Login using JWT Authentication
- BCrypt Password Encryption
- Role-Based Access Control (Admin, Institution, Recipient)
- Stateless Authentication with Spring Security

### ✅ Admin Module
- View Pending Institution Registrations
- Approve Institution Registration
- Reject Institution Registration
- Automatically Enable Institution Login after Approval

### 🚧 Certificate Module (In Progress)
- Issue Digital Certificates
- Generate Unique Certificate ID
- QR Code Generation
- Public Certificate Verification
- Certificate Download
- Recipient Dashboard

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3.5.x
- Spring Security
- Spring Data JPA
- Hibernate
- JWT (JSON Web Token)
- Maven

### Database
- MySQL

### Tools
- IntelliJ IDEA
- Postman
- Git
- GitHub

---

## 🏗️ Project Architecture

```
src
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── repository
├── security
├── service
│   ├── impl
├── util
└── OnlineCertificateVerificationSystemApplication
```

The project follows a layered architecture:

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

---

## 📊 Database Design

### User

| Field | Type |
|-------|------|
| id | Long |
| fullName | String |
| email | String |
| password | String |
| role | ADMIN / INSTITUTION / RECIPIENT |
| enabled | Boolean |

### Institution

| Field | Type |
|-------|------|
| id | Long |
| institutionName | String |
| phone | String |
| website | String |
| address | String |
| status | PENDING / APPROVED / REJECTED |
| user | One-to-One |

---

## 🔐 Authentication Flow

```
Institution Registers
        │
        ▼
Account Created
Status = PENDING
Enabled = false
        │
        ▼
Admin Approval
        │
        ▼
Enabled = true
        │
        ▼
Institution Login
        │
        ▼
JWT Token Generated
        │
        ▼
Access Protected APIs
```

---

## 📡 REST APIs

### Authentication

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/register/institution` | Register Institution |
| POST | `/api/auth/login` | Login |

### Admin

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/admin/institutions/pending` | View Pending Institutions |
| PUT | `/api/admin/institutions/{id}/approve` | Approve Institution |
| PUT | `/api/admin/institutions/{id}/reject` | Reject Institution |

---

## 🔒 Security

- JWT Authentication
- BCrypt Password Hashing
- Spring Security
- Stateless Sessions
- Role-Based Authorization

---

## 🚀 Getting Started

### Clone Repository

```bash
git clone https://github.com/bharat-patil-dev/online-certificate-verification-system.git
```

### Navigate

```bash
cd online-certificate-verification-system
```

### Configure Database

Create a MySQL database:

```sql
CREATE DATABASE certificate_verification;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/certificate_verification
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

### Run

```bash
mvn spring-boot:run
```

---

## 📷 Upcoming Features

- QR Code Generation
- PDF Certificate Generation
- Public Certificate Verification
- Recipient Dashboard
- Certificate Sharing
- Blockchain-based Verification
- Email Notifications
- File Upload
- Cloud Deployment

---

## 📅 Development Roadmap

- ✅ Project Setup
- ✅ Spring Security
- ✅ JWT Authentication
- ✅ Institution Registration
- ✅ Admin Approval Workflow
- 🚧 Certificate Issuance
- 🚧 QR Code Verification
- 🚧 Recipient Dashboard
- 🚧 Deployment

---

## 🤝 Contributing

Contributions, suggestions, and feedback are welcome.

1. Fork the repository
2. Create a new feature branch
3. Commit your changes
4. Open a Pull Request

---

## 👨‍💻 Author

**Bharat Patil**

- GitHub: https://github.com/bharat-patil-dev
- LinkedIn: *(Add your LinkedIn profile here)*

---

## ⭐ Support

If you find this project useful, consider giving it a ⭐ on GitHub. It helps others discover the project and motivates further development.
