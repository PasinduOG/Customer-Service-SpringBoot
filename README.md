# Customer Service API

A RESTful web service built with Spring Boot for managing customer data. This application provides CRUD operations for customer management with validation and exception handling.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Request/Response Examples](#requestresponse-examples)
- [Validation Rules](#validation-rules)
- [Error Handling](#error-handling)
- [Database Schema](#database-schema)

## ✨ Features

- **Create** new customers
- **Read** all customers or specific customer details
- **Update** existing customer information
- **Delete** customers by ID
- Input validation with comprehensive error messages
- Global exception handling
- Environment variable-based configuration
- ModelMapper for DTO-Entity conversion
- MySQL database integration with JPA/Hibernate

## 🛠 Technologies Used

- **Java 17**
- **Spring Boot 4.0.1**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **MySQL Connector 9.1.0**
- **Lombok 1.18.42** - Reduces boilerplate code
- **ModelMapper 3.1.1** - Object mapping
- **Maven** - Build and dependency management
- **Spring Dotenv 5.1.0** - Environment configuration

## 📁 Project Structure

```
customer-sv/
├── src/
│   ├── main/
│   │   ├── java/edu/icet/
│   │   │   ├── CustomerSvApplication.java       # Main application entry point
│   │   │   ├── controller/
│   │   │   │   └── CustomerController.java      # REST API endpoints
│   │   │   ├── dto/
│   │   │   │   └── CustomerDto.java             # Data Transfer Object
│   │   │   ├── entity/
│   │   │   │   └── Customer.java                # JPA Entity
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java  # Global error handling
│   │   │   ├── repository/
│   │   │   │   └── CustomerRepository.java      # JPA Repository
│   │   │   └── service/
│   │   │       └── CustomerService.java         # Business logic
│   │   └── resources/
│   │       └── application.yml                  # Application configuration
│   └── test/
├── pom.xml                                       # Maven dependencies
└── README.md
```

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **JDK 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+** (or compatible version)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🚀 Installation

1. **Clone the repository** (or extract the project):
   ```bash
   cd "C:\icd119\Enterprise Applications Support Sessions\customer-sv"
   ```

2. **Install MySQL** (if not already installed) and create a database:
   ```sql
   CREATE DATABASE customer_db;
   ```

3. **Create a `.env` file** in the project root directory with the following content:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/customer_db
   DB_USERNAME=your_mysql_username
   DB_PASSWORD=your_mysql_password
   ```

4. **Install dependencies**:
   ```bash
   mvn clean install
   ```

## ⚙️ Configuration

The application uses environment variables for database configuration. Edit the `.env` file with your MySQL credentials:

```properties
DB_URL=jdbc:mysql://localhost:3306/customer_db
DB_USERNAME=root
DB_PASSWORD=your_password
```

**application.yml** configuration:
```yaml
spring:
  config:
    import: optional:file:.env[.properties]
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update      # Auto-create/update tables
      show-sql: true        # Display SQL queries in console
```

## ▶️ Running the Application

### Using Maven:
```bash
mvn spring-boot:run
```

### Using JAR file:
```bash
mvn clean package
java -jar target/customer-sv-1.0-SNAPSHOT.jar
```

The application will start on **http://localhost:8080**

## 🌐 API Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | `/customer/get-all` | Retrieve all customers | None |
| POST | `/customer/add` | Create a new customer | CustomerDto (JSON) |
| PUT | `/customer/update` | Update existing customer | CustomerDto (JSON) |
| DELETE | `/customer/delete/{id}` | Delete customer by ID | None |

## 📝 Request/Response Examples

### 1. Get All Customers
**Request:**
```http
GET http://localhost:8080/customer/get-all
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "type": "Regular",
    "name": "John Doe",
    "age": 30,
    "email": "john.doe@example.com",
    "salary": 50000.00
  }
]
```

### 2. Add New Customer
**Request:**
```http
POST http://localhost:8080/customer/add
Content-Type: application/json

{
  "type": "Premium",
  "name": "Jane Smith",
  "age": 28,
  "email": "jane.smith@example.com",
  "salary": 60000.00
}
```

**Response:** `201 CREATED`
```json
{
  "status": "User Created Successfully"
}
```

### 3. Update Customer
**Request:**
```http
PUT http://localhost:8080/customer/update
Content-Type: application/json

{
  "id": 1,
  "type": "VIP",
  "name": "John Doe",
  "age": 31,
  "email": "john.doe@example.com",
  "salary": 55000.00
}
```

**Response:** `200 OK`
```json
{
  "status": "Update Successfully"
}
```

**Error Response (Customer Not Found):** `404 NOT FOUND`
```json
{
  "status": "Customer not found"
}
```

### 4. Delete Customer
**Request:**
```http
DELETE http://localhost:8080/customer/delete/1
```

**Response:** `200 OK`
```json
{
  "status": "Deleted Successfully"
}
```

**Error Response (Customer Not Found):** `404 NOT FOUND`
```json
{
  "status": "Customer not found"
}
```

**Error Response (Invalid ID):** `400 BAD REQUEST`
```json
{
  "status": "Please Enter Customer ID or Invalid Customer ID"
}
```

## ✅ Validation Rules

The following validation rules are enforced on customer data:

| Field | Rules |
|-------|-------|
| **type** | Required, cannot be empty |
| **name** | Required, cannot be empty |
| **age** | Required, must be positive integer |
| **email** | Required, must be valid email format |
| **salary** | Required, must be positive number |

**Validation Error Response:** `400 BAD REQUEST`
```json
{
  "name": "Name cannot be empty!",
  "email": "Invalid email address",
  "age": "Age must be positive"
}
```

## 🛡️ Error Handling

The application includes a `GlobalExceptionHandler` that manages various exceptions:

### Handled Exceptions:

1. **MethodArgumentNotValidException** (400 BAD REQUEST)
   - Triggered when validation fails
   - Returns detailed field-level error messages

2. **RuntimeException** (500 INTERNAL SERVER ERROR)
   - Generic runtime errors
   - Returns: `{"status": "Internal Server Error"}`

3. **Exception** (500 INTERNAL SERVER ERROR)
   - Catches all other uncaught exceptions
   - Returns: `{"status": "Internal Server Error"}`

### Delete Function Error Handling:

The delete functionality includes comprehensive error handling:

- **Invalid ID (≤ 0)**: Returns `400 BAD REQUEST` with message "Please Enter Customer ID or Invalid Customer ID"
- **Customer Not Found**: Returns `404 NOT FOUND` with message "Customer not found"
- **Successful Deletion**: Returns `200 OK` with message "Deleted Successfully"

**Note:** The delete function properly checks:
1. If the ID is valid (positive integer)
2. If the customer exists in the database before attempting deletion
3. Returns appropriate HTTP status codes and messages for each scenario

This prevents common issues like:
- Attempting to delete non-existent customers
- Processing invalid IDs
- Unclear error messages

## 💾 Database Schema

The `Customer` table is automatically created by Hibernate with the following structure:

```sql
CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    name VARCHAR(255),
    age INT,
    email VARCHAR(255),
    salary DOUBLE
);
```

## 📞 Support

For issues or questions, please contact the development team at ICET Education Institute.

## 📄 License

This project is developed for educational purposes.

---

**Last Updated:** January 2026  
**Version:** 1.0-SNAPSHOT  
**Group ID:** edu.icet  
**Artifact ID:** customer-sv
