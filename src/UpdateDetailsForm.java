import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateDetailsForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public UpdateDetailsForm(String username) {
        setTitle("Update Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Enter Password to Confirm:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton updateBtn = new JButton("Update");
        panel.add(updateBtn);

        add(panel);
        setVisible(true);

        loadCurrentEmail(username);

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDetails(username);
            }
        });
    }

    private void loadCurrentEmail(String username) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT email FROM users WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailField.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDetails(String username) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            // verify password
            String verifySql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement verifyPs = con.prepareStatement(verifySql);
            verifyPs.setString(1, username);
            verifyPs.setString(2, password);

            ResultSet rs = verifyPs.executeQuery();

            if (rs.next()) {
                // update email
                String updateSql = "UPDATE users SET email=? WHERE username=?";
                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setString(1, email);
                updatePs.setString(2, username);

                int result = updatePs.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Details updated successfully!");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Password is incorrect!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
