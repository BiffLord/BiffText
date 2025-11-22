import net.bifflib.GUI.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main{
    public static void main(String[] args) {
        boolean saved = false;

        JFrame window = Window.makeWindow(800,700,"Text Editor",new ImageIcon(Main.class.getResource("T.png")).getImage(), Color.WHITE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane saveOrExit = new JOptionPane("Your Work has not been saved. Do you want to save it?", JOptionPane.INFORMATION_MESSAGE,JOptionPane.YES_NO_CANCEL_OPTION);
                int button = JOptionPane.showOptionDialog(window,
                        "Your Work has not been saved. Do you want to save it?",
                        "Save Warning",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null);
                if (button == 1) {
                    System.exit(0);
                }
            }
        });
        JTextArea textBox = new JTextArea("Type Here: ");
        JScrollBar scroll = new JScrollBar(JScrollBar.VERTICAL,0,5,0,100);
        var scrollBarAdjuster = new ScrollBarAdjustmentListner(textBox,textBox.getY(),scroll);
        scroll.addAdjustmentListener(scrollBarAdjuster);
        window.add(scroll, BorderLayout.EAST);

        window.addMouseWheelListener(new MouseScrollListner(scroll));

        JRadioButton distraction = new JRadioButton("Distracting you!");
        distraction.requestFocusInWindow();
        window.add(distraction);
        textBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textBox.getText().equals("Type Here: ")){
                    textBox.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textBox.getText().isEmpty()){
                    textBox.setText("Type Here: ");
                }
            }
        });

        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        textBox.setFont(font);
        textBox.setTabSize(4);
        textBox.setLineWrap(true);
        window.add(textBox);
        window.setVisible(true);

    }
}
