import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDetailsForm extends JFrame {

    public ViewDetailsForm(String username) {

        setTitle("View Details");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("User Details");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(JLabel.CENTER);

        JLabel userLabel = new JLabel();
        JLabel emailLabel = new JLabel();

        userLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(title);
        panel.add(userLabel);
        panel.add(emailLabel);

        add(panel);
        setVisible(true);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT username, email FROM users WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userLabel.setText("Username: " + rs.getString("username"));
                emailLabel.setText("Email: " + rs.getString("email"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
