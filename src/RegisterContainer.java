import api.Database;
import api.UserDAO;
import api.UserDAOImpl;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class RegisterContainer extends JPanel {

    private JTextField userNameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JTextField emailField = new JTextField();
    private UserDAO userDAO;

    public RegisterContainer() {
        Font poppinsFont = null;
        Font poppinsFontBold = null;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Regular.ttf"));
            poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Initialize userDAO
        try {
            userDAO = new UserDAOImpl(new Database().getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1440, 900));
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#4467df"));
        headerPanel.setPreferredSize(new Dimension(1440, 200));

        JLabel headerImage = new JLabel();
        headerImage.setIcon(new ImageIcon("image/header3.jpg"));
        headerImage.setHorizontalAlignment(JLabel.CENTER);
        headerImage.setVerticalAlignment(JLabel.TOP);
        headerPanel.add(headerImage, BorderLayout.CENTER);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setForeground(Color.decode("#d44c9c"));
        titleLabel.setFont(poppinsFontBold.deriveFont(30f));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(titleLabel);

        JLabel detailLabel = new JLabel("Please, fill in your details");
        detailLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        detailLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(detailLabel);

        // Email
        contentPanel.add(createInputPanel("Email", emailField, poppinsFont));

        // Username
        contentPanel.add(createInputPanel("Username", userNameField, poppinsFont));

        // Password
        contentPanel.add(createInputPanel("Password", passwordField, poppinsFont));
        

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        registerButton.setPreferredSize(new Dimension(390, 40));
        registerButton.setBackground(Color.decode("#d44c9c"));
        registerButton.setForeground(Color.WHITE);
        contentPanel.add(registerButton);

        // Back to login info
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel backLabel = new JLabel("Already have an account?");
        backLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 13f));
        backLabel.setForeground(Color.decode("#8c8c8c"));
        JButton loginButton = new JButton("Login Here");
        loginButton.setFont(poppinsFont.deriveFont(Font.BOLD, 13f));
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setForeground(Color.decode("#000000"));
        backPanel.add(backLabel);
        backPanel.add(loginButton);
        contentPanel.add(backPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Action Listeners
        registerButton.addActionListener(e -> RegisterBtnActionPerformed());
        loginButton.addActionListener(e -> {
            removeAll();
            add(new LoginContainer());
            revalidate();
            repaint();
        });
    }

    private JPanel createInputPanel(String labelText, JComponent inputField, Font font) {
        // Panel ini akan memastikan bahwa komponen-komponen di dalamnya terpusat secara horizontal
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Perubahan: FlowLayout.CENTER
        JLabel label = new JLabel(labelText);
        label.setFont(font.deriveFont(Font.BOLD, 14f));
        inputField.setFont(font.deriveFont(Font.BOLD, 12f));
        inputField.setPreferredSize(new Dimension(390, 30));
        panel.add(label);
        panel.add(inputField);
        return panel;
    }
    

    private void RegisterBtnActionPerformed() {
        String username = userNameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {
            Connection conn = userDAO.getConnection(); // Ambil koneksi dari userDAO
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username tidak boleh kosong", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email tidak boleh kosong", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password tidak boleh kosong", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String query = "SELECT * FROM user WHERE username = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username sudah ada", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    query = "INSERT INTO user (username, email, password, jmlh_attempt) VALUES (?, ?, ?, ?)";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, username);
                    pst.setString(2, email);
                    pst.setString(3, password);
                    pst.setInt(4, 0);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Register Berhasil", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Register Gagal. Silakan coba lagi.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}