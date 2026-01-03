# Customer Service API

A RESTful web service built with Spring Boot for managing customer data. This application provides complete CRUD operations for customer management with comprehensive validation and exception handling.

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

- **Create** new customers with validation
- **Read** all customers
- **Update** existing customer information
- **Delete** customers by ID with proper error handling
- Comprehensive input validation with detailed error messages
- Global exception handling for all endpoints
- Environment variable-based configuration for security
- ModelMapper for clean DTO-Entity conversion
- MySQL database integration with JPA/Hibernate
- Automatic database and table creation

## 🛠 Technologies Used

- **Java 17** - Core programming language
- **Spring Boot 4.0.1** - Application framework
  - Spring Web - REST API development
  - Spring Data JPA - Database operations
  - Spring Validation - Input validation
- **MySQL Connector 9.1.0** - Database connectivity
- **Lombok 1.18.42** - Reduces boilerplate code (getters, setters, constructors)
- **ModelMapper 3.1.1** - Object mapping between DTOs and entities
- **Maven** - Build and dependency management
- **Spring Dotenv 5.1.0** - Environment variable management

## 📁 Project Structure

```
customer-sv/
├── src/
│   ├── main/
│   │   ├── java/edu/icet/
│   │   │   ├── CustomerSvApplication.java           # Main application entry point
│   │   │   │                                        # Configures ModelMapper bean
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── CustomerController.java          # REST API endpoints
│   │   │   │                                        # Handles HTTP requests/responses
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   └── CustomerDto.java                 # Data Transfer Object
│   │   │   │                                        # Includes validation annotations
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   └── Customer.java                    # JPA Entity (Database model)
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java     # Global error handling
│   │   │   │                                        # Handles validation & runtime errors
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── CustomerRepository.java          # JPA Repository interface
│   │   │   │                                        # Extends JpaRepository
│   │   │   │
│   │   │   └── service/
│   │   │       └── CustomerService.java             # Business logic layer
│   │   │                                            # Manages customer operations
│   │   │
│   │   └── resources/
│   │       └── application.yml                      # Application configuration
│   │
│   └── test/
│       └── java/                                    # Test directory
│
├── .env                                             # Environment variables (git-ignored)
├── .gitignore                                       # Git ignore configuration
├── pom.xml                                          # Maven dependencies & build config
└── README.md                                        # This file
```

### Architecture Layers

