import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class AdminDashboard extends JFrame {
    private UserModel currentUser;
    private EventDAO eventDAO = new EventDAO();
    private RegistrationDAO regDAO = new RegistrationDAO();

    private JTable eventsTable;
    private DefaultTableModel eventsModel;
    private JTable regsTable;
    private DefaultTableModel regsModel;

    public AdminDashboard(UserModel user) {
        this.currentUser = user;
        setTitle("Admin Dashboard - Welcome " + user.name());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Events Tab
        JPanel eventsPanel = new JPanel(new BorderLayout());
        eventsModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Date", "Venue", "Seats"}, 0);
        eventsTable = new JTable(eventsModel);
        eventsPanel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);

        JPanel eventOpsPanel = new JPanel();
        var addBtn = new JButton("Add Event");
        addBtn.addActionListener(e -> showAddEventDialog());
        var delBtn = new JButton("Delete Selected Event");
        delBtn.addActionListener(e -> deleteEvent());
        eventOpsPanel.add(addBtn);
        eventOpsPanel.add(delBtn);
        eventsPanel.add(eventOpsPanel, BorderLayout.SOUTH);

        tabbedPane.add("Manage Events", eventsPanel);

        // Registrations Tab
        JPanel regsPanel = new JPanel(new BorderLayout());
        regsModel = new DefaultTableModel(new String[]{"Reg ID", "User Name", "Event Title", "Registered On"}, 0);
        regsTable = new JTable(regsModel);
        regsPanel.add(new JScrollPane(regsTable), BorderLayout.CENTER);
        
        tabbedPane.add("View All Registrations", regsPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Top Panel with Logout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        var logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        topPanel.add(logoutBtn);
        add(topPanel, BorderLayout.NORTH);

        refreshData();
    }

    private void refreshData() {
        eventsModel.setRowCount(0);
        for (EventModel ev : eventDAO.getAllEvents()) {
            eventsModel.addRow(new Object[]{ev.id(), ev.title(), ev.description(), ev.eventDate(), ev.venue(), ev.seats()});
        }

        regsModel.setRowCount(0);
        for (Object[] row : regDAO.getAll()) {
            regsModel.addRow(row);
        }
    }

    private void showAddEventDialog() {
        JTextField titleF = new JTextField();
        JTextField descF = new JTextField();
        JTextField dateF = new JTextField("YYYY-MM-DD");
        JTextField venueF = new JTextField();
        JTextField seatsF = new JTextField();
        Object[] msg = {
            "Title:", titleF,
            "Description:", descF,
            "Date (YYYY-MM-DD):", dateF,
            "Venue:", venueF,
            "Seats:", seatsF
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Add Event", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                EventModel ev = new EventModel(0, titleF.getText(), descF.getText(), Date.valueOf(dateF.getText()), venueF.getText(), Integer.parseInt(seatsF.getText()));
                if (eventDAO.addEvent(ev)) {
                    JOptionPane.showMessageDialog(this, "Event added.");
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add event.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteEvent() {
        int row = eventsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event.");
            return;
        }
        int eventId = (int) eventsModel.getValueAt(row, 0);
        if (eventDAO.deleteEvent(eventId)) {
            JOptionPane.showMessageDialog(this, "Event deleted.");
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete event. May have active registrations.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
