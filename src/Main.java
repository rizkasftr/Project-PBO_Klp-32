import javax.swing.*;
import java.awt.*;


public class Main {
   public static void main(String[] args) {

      LoginContainer loginContainer = new LoginContainer();
      JPanel panel = new JPanel();
      JFrame frame = new JFrame("Typing Games");

      panel.setLayout(new GridBagLayout());
      panel.add(loginContainer);
      panel.setBackground(Color.WHITE);
      frame.add(panel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(750, 600);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);

   }
}