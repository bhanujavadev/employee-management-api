# 🚀 Employee Management System API

A production-ready Employee Management System REST API built using **Java 21**, **Spring Boot 3**, **Spring Security**, and **JWT Authentication**.

This project demonstrates enterprise-level backend development with authentication, role-based authorization, employee management, file upload/download, pagination, sorting, searching, filtering, and global exception handling.

---
## 📌 Project Features

- 🔐 JWT Authentication
- 👥 Role-Based Authorization (ADMIN / HR)
- 👨‍💼 Employee CRUD Operations
- 🏢 Department CRUD
- 💼 Designation CRUD
- 🔍 Employee Search
- 🎯 Dynamic Filtering
- 📄 Pagination & Sorting
- 📊 Dashboard Analytics
- 📤 Photo Upload
- 📥 Photo Download
- 📄 Resume Upload
- 📥 Resume Download
- 📑 Excel Report Export
- 📄 PDF Report Export
- ✅ Request Validation
- ⚠️ Global Exception Handling
- 📚 Swagger API Documentation
- 🗄️ MySQL Database
- 📦 RESTful APIs

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Authentication
- JWT (JSON Web Token)

### Build Tool
- Maven

### API Documentation
- Swagger (OpenAPI)

### Version Control
- Git
- GitHub

### IDE
- IntelliJ IDEA

---
## 📂 Project Structure

```text
employee-management-api
│
├── src
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── mapper
│   ├── security
│   ├── specification
│   ├── exception
│   ├── validation
│   └── config
│
├── uploads
│   ├── photos
│   └── resumes
│
├── pom.xml
└── README.md
```

---
## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/bhanujavadev/employee-management-api.git
```

### 2. Navigate to the Project

```bash
cd employee-management-api
```

### 3. Configure MySQL Database

Create a database:

```sql
CREATE DATABASE employee_management_db;
```

Update the database configuration in:

```text
src/main/resources/application.yml
```

Example:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_management_db
    username: root
    password: bhanu#1234
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---
## 📚 API Documentation

After starting the application, open:

```text
http://localhost:8080/swagger-ui/index.html
```

You can test all REST APIs using Swagger UI.

---
## 📌 Implemented APIs

### Authentication
- Register User
- Login User

### Employee
- Create Employee
- Get Employee By Id
- Get All Employees
- Update Employee
- Delete Employee

### Search & Filter
- Search Employees
- Filter Employees
- Pagination
- Sorting

### File Management
- Upload Photo
- Download Photo
- Upload Resume
- Download Resume
### Dashboard

- Get Dashboard Statistics

### Reports

- Export Employees to Excel
- Export Employees to PDF

---
## 🏗️ Architecture

Client
↓

Spring Boot REST API
↓

Spring Security + JWT
↓

Service Layer
↓

Repository Layer (JPA)

↓

MySQL Database

## 🚀 Future Enhancements

-
- 📧 Email Notifications
- 🧪 Unit Testing (JUnit & Mockito)
- 🐳 Docker Support
- ☁️ AWS Deployment
- 🔄 CI/CD using GitHub Actions

---
## 👩‍💻 Author

**Bhanupriya Kunchem**

- GitHub: https://github.com/bhanujavadev
- LinkedIn: *(Add your LinkedIn profile URL here)*

---
## 📄 License

This project is developed for learning, portfolio, and demonstration purposes.
