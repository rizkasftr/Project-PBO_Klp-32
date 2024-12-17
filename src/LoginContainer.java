import api.Database;
import api.UserDAO;
import api.UserDAOImpl;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import javax.swing.*;

public class LoginContainer extends JPanel {

    JTextField userNameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    UserDAO userDAO;

    LoginContainer() {
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

        // Initialize userDAO
        try {
            userDAO = new UserDAOImpl(new Database().getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(300, 200));

        JLabel headerImage = new JLabel();
        headerImage.setIcon(new ImageIcon("image/header3.jpg"));
        headerImage.setHorizontalAlignment(JLabel.CENTER);
        headerImage.setVerticalAlignment(JLabel.TOP);
        headerPanel.add(headerImage, BorderLayout.CENTER);

        // Content Panel dengan GridLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10));

        // Login title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.decode("#d44c9c"));
        titleLabel.setFont(poppinsFontBold.deriveFont(30f));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(titleLabel);

        // Detail label
        JLabel detailLabel = new JLabel("Please, enter your detail");
        detailLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        detailLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(detailLabel);

        // Username input
        contentPanel.add(createInputPanel("Username", userNameField, poppinsFont));

        // Password input
        contentPanel.add(createInputPanel("Password ", passwordField, poppinsFont));


        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        loginButton.setPreferredSize(new Dimension(390, 40));
        loginButton.setBackground(Color.decode("#d44c9c"));
        loginButton.setForeground(Color.WHITE);
        contentPanel.add(loginButton);
        add(contentPanel, BorderLayout.CENTER);

        // Account info
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel accountLabel = new JLabel("Not have an account?");
        accountLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 13f));
        accountLabel.setForeground(Color.decode("#8c8c8c"));
        JButton registerButton = new JButton("Register Here");
        registerButton.setFont(poppinsFont.deriveFont(Font.BOLD, 13f));
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setForeground(Color.decode("000000"));
        accountPanel.add(accountLabel);
        accountPanel.add(registerButton);
        contentPanel.add(accountPanel);

        // Adding panels to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Action listeners
        loginButton.addActionListener(e -> {
            // Action for login button
            LoginBtnActionPerformed(e);
        });
        registerButton.addActionListener(e -> {
            removeAll();
            add(new RegisterContainer());
            revalidate();
            repaint();
        });
    }

    private JPanel createInputPanel(String labelText, JComponent inputField, Font font) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel(labelText);
        label.setFont(font.deriveFont(Font.BOLD, 14f));
        inputField.setFont(font.deriveFont(Font.BOLD, 12f));
        inputField.setPreferredSize(new Dimension(390, 30));
        panel.add(label);
        panel.add(inputField);
        return panel;
    }

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String username, passwordDB, query, passDB = null;

        int notFound = 0;

        try {
            if ("".equals(userNameField.getText())) {
                JOptionPane.showMessageDialog(new JFrame(), "Username tidak boleh kosong", "Error",
                        JOptionPane.WARNING_MESSAGE);
            } else if ("".equals(new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(new JFrame(), "Password tidak boleh kosong", "Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                username = userNameField.getText();
                passwordDB = new String(passwordField.getPassword());

                query = "SELECT * FROM user WHERE username = ? AND password = ?";
                PreparedStatement pst = userDAO.getConnection().prepareStatement(query); // Perbaiki baris ini
                pst.setString(1, username);
                pst.setString(2, passwordDB);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    passDB = rs.getString("password");
                    notFound = 1;
                }
                if (notFound == 1 && Objects.equals(passwordDB, passDB)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Login Berhasil", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    new TestContainer(username);
                    SwingUtilities.getWindowAncestor(LoginContainer.this).dispose();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Username atau Password salah", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}