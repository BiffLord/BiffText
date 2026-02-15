package net.biff;
import net.bifflib.GUI.Window;

import javax.swing.*;
import javax.swing.event.MenuListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;


public class Main{
    private static Font menuFont;
    public static void main(String[] args) {
        //This comment, wrote in this program, BY this program
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Font font;
        try (InputStream fontFile =Main.class.getResourceAsStream("/texgyretermes-regular.otf")){
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/texgyretermes-regular.otf")));
            if (fontFile == null){
                throw new NullPointerException();
            }
            font = new Font("texgyretermes-regular", Font.PLAIN, 14);
        } catch (IOException | FontFormatException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        menuFont = new Font("texgyretermes-regular", Font.PLAIN, 12);
        JFrame window = Window.makeWindow(800,700,"BiffText - Untitled",new ImageIcon(Main.class.getResource("/T.png")).getImage(), Color.WHITE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JTextArea textBox = new JTextArea(8,5);
        textBox.setText("Type Here: ㅤ");

        textBox.setFont(font);
        textBox.setTabSize(1);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        textBox.setMargin(new Insets(0,10,0,0));
        UndoManager UM = new UndoManager();
        textBox.getDocument().addUndoableEditListener(UM);
        var fMenuAction = new FileMenuActionListener(textBox,window);

        var wcl = new WindowClosingListener(window,fMenuAction,textBox);
        fMenuAction.addWindowClosingListener(wcl);
        window.addWindowListener(wcl);
        var eMenuAct = new EditMenuActionListener(textBox,UM);
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = menuMaker("File",'f');
        JMenu editMenu = menuMaker("Edit",'e');
        editMenu.addActionListener(eMenuAct);
        menu.add(fileMenu);
        menu.add(editMenu);




        fileMenu.add(menuItemMaker("Print",fMenuAction,'P'));
        fileMenu.add(menuItemMaker("Open",fMenuAction,'O'));
        fileMenu.add(menuItemMaker("Save",fMenuAction,'S'));
        fileMenu.add(menuItemMaker("Save As",fMenuAction,'E'));
        fileMenu.add(menuItemMaker("New",fMenuAction,'N'));
        editMenu.add(menuItemMaker("Undo",eMenuAct,'Z'));
        editMenu.add(menuItemMaker("Redo",eMenuAct,'Y'));
        window.add(menu, BorderLayout.NORTH);
        var distraction = new JRadioButton("distraction");
        window.add(distraction);
        distraction.requestFocusInWindow();
        fileMenu.requestFocusInWindow();
        JScrollPane scroll = new JScrollPane(textBox);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(100,100));
        if (args.length != 0){
            fMenuAction.open(args[0]);
        }
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
    private static JMenu menuMaker(String name, char accelerant){
        JMenu menu = new JMenu(name);
        menu.setMnemonic(accelerant);
        return menu;
    }
    private static JMenuItem menuItemMaker(String text,ActionListener actionListener, char accelerator){
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText(text);
        menuItem.setActionCommand(text);
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator, InputEvent.CTRL_DOWN_MASK));
        menuItem.setFont(menuFont);
        return  menuItem;
    }
}
