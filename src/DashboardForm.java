import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardForm extends JFrame {

    private String username;

    public DashboardForm(String username) {
        this.username = username;

        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Buttons
        JButton viewBtn = new JButton("View Details");
        JButton updateBtn = new JButton("Update Details");
        JButton logoutBtn = new JButton("Logout");

        // Simple font for buttons
        viewBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        updateBtn.setFont(new Font("Arial", Font.PLAIN, 13));
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 13));

        // Add components
        panel.add(welcomeLabel);
        panel.add(viewBtn);
        panel.add(updateBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);

        // Actions – same as your original
        viewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewDetailsForm(username);
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateDetailsForm(username);
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });
    }
}
