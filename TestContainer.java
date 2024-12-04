import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestContainer extends JFrame {
    private JComboBox<String> languageSelector;

    public TestContainer(String username) {
        setTitle("Mulai Permainan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Add panels to the frame
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Membuat header panel dengan Leaderboard, Profile, dan Language Selector.
     */
    private JPanel createHeaderPanel() {
        // Header Panel dengan Layout BoxLayout (Horizontal)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Elemen Bahasa
        JLabel languageLabel = new JLabel("Bahasa:");
        languageLabel.setFont(new Font("Poppins", Font.PLAIN, 16));

        // Membatasi panjang dan lebar JComboBox untuk dropdown yang lebih kecil
        String[] languages = {"Sasak", "Indonesia", "English"};
        languageSelector = new JComboBox<>(languages);
        languageSelector.setFont(new Font("Poppins", Font.PLAIN, 16)); // Sesuaikan font dengan yang lain

        // Mengubah ukuran dropdown lebih kecil
        languageSelector.setPreferredSize(new Dimension(80, 30)); // Lebar 80, tinggi 30 untuk dropdown lebih kecil

        // Mengubah panjang dropdown dengan mengatur popup size
        languageSelector.setMaximumRowCount(3); // Membatasi jumlah item yang muncul untuk panjang dropdown yang lebih pendek

        // Menyesuaikan warna dropdown agar sama dengan Leaderboard dan Profile
        languageSelector.setBackground(Color.WHITE); // Latar belakang dropdown
        languageSelector.setForeground(Color.decode("#ff0084")); // Warna teks dropdown agar serasi

        // Mengubah warna tombol drop-down
        UIManager.put("ComboBox.buttonBackground", Color.WHITE); 
        UIManager.put("ComboBox.buttonDarkShadow", Color.WHITE);
        UIManager.put("ComboBox.buttonHighlight", Color.WHITE);
        UIManager.put("ComboBox.buttonShadow", Color.WHITE);

        languageSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLanguage = (String) languageSelector.getSelectedItem();
                JOptionPane.showMessageDialog(null, "Bahasa yang dipilih: " + selectedLanguage);
            }
        });

        // Elemen Leaderboard
        JLabel leaderBoardLabel = createClickableLabel("Leaderboard", () -> 
            JOptionPane.showMessageDialog(this, "Fitur Leaderboard belum tersedia.")
        );

        // Elemen Profile
        JLabel profileLabel = createClickableLabel("Profile", () -> 
            JOptionPane.showMessageDialog(this, "Fitur Profile belum tersedia.")
        );

        // Tambahkan elemen ke Header Panel
        headerPanel.add(Box.createHorizontalGlue()); // Ruang di awal untuk mendorong elemen ke kanan
        headerPanel.add(languageLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Jarak kecil
        headerPanel.add(languageSelector);
        headerPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Jarak antar grup
        headerPanel.add(leaderBoardLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Jarak antar grup
        headerPanel.add(profileLabel);

        return headerPanel;
    }

    /**
     * Membuat panel utama dengan konten placeholder.
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel placeholderLabel = new JLabel("Area Konten (Belum Tersedia)", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Poppins", Font.BOLD, 24));

        contentPanel.add(placeholderLabel, BorderLayout.CENTER);
        return contentPanel;
    }

    /**
     * Utility untuk membuat label interaktif dengan action listener.
     */
    private JLabel createClickableLabel(String text, Runnable action) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Poppins", Font.BOLD, 16));
        label.setForeground(Color.decode("#ff0084"));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });
        return label;
    }
}
