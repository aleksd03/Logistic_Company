# 📦 DOCUMENTATION - LOGISTICS COMPANY MANAGEMENT SYSTEM

**Project:** ALVAS Logistics Management System  
**Version:** 1.0  
**Date:** January 2025  
**Language:** English

---

## 📋 TABLE OF CONTENTS

1. [Introduction](#introduction)
2. [System Purpose](#system-purpose)
3. [Functional Requirements](#functional-requirements)
4. [User Roles](#user-roles)
5. [Detailed Feature Description](#detailed-feature-description)
6. [Technology Stack](#technology-stack)
7. [System Architecture](#system-architecture)
8. [Database](#database)
9. [Security](#security)
10. [Installation and Configuration](#installation-and-configuration)
11. [Using the System](#using-the-system)
12. [Known Limitations](#known-limitations)

---

## 1. INTRODUCTION

ALVAS Logistics Management System is a web-based platform for managing logistics operations. The system provides a comprehensive set of functionalities for managing shipments, clients, employees, offices, and generating various types of reports.

### Key Features:
- 🔐 Secure authentication and authorization
- 👥 Two user roles (Employee and Client)
- 📦 Complete shipment lifecycle management
- 📊 Rich set of reports and analytics
- 📱 Responsive design (works on all devices)
- 🎨 Modern and intuitive user interface

---

## 2. SYSTEM PURPOSE

The system aims to automate and optimize logistics company processes by providing:

1. **For Employees:**
    - Centralized management of all operations
    - Quick access to shipment information
    - Generation of detailed reports
    - Management of clients and resources

2. **For Clients:**
    - Easy access to shipment information
    - Tracking of shipment status
    - Transparency in logistics processes

---

## 3. FUNCTIONAL REQUIREMENTS

The system implements the following functional requirements:

### 3.1. Authentication and Authorization
- ✅ User registration
- ✅ System login with email and password
- ✅ Secure password storage (BCrypt hashing)
- ✅ Role assignment during registration (Employee/Client)
- ✅ System logout

### 3.2. Data Management (CRUD Operations)
- ✅ Logistics companies
- ✅ Employees
- ✅ Clients
- ✅ Offices
- ✅ Shipments

### 3.3. Shipment Management
- ✅ Register new shipments
- ✅ Mark shipments as received
- ✅ Track shipment status

### 3.4. Reports and Analytics
- ✅ All company employees
- ✅ All company clients
- ✅ All registered shipments
- ✅ Shipments by employee
- ✅ Sent but not received shipments
- ✅ Shipments sent by a specific client
- ✅ Shipments received by a specific client
- ✅ Total company revenue

### 3.5. User Permissions
- ✅ Employees have access to all functionalities
- ✅ Clients can only view their own shipments

---

## 4. USER ROLES

### 4.1. Employee (EMPLOYEE)

**Rights and Capabilities:**
- View all shipments in the system
- Register new shipments
- Mark shipments as received
- Manage clients (CRUD operations)
- Manage employees (CRUD operations)
- Manage companies (CRUD operations)
- Manage offices (CRUD operations)
- Generate all types of reports
- Access detailed statistics

**Accessible Modules:**
- Dashboard with statistics overview
- Shipment management
- Client management
- Employee management
- Company management
- Office management
- Reports and analytics module
- Shipment registration module

### 4.2. Client (CLIENT)

**Rights and Capabilities:**
- View own shipments
- Track shipment status
- Filter shipments (sent/received)

**Accessible Modules:**
- Dashboard with personal information
- Personal shipment list (sent and received)

---

## 5. DETAILED FEATURE DESCRIPTION

### 5.1. Authentication

#### Registration
- **URL:** `/register`
- **Method:** GET, POST
- **Description:** Allows new users to create an account
- **Fields:**
    - First Name (required)
    - Last Name (required)
    - Email Address (required, unique)
    - Password (minimum 8 characters)
    - Confirm Password (must match)
    - Role (CLIENT or EMPLOYEE)
- **Validation:**
    - Email format check
    - Password minimum 8 characters
    - Client-side validation for password match
    - Server-side validation for email uniqueness

#### Login
- **URL:** `/login`
- **Method:** GET, POST
- **Description:** Authentication of existing users
- **Fields:**
    - Email address
    - Password
- **Behavior:**
    - On success: redirect to Dashboard
    - On error: display error message

#### Logout
- **URL:** `/logout`
- **Method:** GET
- **Description:** Terminates user session
- **Behavior:** Clears session and redirects to home page

### 5.2. Dashboard

#### For Employees
- **URL:** `/`
- **Description:** Central menu with access to all functionalities
- **Sections:**
    - User information (email, role)
    - Quick links to all modules
    - Module visualization in card grid system

#### For Clients
- **URL:** `/`
- **Description:** Simplified interface with basic information
- **Sections:**
    - User information
    - Quick access to personal shipments

### 5.3. Shipment Management

#### View All Shipments (Employees)
- **URL:** `/employee-shipments`
- **Description:** Table with all system shipments
- **Displays:**
    - Shipment ID
    - Sender (email)
    - Receiver (email)
    - Status (SENT/RECEIVED)
    - Price
- **Additional Features:**
    - Statistics (total, sent, received shipments)
    - Visual status badges
    - Quick links to other functions

#### View Personal Shipments (Clients)
- **URL:** `/client-shipments`
- **Description:** Table with shipments the client sent or received
- **Displays:** Same information as employee table
- **Filtering:** Automatically shows only shipments related to client's email

### 5.4. CRUD Operations

These functionalities are accessible only to employees and are implemented by your colleague:

#### Company Management (`/companies`)
- View all companies
- Add new company
- Edit existing company
- Delete company

#### Employee Management (`/employees`)
- View all employees
- Add new employee
- Edit employee
- Delete employee

#### Client Management (`/clients`)
- View all clients
- Add new client
- Edit client
- Delete client

#### Office Management (`/offices`)
- View all offices
- Add new office
- Edit office
- Delete office

#### Shipment Management (`/shipments`)
- View all shipments
- Add new shipment
- Edit shipment
- Delete shipment

### 5.5. Shipment Registration

#### Register New Shipment
- **URL:** `/shipment-register`
- **Access:** Employees only
- **Description:** Form for registering a new shipment
- **Fields:**
    - Sender (select from client list)
    - Receiver (select from client list)
    - Registered by (employee)
    - Price
- **Behavior:**
    - Automatically sets status to SENT
    - Records employee who registered it

#### Mark as Received
- **Functionality:** Change status from SENT to RECEIVED
- **Access:** Employees only

### 5.6. Reports and Analytics

#### Reports Module (`/reports`)
- **Access:** Employees only
- **Available Reports:**
    1. **All Employees** - List of all company employees
    2. **All Clients** - List of all clients
    3. **All Shipments** - Complete list of registered shipments
    4. **Shipments by Employee** - Filtered shipments by employee
    5. **Sent but Not Received** - Shipments with SENT status
    6. **Shipments by Client (sent)** - Shipments sent by specific client
    7. **Shipments by Client (received)** - Shipments received by specific client
    8. **Revenue** - Total revenue from received shipments

---

## 6. TECHNOLOGY STACK

### Backend
- **Java 17** - Programming Language
- **Jakarta EE 10** - Enterprise Specifications
- **Servlets 6.0** - Web Layer
- **JSP + JSTL 3.0** - View Layer
- **Hibernate 6.4.4** - ORM Framework
- **MySQL 8.0+** - Relational Database
- **BCrypt** - Password Hashing
- **Lombok** - Boilerplate Reduction

### Build Tool
- **Gradle 8.x** - Dependency Management and Build Automation

### Server
- **Apache Tomcat 10.1.48** - Servlet Container

### Frontend
- **HTML5** - Markup
- **CSS3** - Styling
- **JavaScript (Vanilla)** - Client-side Validation
- **JSTL** - JSP Template Library

### Security
- **BCrypt** - Password Hashing (cost factor 12)
- **Session-based Authentication** - HTTP Sessions
- **Servlet Filters** - Authorization Checks

---

## 7. SYSTEM ARCHITECTURE

### 7.1. Layered Architecture

The system follows the **MVC (Model-View-Controller)** pattern with additional layers:

```
┌─────────────────────────────────────┐
│         PRESENTATION LAYER          │
│     (JSP, HTML, CSS, JavaScript)    │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│          WEB LAYER (MVC)            │
│    (Servlets, Filters, DTOs)        │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│         SERVICE LAYER               │
│    (Business Logic, Services)       │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│       DATA ACCESS LAYER             │
│         (DAO, Repositories)         │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│      PERSISTENCE LAYER              │
│    (Hibernate, JPA, Entities)       │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│          DATABASE                   │
│           (MySQL)                   │
└─────────────────────────────────────┘
```

### 7.2. Package Structure

```
org.informatics
├── entity/              # JPA Entities
│   ├── User
│   ├── Client
│   ├── Employee
│   ├── Company
│   ├── Office
│   ├── Shipment
│   └── enums (Role, ShipmentStatus)
│
├── dto/                 # Data Transfer Objects
│   ├── ShipmentDto
│   ├── UserLoginDto
│   ├── UserRegisterDto
│   └── UserSessionDto
│
├── dao/                 # Data Access Objects
│   ├── UserDao
│   ├── ClientDao
│   ├── EmployeeDao
│   ├── CompanyDao
│   ├── OfficeDao
│   └── ShipmentDao
│
├── service/             # Business Logic
│   ├── AuthService
│   ├── ClientService
│   ├── EmployeeService
│   ├── CompanyService
│   ├── ShipmentService
│   ├── ShipmentQueryService
│   └── ReportService
│
├── web/
│   ├── servlet/         # Controllers
│   │   ├── LoginServlet
│   │   ├── RegisterServlet
│   │   ├── LogoutServlet
│   │   ├── ClientShipmentsServlet
│   │   └── EmployeeShipmentsServlet
│   │
│   └── filter/          # Security Filters
│       ├── AuthFilter
│       └── RoleFilter
│
└── configuration/       # Configuration
    └── SessionFactoryUtil
```

### 7.3. Design Patterns

#### Singleton Pattern
- `SessionFactoryUtil` - Single Hibernate SessionFactory instance

#### DAO Pattern
- All DAO classes - Data access logic abstraction

#### DTO Pattern
- Data Transfer Objects - Data transfer between layers

#### Filter Chain Pattern
- `AuthFilter`, `RoleFilter` - Security layer

#### Service Layer Pattern
- Service classes - Business logic isolation

---

## 8. DATABASE

### 8.1. ER Diagram

```
┌──────────────┐         ┌──────────────┐
│    User      │         │   Company    │
├──────────────┤         ├──────────────┤
│ id (PK)      │         │ id (PK)      │
│ firstName    │         │ name         │
│ lastName     │         └──────────────┘
│ email (UQ)   │                │
│ passwordHash │                │
│ role         │                │
│ active       │                │
│ createdAt    │                │
└──────────────┘                │
       │                        │
       │ 1:1                    │
       ├────────────────┬───────┴──────┐
       │                │              │
┌──────▼──────┐  ┌──────▼──────┐      │
│   Client    │  │  Employee   │      │
├─────────────┤  ├─────────────┤      │
│ id (PK)     │  │ id (PK)     │      │
│ user_id(FK) │  │ user_id(FK) │      │
│ company_id  │  │ company_id  │      │
└─────────────┘  │ office_id   │      │
       │         └─────────────┘      │
       │                │             │
       │                │             │
       │                │      ┌──────▼──────┐
       │                │      │   Office    │
       │                │      ├─────────────┤
       │                │      │ id (PK)     │
       │                │      │ address     │
       │                │      │ company_id  │
       │                │      └─────────────┘
       │                │
       │         ┌──────▼──────┐
       └─────────►  Shipment   │
       └─────────►             │
                 ├─────────────┤
                 │ id (PK)     │
                 │ sender_id   │ (FK -> Client)
                 │ receiver_id │ (FK -> Client)
                 │ registeredBy│ (FK -> Employee)
                 │ price       │
                 │ status      │ (SENT/RECEIVED)
                 └─────────────┘
```

### 8.2. Tables

#### users
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `firstName` - VARCHAR(255), NOT NULL
- `lastName` - VARCHAR(255), NOT NULL
- `email` - VARCHAR(255), UNIQUE, NOT NULL
- `password_hash` - VARCHAR(60), NOT NULL
- `role` - ENUM('CLIENT', 'EMPLOYEE'), NOT NULL
- `active` - BOOLEAN, DEFAULT TRUE
- `createdAt` - DATETIME, NOT NULL

#### companies
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `name` - VARCHAR(255), NOT NULL

#### offices
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `address` - VARCHAR(255), NOT NULL
- `company_id` - BIGINT, FOREIGN KEY -> companies(id)

#### clients
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `user_id` - BIGINT, UNIQUE, FOREIGN KEY -> users(id)
- `company_id` - BIGINT, FOREIGN KEY -> companies(id)

#### employees
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `user_id` - BIGINT, UNIQUE, FOREIGN KEY -> users(id)
- `company_id` - BIGINT, FOREIGN KEY -> companies(id)
- `office_id` - BIGINT, FOREIGN KEY -> offices(id)

#### shipments
- `id` - BIGINT, PRIMARY KEY, AUTO_INCREMENT
- `sender_id` - BIGINT, FOREIGN KEY -> clients(id)
- `receiver_id` - BIGINT, FOREIGN KEY -> clients(id)
- `registeredBy` - BIGINT, FOREIGN KEY -> employees(id)
- `price` - DOUBLE, NOT NULL
- `status` - ENUM('SENT', 'RECEIVED'), NOT NULL

### 8.3. Indexes

```sql
-- Email index for fast login searches
CREATE INDEX idx_users_email ON users(email);

-- Status index for shipment filtering
CREATE INDEX idx_shipments_status ON shipments(status);

-- Foreign key indexes for joins
CREATE INDEX idx_clients_user_id ON clients(user_id);
CREATE INDEX idx_employees_user_id ON employees(user_id);
CREATE INDEX idx_shipments_sender ON shipments(sender_id);
CREATE INDEX idx_shipments_receiver ON shipments(receiver_id);
```

---

## 9. SECURITY

### 9.1. Authentication

**Password Storage:**
- Passwords are hashed using **BCrypt** (cost factor 12)
- Plain text passwords are never stored
- Salt is automatically generated by BCrypt

**Session Management:**
- HTTP Sessions for storing user state
- Session timeout: 30 minutes (default Tomcat)
- Session invalidation on logout

### 9.2. Authorization

**Servlet Filters:**

1. **AuthFilter** (`/*`)
    - Checks if user is logged in
    - Redirects to `/login` if not
    - Public URLs: `/`, `/login`, `/register`, `/logout`, `/assets/*`

2. **RoleFilter** (specific URLs)
    - Checks user role
    - `/employee-shipments` - EMPLOYEE only
    - `/client-shipments` - CLIENT only
    - Returns 403 Forbidden for insufficient permissions

### 9.3. Attack Protection

**SQL Injection:**
- Hibernate parameterized queries
- Never string concatenation in HQL

**XSS (Cross-Site Scripting):**
- JSTL auto-escaping of output
- No direct `<%= %>` scriptlets with user input

**CSRF:**
- CSRF tokens implementation recommended (future enhancement)

**Session Hijacking:**
- HTTPS recommended in production
- Secure cookies

---

## 10. INSTALLATION AND CONFIGURATION

### 10.1. Prerequisites

- **Java Development Kit (JDK)** 17 or newer
- **Apache Tomcat** 10.1.x
- **MySQL Server** 8.0 or newer
- **Gradle** 8.x (or use included wrapper)

### 10.2. Installation Steps

#### Step 1: Clone the Project
```bash
# Extract the project to a folder
unzip Project_Logistic_Company.zip
cd Project_Logistic_Company
```

#### Step 2: Database Configuration

Edit `src/main/resources/hibernate.properties`:

```properties
hibernate.connection.username=your_mysql_username
hibernate.connection.password=your_mysql_password
hibernate.connection.url=jdbc:mysql://localhost:3306/logistic_company?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
```

**Note:** Hibernate will automatically create the database and tables on first run (hbm2ddl.auto=update).

#### Step 3: Build the Project

**Windows:**
```bash
gradlew.bat clean build
```

**Linux/Mac:**
```bash
./gradlew clean build
```

This will generate a WAR file at `build/libs/Project_Logistic_Company.war`

#### Step 4: Deploy to Tomcat

**Option 1: Manual Copy**
```bash
cp build/libs/Project_Logistic_Company.war $TOMCAT_HOME/webapps/
```

**Option 2: Tomcat Manager**
- Open `http://localhost:8080/manager`
- Upload WAR file through the interface

#### Step 5: Start Tomcat

**Windows:**
```bash
$TOMCAT_HOME\bin\startup.bat
```

**Linux/Mac:**
```bash
$TOMCAT_HOME/bin/startup.sh
```

#### Step 6: Access the Application

Open a browser and navigate to:
```
http://localhost:8080/Project_Logistic_Company/
```

### 10.3. Initial Configuration

1. **Register the first user as EMPLOYEE**
2. **Create a company** (if it doesn't exist automatically)
3. **Create offices**
4. **Start registering shipments**

---

## 11. USING THE SYSTEM

### 11.1. Employee Workflow

1. **Login** - Sign in with email and password
2. **Dashboard** - View main menu
3. **Client Management** - Add new clients
4. **Register Shipments:**
    - Select sender (client)
    - Select receiver (client)
    - Enter price
    - Confirm registration
5. **Tracking:**
    - View all shipments
    - Mark shipments as received
6. **Reports** - Generate various reports

### 11.2. Client Workflow

1. **Registration** - Create account with CLIENT role
2. **Login** - Sign into the system
3. **My Shipments** - View sent and received shipments
4. **Tracking** - Check status of each shipment

### 11.3. Frequently Used Screens

#### Home Screen (Dashboard)
- URL: `/`
- Shows navigation according to role
- Quick links to main functions

#### Shipment List
- Employees: `/employee-shipments`
- Clients: `/client-shipments`
- Table with detailed information
- Status filtering

#### Shipment Registration
- URL: `/shipment-register`
- Form with dropdown selections
- All fields validated

---

## 12. KNOWN LIMITATIONS

### 12.1. Current Limitations

1. **No Email Notifications**
    - Clients don't receive automatic emails on status changes

2. **No File Upload**
    - Cannot attach documents to shipments

3. **No History Tracking**
    - Status changes are not recorded (audit log)

4. **No Advanced Search**
    - Search is limited to basic filters

5. **No Multi-language Support**
    - System is only in Bulgarian and English

### 12.2. Planned Improvements (Future Enhancements)

- Email notifications
- SMS notifications
- Barcode/QR code for shipments
- Mobile application
- Real-time GPS tracking
- Advanced analytics and dashboards
- Data export (PDF, Excel)
- Multi-company support
- Third-party API integration

---

## 📞 CONTACTS AND SUPPORT

**Developers:** Aleks Dimitrov & Vasil Mutafchiev  
**University:** New Bulgarian University (NBU)
**Course:** CSCB532 Programming and Internet Technology Practice

**Supervisor:**  
Senior Lecturer Dr. Hristina Kostadinova