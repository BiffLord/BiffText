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
        JTextArea textBox = new JTextArea(8,5);
        textBox.setText("Type Here: ");
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        textBox.setFont(font);
        textBox.setTabSize(1);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        textBox.setMargin(new Insets(0,10,0,0));
        var menuAction = new MenuActionListener(textBox,window);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
                else if (button ==0){
                    menuAction.actionPerformed(new ActionEvent(window, 1, "save"));
                    System.exit(0);
                }

            }
        });

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        menu.add(fileMenu);
        var printButton = new JMenuItem("Print");
        printButton.setActionCommand("print");
        printButton.addActionListener(menuAction);
        printButton.setAccelerator(KeyStroke.getKeyStroke('P', ActionEvent.CTRL_MASK));
        fileMenu.add(printButton);
        var openButton = new JMenuItem("Open");
        openButton.setActionCommand("open file");

        openButton.addActionListener(menuAction);
        openButton.setAccelerator(KeyStroke.getKeyStroke('O', ActionEvent.CTRL_MASK));
        fileMenu.add(openButton);
        var saveButton = new JMenuItem("Save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(menuAction);
        saveButton.setAccelerator(KeyStroke.getKeyStroke('S', ActionEvent.CTRL_MASK));
        fileMenu.add(saveButton);
        var saveAsButton = new JMenuItem("Save As");
        saveAsButton.setActionCommand("save as");
        saveAsButton.addActionListener(menuAction);
        saveAsButton.setAccelerator(KeyStroke.getKeyStroke('E', ActionEvent.CTRL_MASK));
        fileMenu.add(saveAsButton);
        window.add(menu, BorderLayout.NORTH);
        var distraction = new JRadioButton("distraction");
        window.add(distraction);
        distraction.requestFocusInWindow();
        fileMenu.requestFocusInWindow();
        JScrollPane scroll = new JScrollPane(textBox);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scroll.setPreferredSize(new Dimension(100,100));
        scroll.setVisible(true);
        window.add(scroll);



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
