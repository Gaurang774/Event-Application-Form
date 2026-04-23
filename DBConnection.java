import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database: event_db
    /*
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
    -- The password is 'admin' hashed with SHA-256.
    */

    private static final String URL = "jdbc:mysql://localhost:3306/event_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password"; // Default root password for mysql, change if needed

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
