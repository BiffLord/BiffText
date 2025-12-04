package net.biff;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.bifflib.files.FileDownloader;
import net.bifflib.files.FileUploader;

public class MenuActionListener implements ActionListener {
    JTextArea text;
    String filePath = null;
    JFrame window;
    public MenuActionListener(JTextArea text, JFrame window){
        this.text = text;
        this.window = window;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "open file" -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    open();

                }
            }
            case "save" -> {
                if (filePath != null) {
                    FileUploader.uploadFile(filePath, text.getText());
                } else {
                    actionPerformed(new ActionEvent(e.getSource(), e.getID(), "save as"));
                }
            }
            case "save as" -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        FileUploader.uploadFile(filePath, text.getText());
                    } catch (RuntimeException RE) {
                        makeNewFile();
                    }

                }
            }
            case "print" -> {
                try {
                    text.print();
                } catch (PrinterException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    private void makeNewFile(){
        try {
            new File(filePath).createNewFile();
            FileUploader.uploadFile(filePath,text.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void open(){
        StringBuilder contents = new StringBuilder();
        List<String> file = FileDownloader.loadFile(filePath);
        file.forEach(x -> contents.append(x).append("\n"));
        text.setText(contents.toString());
    }
    public void open(String filePath){
        this.filePath = filePath;
        open();
    }
}
