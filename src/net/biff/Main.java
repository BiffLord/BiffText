package net.biff;
import net.bifflib.GUI.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

public class Main{
    public static void main(String[] args) {
        //This comment, wrote in this program, BY this program
        boolean saved = false;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Font font = new Font("Times New Roman",Font.PLAIN, 14);
        try{
            InputStream fontFile = Main.class.getResourceAsStream("/texgyretermes-regular.otf");
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/texgyretermes-regular.otf")));
            if (fontFile == null){
                throw new NullPointerException();
            }
            font = new Font("texgyretermes-regular", Font.PLAIN, 14);
        } catch (IOException | FontFormatException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        JFrame window = Window.makeWindow(800,700,"Text Editor",new ImageIcon(Main.class.getResource("/T.png")).getImage(), Color.WHITE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JTextArea textBox = new JTextArea(8,5);
        textBox.setText("Type Here: ㅤ");

        textBox.setFont(font);
        textBox.setTabSize(1);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        textBox.setMargin(new Insets(0,10,0,0));
        var menuAction = new MenuActionListener(textBox,window);
        if (args.length != 0){
            menuAction.open(args[0]);
        }
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
        fileMenu.setFont(new Font("texgyretermes-regular", Font.PLAIN, 12));
        fileMenu.setMnemonic('F');

        menu.add(fileMenu);


        fileMenu.add(menuItemMaker("Print",menuAction,'P'));
        fileMenu.add(menuItemMaker("Open",menuAction,'O'));
        fileMenu.add(menuItemMaker("Save",menuAction,'S'));
        fileMenu.add(menuItemMaker("Save As",menuAction,'E'));

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
                if (textBox.getText().equals("Type Here: ㅤ")){
                    textBox.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textBox.getText().isEmpty()){
                    textBox.setText("Type Here: ㅤ");
                }
            }
        });
        window.setVisible(true);

    }
    private static JMenuItem menuItemMaker(String text,ActionListener actionListener, char accelerator){
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(text);
        menuItem.setActionCommand(text);
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator, InputEvent.CTRL_DOWN_MASK));
        menuItem.setFont(new Font("texgyretermes-regular", Font.PLAIN, 12));
        return  menuItem;
    }
}
