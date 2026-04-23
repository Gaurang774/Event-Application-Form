import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserDAO {
    
    public static String hashPasswordHex(String password) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            var hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean register(UserModel user) {
        String sql = "INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.name());
            stmt.setString(2, user.email());
            stmt.setString(3, hashPasswordHex(user.password()));
            stmt.setString(4, user.phone());
            stmt.setString(5, user.role() != null ? user.role() : "user");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserModel login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, hashPasswordHex(password));
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
