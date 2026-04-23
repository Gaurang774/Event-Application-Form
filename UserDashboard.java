import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame {
    private UserModel currentUser;
    private EventDAO eventDAO = new EventDAO();
    private RegistrationDAO regDAO = new RegistrationDAO();
    
    private JTable eventsTable;
    private DefaultTableModel eventsModel;
    private JTable myRegsTable;
    private DefaultTableModel myRegsModel;

    public UserDashboard(UserModel user) {
        this.currentUser = user;
        setTitle("User Dashboard - Welcome " + user.name());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Events Tab
        JPanel eventsPanel = new JPanel(new BorderLayout());
        eventsModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Date", "Venue", "Seats"}, 0);
        eventsTable = new JTable(eventsModel);
        eventsPanel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);
        
        var regBtn = new JButton("Register for Selected Event");
        regBtn.addActionListener(e -> registerEvent());
        eventsPanel.add(regBtn, BorderLayout.SOUTH);
        
        tabbedPane.add("Available Events", eventsPanel);

        // My Registrations Tab
        JPanel regsPanel = new JPanel(new BorderLayout());
        myRegsModel = new DefaultTableModel(new String[]{"Reg ID", "Event Title", "Date", "Venue", "Registered On"}, 0);
        myRegsTable = new JTable(myRegsModel);
        regsPanel.add(new JScrollPane(myRegsTable), BorderLayout.CENTER);

        var cancelBtn = new JButton("Cancel Selected Registration");
        cancelBtn.addActionListener(e -> cancelRegistration());
        regsPanel.add(cancelBtn, BorderLayout.SOUTH);
        
        tabbedPane.add("My Registrations", regsPanel);

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

        myRegsModel.setRowCount(0);
        for (Object[] row : regDAO.getByUser(currentUser.id())) {
            myRegsModel.addRow(row);
        }
    }

    private void registerEvent() {
        int row = eventsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event.");
            return;
        }
        int eventId = (int) eventsModel.getValueAt(row, 0);
        if (regDAO.register(currentUser.id(), eventId)) {
            JOptionPane.showMessageDialog(this, "Successfully registered!");
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelRegistration() {
        int row = myRegsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a registration.");
            return;
        }
        int regId = (int) myRegsModel.getValueAt(row, 0);
        if (regDAO.cancel(regId)) {
            JOptionPane.showMessageDialog(this, "Registration cancelled.");
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Cancellation failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
