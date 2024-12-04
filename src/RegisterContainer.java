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
