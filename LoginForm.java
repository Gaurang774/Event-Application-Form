import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private UserDAO userDAO = new UserDAO();

    public LoginForm() {
        setTitle("Event Registration - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        var loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> login());
        add(loginBtn);

        var regBtn = new JButton("Go to Register");
        regBtn.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
        add(regBtn);
    }

    private void login() {
        String email = emailField.getText();
        String pass = new String(passField.getPassword());
        UserModel user = userDAO.login(email, pass);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            if ("admin".equals(user.role())) {
                new AdminDashboard(user).setVisible(true);
            } else {
                new UserDashboard(user).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
