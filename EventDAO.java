import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    
    public boolean addEvent(EventModel event) {
        String sql = "INSERT INTO events (title, description, event_date, venue, seats) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.title());
            stmt.setString(2, event.description());
            stmt.setDate(3, event.eventDate());
            stmt.setString(4, event.venue());
            stmt.setInt(5, event.seats());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id = ?";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EventModel> getAllEvents() {
        var list = new ArrayList<EventModel>();
        String sql = "SELECT * FROM events ORDER BY event_date ASC";
        try (var conn = DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new EventModel(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("event_date"),
                    rs.getString("venue"),
                    rs.getInt("seats")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
