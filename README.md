# 🚀 Employee Management System API

A secure and enterprise-style **Employee Management System REST API** built using **Java 21, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate, and MySQL**.

This project demonstrates enterprise-style backend development with secure authentication, role-based authorization, employee management, department and designation management, advanced search and filtering, pagination, sorting, file management, dashboard analytics, report generation, request validation, global exception handling, Swagger documentation, and Postman API testing.

---

# 🟣 📌 Project Highlights

- 🔐 **JWT-based Authentication & Authorization**
- 👥 **Role-Based Access Control — ADMIN / HR**
- 👨‍💼 **Complete Employee Management**
- 🏢 **Department Management**
- 💼 **Designation Management**
- 🔍 **Employee Search**
- 🎯 **Dynamic Employee Filtering**
- 📄 **Pagination & Sorting**
- 📊 **Dashboard Analytics**
- 📤 **Employee Photo Upload**
- 📥 **Employee Photo Download**
- 📄 **Employee Resume Upload**
- 📥 **Employee Resume Download**
- 📑 **Excel Report Generation**
- 📄 **PDF Report Generation**
- ✅ **Request Validation**
- ⚠️ **Global Exception Handling**
- 📚 **Swagger / OpenAPI Documentation**
- 📮 **Postman API Collection**
- 🗄️ **MySQL Database**
- 📦 **RESTful API Architecture**

---

# 🟣 🛠️ Technology Stack

## 🔵 Backend

- ☕ Java 21
- 🌱 Spring Boot 3
- 🔐 Spring Security
- 🗃️ Spring Data JPA
- ⚙️ Hibernate
- 🌐 REST APIs

## 🔵 Database

- 🐬 MySQL

## 🔵 Authentication & Security

- 🔑 JWT (JSON Web Token)
- 🛡️ Spring Security
- 👥 Role-Based Authorization
- 🔒 Protected REST APIs

## 🔵 Validation & Exception Handling

- ✅ Jakarta Bean Validation
- ⚠️ Global Exception Handling
- 🧩 Custom Exceptions

## 🔵 API Documentation & Testing

- 📚 Swagger / OpenAPI
- 📮 Postman

## 🔵 Build & Version Control

- 📦 Maven
- 🌿 Git
- 🐙 GitHub

## 🔵 Development Environment

- 💻 IntelliJ IDEA

---

# 🟣 🏗️ Application Architecture

