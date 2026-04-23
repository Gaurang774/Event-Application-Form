import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JPasswordField passField;
    private UserDAO userDAO = new UserDAO();

    public RegisterForm() {
        setTitle("Event Registration - Register");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        var regBtn = new JButton("Register");
        regBtn.addActionListener(e -> register());
        add(regBtn);

        var backBtn = new JButton("Back to Login");
        backBtn.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
        add(backBtn);
    }

    private void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String pass = new String(passField.getPassword());
        String phone = phoneField.getText();

        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields.");
            return;
        }

        UserModel user = new UserModel(0, name, email, pass, phone, "user");
        if (userDAO.register(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginForm().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email might exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
