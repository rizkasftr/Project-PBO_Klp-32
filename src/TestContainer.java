import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import api.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestContainer extends JFrame {
    private JComboBox<String> languageSelector;
    private JTextField typingField;
    private JLabel wordLabel;
    private JLabel instructionsLabel;
    private JLabel timerLabel;
    private Timer gameTimer;
    private int correctWords = 0;
    private ArrayList<String> words;
    private boolean isTimerStarted = false;
    private JButton restartButton;
    private Thread timerThread;
    private Timer timer;
    private String username;

    public TestContainer(String username) {
        this.username = username;
        setTitle("Mulai Permainan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);

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

        JLabel leaderBoardLabel = createClickableLabel("Leaderboard", this::showLeaderBoard);
        JLabel profileLabel = createClickableLabel("Profile", this::showProfile);

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

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Timer label
        timer = new Timer(1); // Timer dengan waktu awal 1 menit
        timerLabel = timer.getLabel();
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
                startGame();
            }
            checkWord();
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
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        contentPanel.add(restartButton);

        loadWords();
        return contentPanel;
    }

    private void startGame() {
        if (timerThread == null || !timerThread.isAlive()) {
            timer.start();
            timerThread = new Thread(() -> {
                while (!timer.hasExpired()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                SwingUtilities.invokeLater(this::endGame);
            });
            timerThread.start();
        }
    }

    private void endGame() {
        typingField.setEditable(false);
        restartButton.setVisible(true);
        timer.stopTimer();

        int wpm = calculateWPM();
        saveScoreToDatabase(wpm); // Simpan skor ke database

        // Tampilkan skor WPM
        new WpmResult(username, wpm);
    }

    private void restartGame() {
        correctWords = 0;
        isTimerStarted = false;
        typingField.setEditable(true);
        typingField.setText("");
        restartButton.setVisible(false);
        loadWords();
        timer.restart();
        startGame();
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
        }
    }

    private int calculateWPM() {
        return correctWords; // Menghitung jumlah kata benar sebagai WPM
    }

    private void saveScoreToDatabase(int wpm) {
        try {
            String query = "INSERT INTO score (id_user, score, no_attempt) VALUES ((SELECT id_user FROM user WHERE username = ?), ?, 1)";
            PreparedStatement pst = Database.database.prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2, wpm);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan skor ke database: " + e.getMessage());
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

    private void showLeaderBoard() {
        new LeaderBoard();
    }

    private void showProfile() {
        new ProfileContainer(username);
    }
}