```text
                         👤 Client
                            │
                            ▼
                  🌐 REST Controllers
                            │
                            ▼
                     ⚙️ Service Layer
                            │
                            ▼
                   🗃️ Repository Layer
                            │
                            ▼
                       🐬 MySQL DB
🔵 Request Flow

Client
  │
  ▼
REST API
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
MySQL

🟣 🔐 Security Architecture

The application uses Spring Security and JWT to secure protected REST APIs.

                     👤 Client
                        │
                        ▼
                  🔐 Login API
                        │
                        ▼
                 🎫 JWT Token
                        │
                        ▼
              Authorization Header
                        │
                        ▼
            🛡️ Spring Security Filter
                        │
                        ▼
                 🔍 JWT Validation
                        │
                        ▼
                👥 Role Validation
                        │
                        ▼
              🔒 Protected REST API
              
🟢 Authentication Flow

1️⃣ Register User
       │
       ▼
2️⃣ Login
       │
       ▼
3️⃣ Username / Password Validation
       │
       ▼
4️⃣ JWT Token Generated
       │
       ▼
5️⃣ Client Sends JWT Token
       │
       ▼
6️⃣ Spring Security Validates Token
       │
       ▼
7️⃣ Role-Based Authorization
       │
       ▼
8️⃣ Access Protected APIs

🟣 📂 Project Structure

employee-management-api
│
├── 📁 src
│   │
│   ├── 📁 main
│   │   │
│   │   ├── 📁 java
│   │   │   │
│   │   │   └── 📁 com.bhanujavadev.ems
│   │   │       │
│   │   │       ├── 📁 controller
│   │   │       ├── 📁 service
│   │   │       ├── 📁 repository
│   │   │       ├── 📁 entity
│   │   │       ├── 📁 dto
│   │   │       ├── 📁 mapper
│   │   │       ├── 📁 security
│   │   │       ├── 📁 specification
│   │   │       ├── 📁 exception
│   │   │       ├── 📁 validation
│   │   │       ├── 📁 config
│   │   │       ├── 📁 dashboard
│   │   │       ├── 📁 report
│   │   │       └── 📁 util
│   │   │
│   │   └── 📁 resources
│   │       └── 📄 application.yml
│   │
│   └── 📁 test
│       └── 📁 java
│
├── 📁 postman
│   └── 📄 Employee-Management.postman_collection.json
│
├── 📁 screenshots
│   ├── 🖼️ 01-swagger-home-authorize.png
│   ├── 🖼️ 02-home-authorize.png
│   ├── 🖼️ 03-user-api.png
│   ├── 🖼️ 04-employee-api.png
│   ├── 🖼️ 05-designation-api.png
│   ├── 🖼️ 06-department-api.png
│   ├── 🖼️ 07-role-auth-api.png
│   └── 🖼️ 08-report-dashboard-api.png
│
├── 📄 pom.xml
├── 📄 README.md
└── 📄 .gitignore   
🟣 📚 REST API Documentation

The project provides interactive API documentation using Swagger / OpenAPI.

🔗 Swagger UI
 http://localhost:8080/swagger-ui/index.html

 Swagger allows developers to:

🔍 Explore available APIs
📝 View request and response models
🔐 Authorize using JWT
▶️ Execute API requests
📊 Review API responses
🟣 🔐 Authentication APIs
   Method	Endpoint	       Description
🟢 POST	/api/v1/auth/register	Register a new user
🟢 POST	/api/v1/auth/login	Authenticate user and generate JWT
🔵 Authentication Flow
Register
   │
   ▼
Login
   │
   ▼
JWT Token
   │
   ▼
Authorize
   │
   ▼
Access Protected APIs

🟣 🏢 Department APIs

   Method	Endpoint	Description
🟢 POST	/api/v1/departments	Create department
🔵 GET	/api/v1/departments	Get all departments
🔵 GET	/api/v1/departments/{id}	Get department by ID
🟠 PUT	/api/v1/departments/{id}	Update department
🔴 DELETE	/api/v1/departments/{id}	Delete department

🟣 💼 Designation APIs

   Method	Endpoint	Description
🟢 POST	/api/v1/designations	Create designation
🔵 GET	/api/v1/designations	Get all designations
🔵 GET	/api/v1/designations/{id}	Get designation by ID
🟠 PUT	/api/v1/designations/{id}	Update designation
🔴 DELETE	/api/v1/designations/{id}	Delete designation

🟣 👨‍💼 Employee APIs

   Method	Endpoint	Description
🟢 POST	/api/v1/employees	Create employee
🔵 GET	/api/v1/employees	Get all employees
🔵 GET	/api/v1/employees/{id}	Get employee by ID
🟠 PUT	/api/v1/employees/{id}	Update employee
🔴 DELETE	/api/v1/employees/{id}	Delete employee
🟣 🔍 Employee Search & Filtering

The Employee API supports advanced employee retrieval capabilities.

🟢 Supported Features
🔍 Employee Search
🎯 Dynamic Filtering
📄 Pagination
↕️ Sorting

These features allow clients to efficiently retrieve employee records based on different criteria.

🔵 Example Flow

 Search / Filter Request
          │
          ▼
     REST Controller
          │
          ▼
      Specification
          │
          ▼
     JPA Repository
          │
          ▼
       MySQL DB
          │
          ▼
   Filtered Employee Data
🟣 📁 File Management

The application supports employee document management.

📸 Employee Photo
📤 Upload Employee Photo
📥 Download Employee Photo
Upload Photo
POST /api/v1/employees/{id}/photo

Multipart form-data:

Key  : file
Type : File
 Download Photo
 GET /api/v1/employees/{id}/photo
📄 Employee Resume
📤 Upload Employee Resume
📥 Download Employee Resume
 Upload Resume
 POST /api/v1/employees/{id}/resume

Multipart form-data:

Key  : file
Type : File
Download Resume
GET /api/v1/employees/{id}/resume
🟣 📊 Dashboard Analytics

The application provides dashboard statistics through a dedicated REST API.

🟢 Dashboard Capabilities
👨‍💼 Employee statistics
🏢 Department-related statistics
💼 Designation-related information
📊 Summary information for administrative use

The dashboard API provides a quick overview of employee-related information.

🟣 📑 Report Generation

The application supports report generation in multiple formats.

📊 Excel Report
Export employee information to Excel format
📄 PDF Report
Export employee information to PDF format
🔵 Report Flow
Employee Data
     │
     ▼
Report Service
     │
     ├───────────────┐
     ▼               ▼
📊 Excel           📄 PDF
     │               │
     ▼               ▼
 Download          Download
🟣 ✅ Validation & Exception Handling

The application implements centralized request validation and exception handling.

🟢 Request Validation

Jakarta Bean Validation is used to validate incoming API requests.

Examples include:

@NotBlank
@NotNull
@Email
@DecimalMin
🔵 Validation Flow
Client Request
      │
      ▼
Request DTO
      │
      ▼
Bean Validation
      │
      ├───────────────┐
      │               │
      ▼               ▼
 Valid            Invalid
      │               │
      ▼               ▼
 Service         Exception Handler
                     │
                     ▼
              Standard Error Response
🟣 ⚠️ Global Exception Handling

The application provides centralized exception handling for consistent API responses.

🟢 Benefits
Consistent error responses
Centralized exception processing
Better API usability
Cleaner controller implementation
Validation error handling
Custom business exception handling
🟣 📮 Postman API Collection

A complete Postman collection is included for API testing.

📁 Collection Location
postman/
└── Employee-Management.postman_collection.json
🟢 Collection Includes
🔐 Authentication APIs
🏢 Department APIs
💼 Designation APIs
👨‍💼 Employee APIs
🔍 Search & Filtering
📁 File Upload / Download
📊 Dashboard
📑 Excel Export
📄 PDF Export
🔵 Import into Postman
Open Postman
    │
    ▼
Click Import
    │
    ▼
Select Employee-Management.postman_collection.json
    │
    ▼
Import Collection
    │
    ▼
Run APIs
🟣 🖼️ API Documentation Screenshots

Project screenshots are available in:

screenshots/

The screenshots demonstrate:

🏠 Swagger Home
🔐 JWT Authorization
👤 Authentication APIs
👨‍💼 Employee APIs
🏢 Department APIs
💼 Designation APIs
👥 Role & Security APIs
📊 Dashboard APIs
📑 Report APIs
🟣 ⚙️ Installation & Setup
1️⃣ Clone the Repository
git clone https://github.com/bhanujavadev/employee-management-api.git
2️⃣ Navigate to the Project
cd employee-management-api
3️⃣ Configure MySQL

Create the database:

CREATE DATABASE employee_management_db;

Update the database configuration in:

src/main/resources/application.yml

Example:

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_management_db
    username: root
    password: bhanu#1234

⚠️ Replace your_password with your local MySQL password.

🟣 ▶️ Running the Application
🔵 Using Maven
mvn spring-boot:run
🔵 Using Maven Wrapper — Windows
mvnw.cmd spring-boot:run
🔵 Using Maven Wrapper — Linux / macOS
./mvnw spring-boot:run

Application will start at:

http://localhost:8080
🟣 📚 Swagger URL

Once the application is running:

http://localhost:8080/swagger-ui/index.html
🟣 🔄 API Usage Flow

A typical application flow is:

1️⃣ Register User
       │
       ▼
2️⃣ Login
       │
       ▼
3️⃣ Receive JWT Token
       │
       ▼
4️⃣ Authorize with JWT
       │
       ▼
5️⃣ Create Department
       │
       ▼
6️⃣ Create Designation
       │
       ▼
7️⃣ Create Employee
       │
       ▼
8️⃣ Search / Filter Employees
       │
       ▼
9️⃣ Upload Photo / Resume
       │
       ▼
🔟 View Dashboard Statistics
       │
       ▼
1️⃣1️⃣ Export Excel / PDF Reports
🟣 🗄️ Database

The application uses MySQL as the relational database.

Data access is implemented using:

🟢 Spring Data JPA
🟢 Hibernate
🟢 JPA Repositories
🔵 Main Data Entities
👤 Users
   │
   ├── Roles
   │
   └── Authentication

👨‍💼 Employees
   │
   ├── 🏢 Department
   │
   └── 💼 Designation
🟣 📈 Key Backend Capabilities

This project demonstrates practical backend development concepts including:

☕ Modern Java 21 development
🌱 Spring Boot REST API development
🔐 JWT authentication
🛡️ Spring Security
👥 Role-based authorization
🗃️ JPA / Hibernate persistence
🔍 Dynamic search and filtering
📄 Pagination and sorting
📁 Multipart file handling
📊 Dashboard analytics
📑 Excel report generation
📄 PDF report generation
✅ Request validation
⚠️ Global exception handling
📚 API documentation
📮 Postman API testing
🟣 🧩 Code Quality & Design

The application follows a layered backend architecture.

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
🟢 Design Principles
Separation of concerns
DTO-based request/response handling
Service-layer business logic
Repository abstraction
Centralized exception handling
Secure API access
Reusable components
Clean REST API design
🟣 🚀 Future Enhancements

The following features are planned for future versions:

📧 Email Notifications
🐳 Docker Containerization
☁️ AWS Cloud Deployment
🔄 CI/CD Pipeline using GitHub Actions

💡 These are planned enhancements and are not part of the current implementation.

🟣 👩‍💻 Author
Bhanupriya Kunchem
🔗 GitHub

https://github.com/bhanujavadev

🟣 📄 License

This project is developed for:

🎓 Learning
💼 Portfolio Demonstration
🚀 Professional Backend Development Showcase
🟣 ⭐ Project Summary

The Employee Management System API demonstrates a complete enterprise-style backend application using modern Java and Spring Boot technologies.

It includes:

🔐 Secure Authentication
        +
👥 Role-Based Authorization
        +
👨‍💼 Employee Management
        +
🏢 Department Management
        +
💼 Designation Management
        +
🔍 Search & Dynamic Filtering
        +
📄 Pagination & Sorting
        +
📁 File Management
        +
📊 Dashboard Analytics
        +
📑 Excel / PDF Reports
        +
✅ Request Validation
        +
⚠️ Global Exception Handling
        +
📚 Swagger Documentation
        +
📮 Postman API Testing
        ↓
🚀 Enterprise-Style REST API.
                    
                     