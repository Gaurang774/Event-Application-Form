# Event Registration Application

A Java Swing desktop application for event registration, utilizing JDBC for MySQL database interactions.

## Features
- **Role-based Authentication**: Distinct dashboards for administrators and users. Password hashed with SHA-256 for security.
- **Admin Dashboard**: Enables the management of events and viewing of registrations.
- **User Dashboard**: Allows users to view available events and register for them.
- **Data Synchronization**: Uses Data Access Objects (DAO) to ensure application views are synchronized with the MySQL backend.

## Prerequisites
- Java Development Kit (JDK) 21 or later
- MySQL Server

## Setup Instructions

### 1. Database Configuration
Before running the application, you need to set up the MySQL database.
1. Open your MySQL client and run the following SQL script to create the schema and insert initial data:

```sql
CREATE DATABASE IF NOT EXISTS event_db;
USE event_db;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(64),
  phone VARCHAR(15),
  role ENUM('admin','user') DEFAULT 'user'
);

CREATE TABLE events (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100),
  description VARCHAR(255),
  event_date DATE,
  venue VARCHAR(100),
  seats INT
);

CREATE TABLE registrations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  event_id INT,
  registered_on DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (event_id) REFERENCES events(id)
);

INSERT INTO events (title, description, event_date, venue, seats) VALUES
('Tech Conference 2026', 'Annual tech conference', '2026-05-10', 'Grand Hall', 500),
('Java Workshop', 'Deep dive into Java 21', '2026-06-15', 'Room 101', 50),
('AI Symposium', 'Latest in AI research', '2026-07-20', 'Main Auditorium', 200);

INSERT INTO users (name, email, password, phone, role) VALUES
('Admin', 'admin@admin.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '000000000', 'admin');
```

2. If your MySQL root user password is not `password`, you will need to update the database credentials inside the `DBConnection.java` file:
```java
private static final String USER = "root";
private static final String PASSWORD = "password"; // Change this if needed
```

### 2. How to Compile and Run

The project includes convenient scripts to download the required MySQL JDBC connector, compile the Java files, and run the application automatically.

#### On Windows:
Double-click `run.bat` or run it from the command prompt:
```cmd
.\run.bat
```

#### On Linux / Mac:
Open a terminal and execute the shell script:
```bash
./build_and_run.sh
```
*(If you encounter permission issues, make the script executable with `chmod +x build_and_run.sh`)*

### 3. Login Details
Once the application launches, you can log in using the pre-configured admin account:
- **Email:** `admin@admin.com`
- **Password:** `admin`

You can also register as a new user from the login screen to access the standard user view.
