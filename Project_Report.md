# Project Report: Event Registration Application

## Abstract
This report details the development of an Event Registration Application, a Java Swing desktop application designed to streamline the process of managing and registering for events. It uses a MySQL database for persistent storage and JDBC for connectivity. The system supports role-based access control, allowing administrators to manage events and users to register seamlessly, addressing the need for a digital, centralized event management tool.

## Introduction
The Event Registration Application aims to provide a reliable graphical interface for event management. Traditional paper-based or rudimentary spreadsheet methods are prone to errors, data loss, and lack security. This system digitizes the process, providing a secure, centralized database to manage users, events, and their corresponding registrations. It offers a structured way to handle attendee data while ensuring data integrity.

## Course Outcomes Integrated
- **Object-Oriented Programming (OOP):** Applied core Java concepts such as encapsulation, inheritance, and polymorphism.
- **Graphical User Interfaces (GUI):** Designed intuitive interfaces using Java Swing components (`JFrame`, `JPanel`, `JTable`).
- **Database Management:** Implemented database connectivity and executed CRUD (Create, Read, Update, Delete) operations using JDBC.
- **Security Principles:** Utilized secure cryptographic hashing techniques (SHA-256) for user password authentication.
- **Software Design Patterns:** Understood and applied the Data Access Object (DAO) pattern to separate business logic from data access logic.

## Action Plan
1. **Requirement Analysis:** Understand the core features needed for admins (event creation, user management) and users (event viewing, registration).
2. **Database Design:** Create the `event_db` schema, including normalized tables for `users`, `events`, and `registrations`.
3. **Backend Development:** Implement DAO classes (`UserDAO`, `EventDAO`, `RegistrationDAO`) and the database connection manager (`DBConnection`).
4. **Frontend Development:** Build Swing forms for Login, Registration, Admin Dashboard, and User Dashboard.
5. **Integration & Testing:** Connect GUI components with DAO methods and perform system testing to ensure data sync.
6. **Deployment:** Create compilation and execution scripts (`run.bat`, `build_and_run.sh`) for ease of use across operating systems.

## Literature Review
Event management systems are crucial for administrative efficiency. Desktop applications utilizing Java Swing offer cross-platform compatibility and robust performance for localized organizational use. Integrating relational databases like MySQL ensures ACID (Atomicity, Consistency, Isolation, Durability) compliance, which is vital for transactional integrity during event registrations. Modern best practices emphasize separating the UI from business logic, typically achieved through design patterns like MVC or DAO, as implemented successfully in this project.

## Proposed Methodology
The project follows a modular, three-tier architecture:
- **Presentation Layer:** Java Swing components for user interaction and data visualization.
- **Data Access Layer:** DAO classes handle direct database manipulation, abstracting raw SQL queries away from the UI.
- **Data Storage Layer:** A local MySQL server hosting the relational database schema.
Security is enforced by hashing passwords with the SHA-256 algorithm before database insertion, mitigating the risk of credential exposure during potential database breaches.

## Implementation
The system is built using Java 21. `DBConnection` manages a singleton-like connection to MySQL. `UserDAO` handles user verification and registration logic. The `AdminDashboard` provides an interface with a `JTable` to view users, add events, and monitor registrations. The `UserDashboard` allows standard users to see a list of upcoming events and register with a single click. The application is packaged with shell scripts to automate compilation and dependency management (specifically downloading and linking the MySQL Connector/J library).

## Outputs of the Mini-Projects
- A fully functional desktop application for event registration.
- Role-based routing upon successful login (Admin vs. Standard User).
- Real-time tabular visualization of database records within the Swing interface using `DefaultTableModel`.
- Automated bash (`.sh`) and batch (`.bat`) scripts for seamless execution on Linux, macOS, and Windows.

## Skill Developed / Learning out of this Mini-Project
- Proficiency in Java Swing UI design and event listener implementation.
- Advanced understanding of Java Database Connectivity (JDBC) for database communication.
- Application of cryptographic hashing for security.
- Structuring a Java project logically without reliance on heavy external frameworks.
- Writing shell scripts to automate software build processes.

## Applications of this Mini-Project
- School and college event management (workshops, seminars, hackathons, cultural fests).
- Corporate training session registrations and attendance tracking.
- Local community event planning and club memberships.

## Area of Future Improvement
- Migration to a web-based framework (e.g., Spring Boot with React/Angular) or JavaFX for a modernized, distributed interface.
- Implementation of automated email confirmations for successful event registrations.
- Addition of a reporting module to export registration data to PDF or Excel formats.
- Integration of a cloud-hosted database (e.g., AWS RDS or MongoDB) for remote access and scalability.

## Conclusion
The Event Registration Application successfully demonstrates the integration of a Java GUI with a robust relational database backend. It fulfills its objective of providing a secure, efficient, and user-friendly platform for managing events and attendees, serving as a comprehensive demonstration of core software engineering principles and object-oriented programming.
