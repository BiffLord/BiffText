package net.biff;

import net.bifflib.files.FileDownloader;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class WindowClosingListener extends WindowAdapter {
    FileMenuActionListener menu;
    JTextArea text;
    JFrame window;
    boolean closing=false;
    public boolean cancel = false;
    public WindowClosingListener(JFrame window, FileMenuActionListener menu, JTextArea text) {
        this.menu=menu;
        this.text=text;
        this.window=window;
    }
    @Override
    public void windowClosing(WindowEvent e) {
        closing= true;
        if (menu.filePath==null && text.getText().equals("Type Here: ㅤ")){
            finish();return;
        } else if (text.getText().equals(FileDownloader.loadToString(menu.filePath))){
            finish();return;
        }
        int button = JOptionPane.showOptionDialog(window,
                "Your work has not been saved. Do you want to save it?",
                "Save Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null);
        if (button == 1) {
            finish();return;
        }
        else if (button ==0){
            menu.actionPerformed(new ActionEvent(window, 1, "Save"));
            finish();return;
        }
        cancel = true;
        closing = false;


    }
    private void finish(){
        if (menu.changing){
            closing = false;
            return;
        }
        closing=false;
        System.exit(0);
    }
}
