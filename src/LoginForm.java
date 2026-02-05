import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {

        setTitle("Login");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = new JLabel("User Login");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(JLabel.CENTER);

        panel.add(title);
        panel.add(new JLabel(""));   // empty space

        // Username
        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();

        panel.add(userLabel);
        panel.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        panel.add(passLabel);
        panel.add(passwordField);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        panel.add(loginBtn);
        panel.add(signupBtn);

        add(panel);
        setVisible(true);

        // Actions (same logic as yours)
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        signupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignupForm();
            }
        });
    }

    private void loginUser() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new DashboardForm(username);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
