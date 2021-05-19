package lol.ui;
import javax.swing.*;
import java.awt.*;

public class WinnerScreen {
    private JFrame frame = new JFrame();

    public WinnerScreen(String winner) {

        // the panel with the button and text
        JLabel label = new JLabel("The winner is team " + winner + "!");
        label.setIconTextGap(10);
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(150, 150, 150, 150));
        panel.setLayout(new GridLayout(0, 1));
        if(winner.equals("Blue")) {
            panel.setBackground(Color.blue);
        }
        else {
            panel.setBackground(Color.red);
        }
        panel.add(label);



        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Winner Screen");
        frame.pack();
        frame.setVisible(true);
    }
}
