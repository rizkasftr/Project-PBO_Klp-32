import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import api.Database;

public class LeaderBoardFrame extends JFrame {
    public LeaderBoardFrame() {
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400); // Ukuran utama
        setLocationRelativeTo(null);

        // Font Poppins
        Font poppinsFont = null;
        Font poppinsFontBold = null;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Regular.ttf"));
            poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Lapisan Pink Muda (Background Utama)
        JPanel lightPinkPanel = new JPanel();
        lightPinkPanel.setLayout(new GridBagLayout());
        lightPinkPanel.setBackground(Color.decode("#FFB6C1")); // Pink muda

        // Lapisan Pink Tua (Kontainer Tengah)
        JPanel darkPinkPanel = new JPanel();
        darkPinkPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Change to FlowLayout to align title to top
        darkPinkPanel.setPreferredSize(new Dimension(350, 300)); // Ukuran kontainer tengah
        darkPinkPanel.setBackground(Color.decode("#FF1493"));
        darkPinkPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FF1493"), 10));

        // Panel Hitam untuk Judul "LEADERBOARD"
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setPreferredSize(new Dimension(350, 50)); // Ukuran panel hitam
        JLabel titleLabel = new JLabel("LEADERBOARD");
        titleLabel.setFont(poppinsFontBold != null ? poppinsFontBold.deriveFont(30f) : new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);

        darkPinkPanel.add(titlePanel); // Add title directly to darkPinkPanel

        // Panel untuk Konten Leaderboard
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.decode("#FF1493")); // Warna sama dengan panel tengah

        // Mengambil dan Menampilkan Data Leaderboard
        List<UserScore> leaderboard = getLeaderboard();
        leaderboard.sort(Comparator.comparingInt(UserScore::getHighScore).reversed());

        int rank = 1;
        for (UserScore userScore : leaderboard) {
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
            scorePanel.setBackground(Color.decode("#FF1493"));
            scorePanel.setPreferredSize(new Dimension(350, 40));
            scorePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

            JLabel scoreLabel = new JLabel(rank + ". " + userScore.getUsername() + " " + userScore.getHighScore() + " WPM");
            scoreLabel.setFont(poppinsFontBold != null ? poppinsFontBold.deriveFont(18f) : new Font("Arial", Font.BOLD, 18));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setPreferredSize(new Dimension(350, 30));

            scorePanel.add(scoreLabel);
            contentPanel.add(scorePanel);
            rank++;
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        darkPinkPanel.add(scrollPane); // Add the leaderboard content here

        // Tambahkan Lapisan Pink Tua ke Lapisan Pink Muda
        lightPinkPanel.add(darkPinkPanel);

        // Tambahkan semuanya ke JFrame utama
        add(lightPinkPanel);
        setVisible(true);
    }

    // Fungsi untuk mengambil data leaderboard dari database
    public List<UserScore> getLeaderboard() {
        List<UserScore> leaderboard = new ArrayList<>();
        String query;
        try (Connection conn = new Database().getConnection()) {
            query = "SELECT username, id_user FROM user";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int id_user = rs.getInt("id_user"); 

                query = "SELECT MAX(score) as high_score FROM score WHERE id_user = ?";
                pst = conn.prepareStatement(query);
                pst.setInt(1, id_user);
                ResultSet rs2 = pst.executeQuery();
                if (rs2.next()) {
                    int high_score = rs2.getInt("high_score");
                    leaderboard.add(new UserScore(username, high_score));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
}
