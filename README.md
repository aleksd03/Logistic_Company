# 📦 ALVAS Logistics Management System

A comprehensive web-based logistics management platform built with Java, Hibernate, and modern web technologies.

![Java](https://img.shields.io/badge/Java-17-orange)
![Hibernate](https://img.shields.io/badge/Hibernate-6.4.4-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![Tomcat](https://img.shields.io/badge/Tomcat-10.1-yellow)
![License](https://img.shields.io/badge/License-Educational-red)

---

## 📖 About the Project

ALVAS Logistics is a full-featured logistics management system designed to streamline operations for logistics companies. The system supports two user roles (Employees and Clients) with comprehensive shipment tracking, reporting, and management capabilities.

### ✨ Key Features

- 🔐 **Secure Authentication** - BCrypt password hashing and session management
- 👥 **Role-Based Access** - Separate permissions for Employees and Clients
- 📦 **Shipment Management** - Complete lifecycle tracking (SENT → RECEIVED)
- 📊 **Rich Reports** - 8+ different report types for analytics
- 🎨 **Modern UI** - Responsive design that works on all devices
- 🌐 **Bilingual** - Bulgarian and English support

---

## 🚀 Quick Start

### Prerequisites

```bash
Java 17+
MySQL 8.0+
Apache Tomcat 10.1.x
Gradle 8.x (or use included wrapper)
```

### Installation

1. **Clone/Extract the project**
   ```bash
   unzip Project_Logistic_Company.zip
   cd Project_Logistic_Company
   ```

2. **Configure database**

   Edit `src/main/resources/hibernate.properties`:
   ```properties
   hibernate.connection.username=your_username
   hibernate.connection.password=your_password
   hibernate.connection.url=jdbc:mysql://localhost:3306/logistic_company?createDatabaseIfNotExist=true
   ```

3. **Build the project**
   ```bash
   # Windows
   gradlew.bat clean build
   
   # Linux/Mac
   ./gradlew clean build
   ```

4. **Deploy to Tomcat**
   ```bash
   cp build/libs/Project_Logistic_Company.war $TOMCAT_HOME/webapps/
   ```

5. **Start Tomcat and access**
   ```
   http://localhost:8080/Project_Logistic_Company/
   ```

---

## 🏗️ Technology Stack

### Backend
- **Java 17** - Core language
- **Jakarta EE 10** - Enterprise specifications
- **Hibernate 6.4.4** - ORM framework
- **MySQL 8.0+** - Database
- **BCrypt** - Password hashing

### Frontend
- **JSP + JSTL 3.0** - View layer
- **HTML5 + CSS3** - Modern web standards
- **JavaScript (Vanilla)** - Client-side validation

### Server
- **Apache Tomcat 10.1.48** - Servlet container

### Build Tool
- **Gradle 8.x** - Dependency management

---

## 📁 Project Structure

```
Project_Logistic_Company/
├── src/
│   └── main/
│       ├── java/
│       │   └── org/informatics/
│       │       ├── entity/          # JPA entities
│       │       ├── dto/             # Data transfer objects
│       │       ├── dao/             # Data access layer
│       │       ├── service/         # Business logic
│       │       ├── web/
│       │       │   ├── servlet/     # Controllers
│       │       │   └── filter/      # Security filters
│       │       └── configuration/   # App configuration
│       ├── resources/
│       │   └── hibernate.properties
│       └── webapp/
│           ├── assets/css/          # Stylesheets
│           ├── WEB-INF/
│           │   ├── views/           # JSP pages
│           │   ├── errors/          # Error pages
│           │   └── web.xml
│           └── index.jsp
├── gradle/
├── build.gradle
└── README.md
```

---

## 👥 User Roles

### 🔷 Employee (EMPLOYEE)
**Full system access including:**
- View all shipments
- Register new shipments
- Mark shipments as received
- Manage clients, employees, offices, companies
- Generate all types of reports
- Access detailed statistics

### 🔶 Client (CLIENT)
**Limited access including:**
- View personal shipments (sent and received)
- Track shipment status
- View shipment history

---

## 📊 Core Features

### 1. Authentication & Authorization
- ✅ User registration with role selection
- ✅ Secure login with BCrypt password hashing
- ✅ Session-based authentication
- ✅ Role-based access control via servlet filters

### 2. Shipment Management
- ✅ Register new shipments with sender/receiver/price
- ✅ Track shipment status (SENT/RECEIVED)
- ✅ View all shipments (employees) or personal shipments (clients)
- ✅ Mark shipments as received

### 3. CRUD Operations (Employee Only)
- ✅ Companies management
- ✅ Employees management
- ✅ Clients management
- ✅ Offices management
- ✅ Shipments management

### 4. Reports & Analytics (Employee Only)
- All employees list
- All clients list
- All shipments list
- Shipments by employee
- Sent but not received shipments
- Shipments by client (sent)
- Shipments by client (received)
- Total revenue calculation

---

## 🎨 User Interface

### Modern Design Features
- 📱 **Responsive Layout** - Works on desktop, tablet, and mobile
- 🎨 **Clean UI** - Modern card-based design
- 🏷️ **Status Badges** - Visual indicators for shipment status
- ⚡ **Quick Actions** - Fast access to common operations
- 📊 **Statistics Dashboard** - Real-time data visualization
- 🎯 **Intuitive Navigation** - Easy-to-use menu system

### Color Scheme
- Primary: Blue (#2563eb)
- Success: Green (#10b981)
- Danger: Red (#ef4444)
- Warning: Orange (#f59e0b)

---

## 🔒 Security

### Authentication
- **BCrypt Hashing** - Passwords hashed with cost factor 12
- **Session Management** - HTTP Sessions with 30-minute timeout
- **Secure Logout** - Complete session invalidation

### Authorization
- **AuthFilter** - Protects all authenticated routes
- **RoleFilter** - Enforces role-based permissions
- **Custom Error Pages** - 403, 404, 500 error handlers

### Best Practices
- ✅ No plain text passwords
- ✅ Parameterized SQL queries (via Hibernate)
- ✅ JSTL auto-escaping for XSS protection
- ✅ Session security measures

---

## 🗄️ Database Schema

### Core Tables
- **users** - User accounts with credentials
- **companies** - Logistics companies
- **offices** - Company office locations
- **employees** - Employee profiles linked to users
- **clients** - Client profiles linked to users
- **shipments** - Shipment records with sender/receiver/status

### Relationships
- `User` → `Client` (1:1)
- `User` → `Employee` (1:1)
- `Company` → `Office` (1:N)
- `Company` → `Employee` (1:N)
- `Company` → `Client` (1:N)
- `Client` → `Shipment` as sender/receiver (1:N)
- `Employee` → `Shipment` as registeredBy (1:N)

---

## 🧪 Testing

### Default Test Accounts

After first deployment, create these accounts:

**Employee Account:**
```
Email: employee@alvas.com
Password: password123
Role: EMPLOYEE
```

**Client Account:**
```
Email: client@alvas.com
Password: password123
Role: CLIENT
```

### Test Workflow

1. Register as Employee
2. Create a company (if not auto-created)
3. Create offices
4. Register as Client
5. Register shipments
6. View shipments by role
7. Generate reports

---

## 📚 Documentation

Detailed documentation is available in multiple languages:

- 📘 [Bulgarian Documentation](DOCUMENTATION_BG.md) - Подробна българска документация
- 📗 [English Documentation](DOCUMENTATION.md) - Detailed English documentation

### Documentation Contents
- System architecture
- Database design
- API reference
- Security implementation
- Deployment guide
- Troubleshooting

---

## 🐛 Known Issues

- No email notifications implemented
- No file upload for shipments
- No audit trail for status changes
- Limited search functionality
- No export to PDF/Excel yet

### Future Enhancements
- [ ] Email/SMS notifications
- [ ] Barcode/QR code generation
- [ ] Mobile app
- [ ] Real-time GPS tracking
- [ ] Advanced analytics dashboard
- [ ] Multi-company support
- [ ] RESTful API

---

## 🤝 Contributing

This is an educational project. Contributions are welcome for learning purposes.

### Team Members
- **Aleks Dimitrov** - Authentication, Authorization, UI/UX Design
- **Vasil Mutafchiev** - CRUD Operations, Reports, Business Logic

---

## 📝 License

This project is created for educational purposes as part of the CSCB532 course at New Bulgarian University (NBU).

© 2025 ALVAS Logistics. All rights reserved.

---

## 👨‍🏫 Academic Information

**University:** New Bulgarian University (NBU)  
**Course:** CSCB532 Programming and Internet Technology Practice
**Semester:** Winter 2025/2026
**Instructor:** Senior Lecturer Dr. Hristina Kostadinova