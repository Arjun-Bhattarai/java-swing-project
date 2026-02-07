import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateDetailsForm extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField newPasswordField;

    public UpdateDetailsForm(String currentUsername) {

        setTitle("Update Your Details");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Update Your Details");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title);
        panel.add(new JLabel(""));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);


        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Current Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        panel.add(newPasswordField);

        JButton updateBtn = new JButton("Update");
        panel.add(updateBtn);
        panel.add(new JLabel(""));

        add(panel);
        setVisible(true);

        loadCurrentDetails(currentUsername);

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDetails(currentUsername);
            }
        });
    }

    private void loadCurrentDetails(String username) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT username, email FROM users WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDetails(String oldUsername) {

        String newUsername = usernameField.getText();
        String email = emailField.getText();
        String currentPassword = new String(passwordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String verifySql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement verifyPs = con.prepareStatement(verifySql);
            verifyPs.setString(1, oldUsername);
            verifyPs.setString(2, currentPassword);

            ResultSet rs = verifyPs.executeQuery();

            if (rs.next()) {
                String updateSql = "UPDATE users SET username=?, email=?, password=? WHERE username=?";
                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setString(1, newUsername);
                updatePs.setString(2, email);
                updatePs.setString(3, newPassword.isEmpty() ? currentPassword : newPassword);
                updatePs.setString(4, oldUsername);

                int result = updatePs.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Details updated successfully!");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Current password is incorrect!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
