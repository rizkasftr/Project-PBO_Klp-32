import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TestContainer extends JFrame {
    private JComboBox<String> languageSelector;
    private JTextField typingField;
    private JLabel wordLabel;
    private JLabel instructionsLabel;
    private JLabel timerLabel; // Label untuk waktu
    private Timer gameTimer;
    private int correctWords = 0;
    private ArrayList<String> words;
    private boolean isTimerStarted = false; // Untuk memastikan timer hanya berjalan sekali
    private int seconds = 60; // Waktu dalam detik
    private javax.swing.Timer timer; // Timer untuk menghitung waktu
    private JButton restartButton; // Tombol untuk memulai permainan lagi

    public TestContainer(String username) {
        setTitle("Mulai Permainan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(username), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel languageLabel = new JLabel("Bahasa:");
        languageLabel.setFont(new Font("Poppins", Font.PLAIN, 16));

        String[] languages = {"Sasak", "Indonesia", "English"};
        languageSelector = new JComboBox<>(languages);
        languageSelector.setFont(new Font("Poppins", Font.PLAIN, 16));
        languageSelector.addActionListener(e -> loadWords());

        JLabel leaderBoardLabel = createClickableLabel("Leaderboard", () ->
            JOptionPane.showMessageDialog(this, "Fitur Leaderboard belum tersedia.")
        );

        JLabel profileLabel = createClickableLabel("Profile", () ->
            JOptionPane.showMessageDialog(this, "Fitur Profile belum tersedia.")
        );

        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(languageLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        headerPanel.add(languageSelector);
        headerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        headerPanel.add(leaderBoardLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        headerPanel.add(profileLabel);

        return headerPanel;
    }

    private JPanel createContentPanel(String username) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Timer label
        timerLabel = new JLabel("Waktu: 60 detik");
        timerLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        timerLabel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(timerLabel);

        // Kata target
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Poppins", Font.BOLD, 32));
        wordLabel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(wordLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Field untuk mengetik
        typingField = new JTextField();
        typingField.setFont(new Font("Poppins", Font.PLAIN, 24));
        typingField.setMaximumSize(new Dimension(400, 40));
        typingField.setAlignmentX(CENTER_ALIGNMENT);
        typingField.addActionListener(e -> {
            if (!isTimerStarted) {
                isTimerStarted = true;
                startTimer(username);  // Memulai timer ketika permainan dimulai
            }
            checkWord();  // Mengecek kata yang diketik
        });
        contentPanel.add(typingField);

        // Petunjuk
        instructionsLabel = new JLabel("Ketik kata di atas dan tekan Enter untuk memulai", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Poppins", Font.ITALIC, 16));
        instructionsLabel.setForeground(Color.GRAY);
        instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);
        contentPanel.add(instructionsLabel);

        // Tombol Mulai Lagi
        restartButton = new JButton("Mulai Lagi");
        restartButton.setFont(new Font("Poppins", Font.PLAIN, 16));
        restartButton.setAlignmentX(CENTER_ALIGNMENT);
       
        restartButton.setVisible(false);  // Tidak ditampilkan saat permainan dimulai
        contentPanel.add(restartButton);

        loadWords();
        return contentPanel;
    }

    private void startTimer(String username) {
        // Timer yang dimulai ketika permainan dimulai
        if (timer == null) {
            timer = new javax.swing.Timer(1000, e -> {
                seconds--;
                timerLabel.setText("Waktu: " + seconds + " detik");
                if (seconds <= 0) {
                    timer.stop();  // Hentikan timer ketika waktu habis
                    typingField.setEditable(false);  // Nonaktifkan input
                    restartButton.setVisible(true);  // Menampilkan tombol "Mulai Lagi"
                    new WpmResult(username, correctWords).setVisible(true);  // Menampilkan hasil
                }
            });
            timer.start();  // Mulai timer
        }
    }

    private void loadWords() {
        String selectedLanguage = (String) languageSelector.getSelectedItem();
        CsvReader reader = new CsvReader();
        switch (selectedLanguage) {
            case "Sasak":
                reader.readCSV("src/data/sasak.csv");
                break;
            case "Indonesia":
                reader.readCSV("src/data/stopwordbahasa.csv");
                break;
            case "English":
                reader.readCSV("src/data/english.csv");
                break;
        }
        words = reader.getRecords();
        nextWord();
    }

    private void nextWord() {
        if (words != null && !words.isEmpty()) {
            Random random = new Random();
            String randomWord = words.get(random.nextInt(words.size()));
            wordLabel.setText(randomWord);
        }
    }

    private void checkWord() {
        String typedWord = typingField.getText().trim();
        if (typedWord.equalsIgnoreCase(wordLabel.getText())) {
            correctWords++;
            typingField.setText("");
            nextWord();
        } else {
            JOptionPane.showMessageDialog(this, "Kata yang Anda ketik salah! Coba lagi.");
        }
    }

    

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
