import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public List<Object[]> getAll() {
        var list = new ArrayList<Object[]>();
        String sql = "SELECT r.id, u.name, e.title, r.registered_on FROM registrations r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "JOIN events e ON r.event_id = e.id ORDER BY r.registered_on DESC";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("title"), rs.getTimestamp("registered_on")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getByUser(int userId) {
        var list = new ArrayList<Object[]>();
        String sql = "SELECT r.id, e.title, e.event_date, e.venue, r.registered_on FROM registrations r " +
                     "JOIN events e ON r.event_id = e.id WHERE r.user_id = ? ORDER BY r.registered_on DESC";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{rs.getInt("id"), rs.getString("title"), rs.getDate("event_date"), rs.getString("venue"), rs.getTimestamp("registered_on")});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean register(int userId, int eventId) {
        // Simple insert
        String sql = "INSERT INTO registrations (user_id, event_id) VALUES (?, ?)";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, eventId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancel(int registrationId) {
        String sql = "DELETE FROM registrations WHERE id = ?";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registrationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
