import java.sql.Date;
public record EventModel(int id, String title, String description, Date eventDate, String venue, int seats) {}
