import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI() {
        JFrame frame = new JFrame();

        JButton button = new JButton("Start Game");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,20,40));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);

        frame.add(panel, BorderLayout. CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("WarGameWorld");
        frame.pack();
        frame.setVisible(true);
    }
}
