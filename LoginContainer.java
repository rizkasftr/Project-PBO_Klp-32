import javax.swing.*;
<<<<<<< HEAD

import api.Database;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
=======
import java.awt.*;
import java.io.File;
import java.io.IOException;
>>>>>>> 34d26d192b32abd07bee9ec6c4799fd8018f3688

public class LoginContainer extends JPanel {

    JTextField userNameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

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

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1440, 900));  // Menyesuaikan ukuran panel agar lebih tinggi
        setLayout(new BorderLayout());  // Menggunakan BorderLayout untuk menempatkan header di atas

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode("#4467df"));
        headerPanel.setPreferredSize(new Dimension(1440, 200));  // Memastikan header memiliki tinggi yang cukup

        JLabel headerImage = new JLabel();
        headerImage.setIcon(new ImageIcon("image/header3.jpeg")); // Ganti dengan path gambar Anda
        headerImage.setHorizontalAlignment(JLabel.CENTER);
        headerImage.setVerticalAlignment(JLabel.TOP);  // Menjaga header tetap di atas
        headerPanel.add(headerImage, BorderLayout.CENTER);

        // Content Panel dengan GridLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10));  // GridLayout dengan 1 kolom dan jarak antar baris

        // Login title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.decode("#d44c9c"));
        titleLabel.setFont(poppinsFontBold.deriveFont(30f));
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Memastikan judul berada di tengah
        contentPanel.add(titleLabel);

        // Detail label
        JLabel detailLabel = new JLabel("Please, enter your detail");
        detailLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        detailLabel.setHorizontalAlignment(JLabel.CENTER);  // Memastikan detail label berada di tengah
        contentPanel.add(detailLabel);

        // Username input
        JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to left
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        userNameField.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        userNameField.setPreferredSize(new Dimension(390, 30));
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);
        contentPanel.add(userNamePanel);

        // Password input
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align to left
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        passwordField.setFont(poppinsFont.deriveFont(Font.BOLD, 12f));
        passwordField.setPreferredSize(new Dimension(390, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        contentPanel.add(passwordPanel);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        loginButton.setPreferredSize(new Dimension(390, 40));
        loginButton.setBackground(Color.decode("#d44c9c"));
        loginButton.setForeground(Color.WHITE);
        contentPanel.add(loginButton);

        // Account info
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Align to center
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
<<<<<<< HEAD
            LoginBtnActionPerformed(e);
=======
>>>>>>> 34d26d192b32abd07bee9ec6c4799fd8018f3688
        });
        registerButton.addActionListener(e -> {
            removeAll();
            add(new RegisterContainer());
            revalidate();
            repaint();
        });
    }
<<<<<<< HEAD

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
              PreparedStatement pst = Database.database.prepareStatement(query);
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
=======
}
>>>>>>> 34d26d192b32abd07bee9ec6c4799fd8018f3688
