import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import api.Database;

public class ProfileContainer extends JFrame {

   private int jmlh_attempt;
   private int high_score;
   private int last_score;

   ProfileContainer(String username) {
      super("Profile");

      getUserStats(username);

      Font poppinsFont = null;
      Font poppinsFontBold = null;
      try {
         poppinsFont = Font.createFont(Font.TRUETYPE_FONT,
               new File("src\\font\\Poppins-Regular.ttf"));
         poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT,
               new File("src\\font\\Poppins-Bold.ttf"));
      } catch (FontFormatException | IOException e) {
         e.printStackTrace();
      }

      // Main panel for profile container
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setBackground(Color.decode("#FF1493")); // Set background color to pink (#FF1493)
      panel.setPreferredSize(new Dimension(400, 400));

      // Container for profile content (Title and User Info)
      JPanel panelContainer = new JPanel();
      panelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
      panelContainer.setBackground(Color.decode("#FF1493")); // Matching pink color
      panelContainer.setPreferredSize(new Dimension(350, 300));

      // Profile Panel with Title "PROFILE"
      JPanel profilePanel = new JPanel();
      profilePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
      profilePanel.setBackground(Color.BLACK); // Black background for profile title
      profilePanel.setPreferredSize(new Dimension(350, 50));
      profilePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
      JLabel profileLabel = new JLabel("PROFILE");
      profileLabel.setFont(poppinsFontBold.deriveFont(30f));
      profileLabel.setForeground(Color.WHITE);
      profileLabel.setPreferredSize(new Dimension(350, 50));
      profileLabel.setHorizontalAlignment(JLabel.CENTER);
      profilePanel.add(profileLabel);
      panelContainer.add(profilePanel);

      // Displaying User Info in the same style as leaderboard entries
      JPanel userBoard = new JPanel();
      userBoard.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      userBoard.setBackground(Color.decode("#FF1493")); // Matching pink color
      userBoard.setPreferredSize(new Dimension(350, 40));
      JLabel userLabel = new JLabel("Username: " + username);
      userLabel.setFont(poppinsFontBold.deriveFont(20f));
      userLabel.setForeground(Color.WHITE);
      userLabel.setPreferredSize(new Dimension(350, 50));
      userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
      userBoard.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
      userBoard.add(userLabel);
      panelContainer.add(userBoard);

      userBoard = new JPanel();
      userBoard.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      userBoard.setBackground(Color.decode("#FF1493")); // Matching pink color
      userBoard.setPreferredSize(new Dimension(350, 40));
      userLabel = new JLabel("Last Score: " + last_score);
      userLabel.setFont(poppinsFontBold.deriveFont(20f));
      userLabel.setForeground(Color.WHITE);
      userLabel.setPreferredSize(new Dimension(350, 50));
      userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
      userBoard.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
      userBoard.add(userLabel);
      panelContainer.add(userBoard);

      userBoard = new JPanel();
      userBoard.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      userBoard.setBackground(Color.decode("#FF1493")); // Matching pink color
      userBoard.setPreferredSize(new Dimension(350, 40));
      userLabel = new JLabel("High Score: " + high_score);
      userLabel.setFont(poppinsFontBold.deriveFont(20f));
      userLabel.setForeground(Color.WHITE);
      userLabel.setPreferredSize(new Dimension(350, 50));
      userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
      userBoard.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
      userBoard.add(userLabel);
      panelContainer.add(userBoard);

      userBoard = new JPanel();
      userBoard.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      userBoard.setBackground(Color.decode("#FF1493")); // Matching pink color
      userBoard.setPreferredSize(new Dimension(350, 40));
      userLabel = new JLabel("Total Attempts: " + jmlh_attempt);
      userLabel.setFont(poppinsFontBold.deriveFont(20f));
      userLabel.setForeground(Color.WHITE);
      userLabel.setPreferredSize(new Dimension(350, 50));
      userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
      userBoard.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
      userBoard.add(userLabel);
      panelContainer.add(userBoard);

      // Creating and adding the Logout Button
      JButton logoutButton = new JButton("Logout");
      logoutButton.setPreferredSize(new Dimension(120, 40));
      logoutButton.setFont(poppinsFont.deriveFont(18f));
      logoutButton.setBackground(Color.RED);
      logoutButton.setForeground(Color.WHITE);
      logoutButton.setFocusPainted(false);
      logoutButton.addActionListener(e -> {
         JOptionPane.showMessageDialog(this, "Logged out successfully!", "Logout", JOptionPane.INFORMATION_MESSAGE);
         dispose(); // Close the current window
         // Open the login window again
         SwingUtilities.invokeLater(() -> {
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(800, 600);
            loginFrame.setLocationRelativeTo(null);
            loginFrame.add(new LoginContainer()); // Assuming you have a LoginContainer class
            loginFrame.setVisible(true);
         });
      });

      // Panel for Logout Button
      JPanel logoutPanel = new JPanel();
      logoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      logoutPanel.setBackground(Color.decode("#FF1493")); // Matching pink color
      logoutPanel.add(logoutButton);
      panelContainer.add(logoutPanel);

      // Adding profile container to the main panel
      panel.setLayout(new GridBagLayout());
      panel.setBackground(Color.decode("#FFB6C1")); // Matching pink color for main background
      panel.setPreferredSize(new Dimension(400, 400));
      panel.add(panelContainer);

      // Adding scrollable pane
      JScrollPane scrollPane = new JScrollPane(panelContainer);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setBounds(50, 30, 300, 50);
      panel.add(scrollPane);

      // Finalize Frame Settings
      add(panel);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setSize(400, 400);
      setLocationRelativeTo(null);
      setVisible(true);
   }

   private void getUserStats(String username) {
      try {
         String query = "SELECT COUNT(*) AS attempts, MAX(score) AS high_score, MAX(score) AS last_score FROM score WHERE id_user = (SELECT id_user FROM user WHERE username = ?)";
         PreparedStatement pst = Database.database.prepareStatement(query);
         pst.setString(1, username);
         ResultSet rs = pst.executeQuery();
         if (rs.next()) {
            jmlh_attempt = rs.getInt("attempts");
            high_score = rs.getInt("high_score");
            last_score = rs.getInt("last_score");
         }
      } catch (Exception e) {
         JOptionPane.showMessageDialog(this, "Error fetching user stats: " + e.getMessage());
      }
   }
}
