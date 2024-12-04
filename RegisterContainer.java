<<<<<<< HEAD
import javax.swing.*;
import api.Database;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterContainer extends JPanel {

    private JTextField userNameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JTextField emailField = new JTextField();

    public RegisterContainer() {
        Font poppinsFont = null;
        Font poppinsFontBold = null;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Regular.ttf"));
            poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
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
        headerImage.setIcon(new ImageIcon("image/header3.jpeg"));
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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
            Connection conn = Database.database; // Ambil koneksi langsung
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
=======
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RegisterContainer extends JPanel {

    private JTextField userNameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JPasswordField confirmPasswordField = new JPasswordField();

    RegisterContainer() {
        Font poppinsFont = null;
        Font poppinsFontBold = null;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Regular.ttf"));
            poppinsFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\font\\Poppins-Bold.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 500));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Register Panel
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(poppinsFontBold.deriveFont(30f));
        registerPanel.add(registerLabel);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setPreferredSize(new Dimension(400, 50));

        // Username Field
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userNamePanel.setOpaque(false);
        userNamePanel.setPreferredSize(new Dimension(400, 60));
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        userNameField.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        userNameField.setPreferredSize(new Dimension(390, 30));
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);

        // Password Field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setOpaque(false);
        passwordPanel.setPreferredSize(new Dimension(400, 62));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        passwordField.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        passwordField.setPreferredSize(new Dimension(390, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Confirm Password Field
        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        confirmPasswordPanel.setOpaque(false);
        confirmPasswordPanel.setPreferredSize(new Dimension(400, 100));
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        confirmPasswordField.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        confirmPasswordField.setPreferredSize(new Dimension(390, 30));
        confirmPasswordPanel.add(confirmPasswordLabel);
        confirmPasswordPanel.add(confirmPasswordField);

        // Register Button
        JPanel registerButtonPanel = new JPanel();
        registerButtonPanel.setLayout(new FlowLayout());
        registerButtonPanel.setOpaque(false);
        registerButtonPanel.setPreferredSize(new Dimension(400, 50));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        registerButton.setPreferredSize(new Dimension(390, 40));
        registerButton.setBackground(Color.decode("#3465ff"));
        registerButton.setForeground(Color.WHITE);
        registerButtonPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Register logic (validate password, etc.)
                System.out.println("Register clicked");
            }
        });

        add(registerPanel);
        add(userNamePanel);
        add(passwordPanel);
        add(confirmPasswordPanel);
        add(registerButtonPanel);
    }
}
>>>>>>> 34d26d192b32abd07bee9ec6c4799fd8018f3688