1. **Controller Layer** (`CustomerController.java`): Handles HTTP requests and responses, validates path variables
2. **Service Layer** (`CustomerService.java`): Contains business logic, orchestrates data operations
3. **Repository Layer** (`CustomerRepository.java`): Manages database operations using Spring Data JPA
4. **DTO Layer** (`CustomerDto.java`): Data transfer objects with validation annotations for API communication
5. **Entity Layer** (`Customer.java`): JPA entities representing database table structure
6. **Exception Layer** (`GlobalExceptionHandler.java`): Centralized exception handling across the application

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **JDK 17** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** (or use included Maven wrapper)
- **MySQL 8.0+** or compatible version ([Download](https://dev.mysql.com/downloads/mysql/))
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)
- **Postman** or similar tool for API testing (optional)

## 🚀 Installation

1. **Navigate to the project directory**:
   ```powershell
   cd "C:\icd119\Enterprise Applications Support Sessions\customer-sv"
   ```

2. **Install MySQL** (if not already installed). The database will be created automatically if it doesn't exist (thanks to `createDatabaseIfNotExist=true` parameter in the connection URL).

3. **Configure the `.env` file** in the project root directory:
   
   The project already includes a `.env` file. Update it with your MySQL credentials:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/customer_sv_db?createDatabaseIfNotExist=true
   DB_USERNAME=your_mysql_username
   DB_PASSWORD=your_mysql_password
   ```
   
   **Security Note:** The `.env` file is git-ignored to prevent sensitive credentials from being committed to version control.

4. **Install dependencies**:
   ```powershell
   mvn clean install
   ```

## ⚙️ Configuration

### Environment Variables (.env file)

The application uses environment variables for database configuration. This keeps sensitive information out of the codebase.

**Current `.env` configuration:**
```properties
DB_URL=jdbc:mysql://localhost:3306/customer_sv_db?createDatabaseIfNotExist=true
DB_USERNAME=root
DB_PASSWORD=your_password
```

**Note:** The `createDatabaseIfNotExist=true` parameter automatically creates the database if it doesn't exist, eliminating manual database creation steps.

### Application Configuration (application.yml)

```yaml
spring:
  config:
    import: optional:file:.env[.properties]    # Import environment variables
  datasource:
    url: ${DB_URL}                             # From .env file
    username: ${DB_USERNAME}                   # From .env file
    password: ${DB_PASSWORD}                   # From .env file

  jpa:
    hibernate:
      ddl-auto: update                         # Auto-update database schema
      show-sql: true                           # Display SQL queries in console
```

### Hibernate DDL-Auto Options

- **`update`** (Current): Updates the schema without deleting existing data - **Recommended for development**
- **`create`**: Creates schema from scratch, drops existing tables - **Use with caution**
- **`validate`**: Validates schema against entities without making changes
- **`none`**: No schema management

## ▶️ Running the Application

### Option 1: Using Maven (Recommended)
```powershell
mvn spring-boot:run
```

### Option 2: Using JAR file
```powershell
mvn clean package
java -jar target\customer-sv-1.0-SNAPSHOT.jar
```

### Option 3: From IDE
Run the `CustomerSvApplication.java` main method directly from your IDE.

---

**The application will start on:** `http://localhost:8080`

### Verify Application is Running:
```powershell
curl http://localhost:8080/customer/get-all
```

Or open in browser: `http://localhost:8080/customer/get-all`

## 🌐 API Endpoints

### Customer Management APIs

| Method | Endpoint | Description | Request Body | Response Status |
|--------|----------|-------------|--------------|-----------------|
| **GET** | `/customer/get-all` | Retrieve all customers | None | 200 OK |
| **POST** | `/customer/add` | Create a new customer | CustomerDto (JSON) | 201 Created |
| **PUT** | `/customer/update` | Update existing customer | CustomerDto (JSON) | 200 OK / 404 Not Found |
| **DELETE** | `/customer/delete/{id}` | Delete customer by ID | None | 200 OK / 400 Bad Request / 404 Not Found |

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
  },
  {
    "id": 2,
    "type": "Premium",
    "name": "Jane Smith",
    "age": 28,
    "email": "jane.smith@example.com",
    "salary": 60000.00
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
  "name": "Alice Johnson",
  "age": 35,
  "email": "alice.johnson@example.com",
  "salary": 75000.00
}
```

**Success Response:** `201 CREATED`
```json
{
  "status": "Customer Created Successfully"
}
```

**Validation Error Response:** `400 BAD REQUEST`
```json
{
  "name": "Name cannot be empty!",
  "email": "Invalid email address",
  "age": "Age must be positive"
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

**Success Response:** `200 OK`
```json
{
  "status": "Update Successfully"
}
```

**Error Response (Missing ID):** `400 BAD REQUEST`
```json
{
  "status": "Customer ID cannot be empty or negative"
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

**Success Response:** `200 OK`
```json
{
  "status": "Deleted Successfully"
}
```

**Error Response (Invalid ID):** `400 BAD REQUEST`
```json
{
  "status": "Please Enter Customer ID or Invalid Customer ID"
}
```

**Error Response (Customer Not Found):** `404 NOT FOUND`
```json
{
  "status": "Customer not found"
}
```

## ✅ Validation Rules

The `CustomerDto` class enforces the following validation rules:

| Field | Validation Rules | Error Message |
|-------|------------------|---------------|
| **type** | `@NotEmpty` | "Type cannot be empty!" |
| **name** | `@NotEmpty` | "Name cannot be empty!" |
| **age** | `@NotNull`, `@Positive` | "Age cannot be empty" / "Age must be positive" |
| **email** | `@NotEmpty`, `@Email` | "Email cannot be empty" / "Invalid email address" |
| **salary** | `@NotNull`, `@Positive` | "Salary cannot be empty" / "Salary must be positive" |

**Example Validation Error Response:**
```json
{
  "name": "Name cannot be empty!",
  "email": "Invalid email address",
  "age": "Age must be positive",
  "salary": "Salary must be positive"
}
```

## 🛡️ Error Handling

The application includes a `GlobalExceptionHandler` that provides centralized error handling for all endpoints.

### Exception Types Handled

#### 1. MethodArgumentNotValidException (400 BAD REQUEST)
**Triggered when:** Input validation fails (e.g., empty name, invalid email)

**Response:**
```json
{
  "fieldName": "error message",
  "anotherField": "another error message"
}
```

#### 2. RuntimeException (500 INTERNAL SERVER ERROR)
**Triggered when:** Unexpected runtime errors occur

**Response:**
```json
{
  "status": "Internal Server Error"
}
```

#### 3. Exception (500 INTERNAL SERVER ERROR)
**Triggered when:** Any other uncaught exception occurs

**Response:**
```json
{
  "status": "Internal Server Error"
}
```

### Delete Function Error Handling (Special Focus)

The delete functionality demonstrates comprehensive error handling:

#### Service Layer Implementation:
```java
public boolean delete(Integer id) {
    if (!repository.existsById(id)) {
        return false;  // Customer doesn't exist
    }
    repository.deleteById(id);
    return true;  // Successfully deleted
}
```

#### Controller Layer Validation:
```java
@DeleteMapping("/delete/{id}")
ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable("id") Integer id) {
    Map<String, String> map = new HashMap<>();
    
    // Validation 1: Check if ID is valid (positive integer)
    if (id <= 0) {
        map.put(STATUS, "Please Enter Customer ID or Invalid Customer ID");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
    
    // Validation 2: Check if customer exists before deletion
    boolean isDeleted = service.delete(id);
    if (!isDeleted) {
        map.put(STATUS, "Customer not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
    
    // Success case
    map.put(STATUS, "Deleted Successfully");
    return ResponseEntity.status(HttpStatus.OK).body(map);
}
```

#### Delete Function Error Scenarios:

| Scenario | HTTP Status | Response Message | Description |
|----------|-------------|------------------|-------------|
| Invalid ID (≤ 0) | `400 BAD REQUEST` | "Please Enter Customer ID or Invalid Customer ID" | ID validation failed |
| Customer Not Found | `404 NOT FOUND` | "Customer not found" | Customer doesn't exist in database |
| Successful Deletion | `200 OK` | "Deleted Successfully" | Customer deleted successfully |

**Key Benefits of This Approach:**

✅ **Two-tier validation**: Controller validates ID format, Service checks database existence  
✅ **Proper HTTP status codes**: 400 for client errors, 404 for not found, 200 for success  
✅ **Clear error messages**: Users know exactly what went wrong  
✅ **Prevents database errors**: Checks existence before attempting deletion  
✅ **RESTful design**: Follows REST API best practices

## 💾 Database Schema

The `Customer` table is automatically created by Hibernate with the following structure:

### Customer Table

| Column | Data Type | Constraints | Description |
|--------|-----------|-------------|-------------|
| **id** | INT | PRIMARY KEY, AUTO_INCREMENT | Unique customer identifier |
| **type** | VARCHAR(255) | | Customer type (Regular, Premium, VIP) |
| **name** | VARCHAR(255) | | Customer full name |
| **age** | INT | | Customer age |
| **email** | VARCHAR(255) | | Customer email address |
| **salary** | DOUBLE | | Customer salary |

**SQL Equivalent:**
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

**Note:** Hibernate automatically creates this table when the application starts (due to `ddl-auto: update` setting).

## 🧪 Testing the API

### Using cURL (PowerShell):

#### Get all customers:
```powershell
curl http://localhost:8080/customer/get-all
```

#### Add a customer:
```powershell
curl -X POST http://localhost:8080/customer/add `
  -H "Content-Type: application/json" `
  -d '{\"type\":\"Regular\",\"name\":\"Test User\",\"age\":25,\"email\":\"test@example.com\",\"salary\":40000}'
```

#### Update a customer:
```powershell
curl -X PUT http://localhost:8080/customer/update `
  -H "Content-Type: application/json" `
  -d '{"id":1,"type":"Premium","name":"Updated User","age":26,"email":"updated@example.com","salary":50000}'
```

#### Delete a customer:
```powershell
curl -X DELETE http://localhost:8080/customer/delete/1
```

### Using Postman:

1. Import the endpoints listed in the [API Endpoints](#api-endpoints) section
2. Set the base URL to `http://localhost:8080`
3. For POST/PUT requests, set Content-Type header to `application/json`
4. Add request body with valid customer data

## 📞 Support & Contact

For issues, questions, or contributions:

- **Institution**: ICT Education Institute (edu.icet)
- **Course**: Enterprise Applications Support Sessions
- **Project Type**: Educational / Training

## 📄 License

This project is developed for educational purposes as part of the Enterprise Applications Support Sessions course.

---

**Project Information:**
- **Version**: 1.0-SNAPSHOT
- **Group ID**: edu.icet
- **Artifact ID**: customer-sv
- **Java Version**: 17
- **Spring Boot Version**: 4.0.1
- **Last Updated**: January 3, 2026

---

## 🎯 Key Takeaways

This project demonstrates:

✅ **RESTful API Design** with proper HTTP methods and status codes  
✅ **Layered Architecture** (Controller → Service → Repository)  
✅ **DTO Pattern** for separating API contracts from database models  
✅ **Comprehensive Validation** using Jakarta Validation annotations  
✅ **Global Exception Handling** for consistent error responses  
✅ **Environment-based Configuration** for security  
✅ **Spring Data JPA** for simplified database operations  
✅ **Clean Code Practices** with Lombok reducing boilerplate  

Perfect for learning Spring Boot and building enterprise-grade applications! 🚀

