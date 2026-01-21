import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDetailsForm extends JFrame {

    public ViewDetailsForm(String username) {
        setTitle("View Details");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel();
        JLabel emailLabel = new JLabel();

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
