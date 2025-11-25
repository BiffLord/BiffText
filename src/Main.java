import net.bifflib.GUI.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Main{
    public static void main(String[] args) {
        //This comment, wrote in this program, BY this program
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

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        menu.add(fileMenu);
        fileMenu.add(new JMenuItem("Print"));
        var openButton = new JMenuItem("Open");
        openButton.setActionCommand("open file");
        JTextArea textBox = new JTextArea(8,5);
        textBox.setText("Type Here: ");
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        textBox.setFont(font);
        textBox.setTabSize(1);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        var menuAction = new MenuActionListener(textBox);
        openButton.addActionListener(menuAction);
        fileMenu.add(openButton);
        var saveButton = new JMenuItem("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(menuAction);
        fileMenu.add(saveButton);
        var saveAsButton = new JMenuItem("Save As");
        saveAsButton.setActionCommand("save as");
        saveAsButton.addActionListener(menuAction);
        fileMenu.add(saveAsButton);

        window.add(menu, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(textBox);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scroll.setPreferredSize(new Dimension(100,100));
        scroll.setVisible(true);
        window.add(scroll);
        scroll.getVerticalScrollBar().requestFocusInWindow();



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
        window.setVisible(true);

    }
}
