import javax.swing.*;

public class WpmResult extends JFrame {
    public WpmResult(String username, int wpm) {
        setTitle("Hasil Permainan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel resultLabel = new JLabel("Hi " + username + ", WPM Anda adalah:");
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        JLabel wpmLabel = new JLabel(String.valueOf(wpm));
        wpmLabel.setFont(wpmLabel.getFont().deriveFont(24f));
        wpmLabel.setAlignmentX(CENTER_ALIGNMENT);

        JButton closeButton = new JButton("Tutup");
        closeButton.setAlignmentX(CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());

        panel.add(resultLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(wpmLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(closeButton);

        add(panel);
        setVisible(true);
    }
}