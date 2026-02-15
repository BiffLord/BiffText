package net.biff;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import net.bifflib.files.FileDownloader;
import net.bifflib.files.FileUploader;

public class FileMenuActionListener implements ActionListener {
    JTextArea text;
    String filePath = null;
    JFrame window;
    public boolean changing = false;
    public boolean making = false;
    WindowClosingListener wcl;
    public FileMenuActionListener(JTextArea text, JFrame window){
        this.text = text;
        this.window = window;
    }
    public void addWindowClosingListener(WindowClosingListener wcl){
        this.wcl=wcl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open" -> {
                changing = true;
                wcl.windowClosing(new WindowEvent(window,0));
                //while (wcl.closing){}
                if (wcl.cancel){
                    changing = false;
                    wcl.cancel = false;
                    return;
                }
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    window.setTitle("BiffText - "+chooser.getSelectedFile());
                    open();
                }
                changing = false;
            }
            case "Save" -> {
                if (filePath != null) {
                    String textValue;
                    FileUploader.uploadFile(filePath, ((textValue = text.getText()).equals("Type Here: ㅤ")) ? "":textValue);
                } else {
                    actionPerformed(new ActionEvent(e.getSource(), e.getID(), "Save As"));
                }
            }
            case "Save As" -> {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                    String textValue;
                    try {
                        FileUploader.uploadFile(filePath, ((textValue = text.getText()).equals("Type Here: ㅤ")) ? "":textValue);
                    } catch (RuntimeException RE) {
                        makeNewFile();
                    }
                    window.setTitle("BiffText - "+chooser.getSelectedFile());

                }
            }
            case "Print" -> {
                try {
                    text.print();
                } catch (PrinterException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case "New" ->{
                changing = true;
                wcl.windowClosing(new WindowEvent(window,0));
                if (wcl.cancel){
                    changing = false;
                    wcl.cancel = false;
                    return;
                }
                filePath = null;
                window.setTitle("BiffText - Untitled");
                text.setText("");
                changing = false;
            }
            case "Copy File"->{
                System.out.println("yo");
                changing = true;
                wcl.windowClosing(new WindowEvent(window,0));
                if (wcl.cancel){
                    changing = false;
                    wcl.cancel = false;
                    return;
                }
                if (filePath == null){
                    JDialog warning = new JDialog(window);
                    JTextArea warn = new JTextArea("You can't copy a non-existant file");
                    warn.setFocusable(false);
                    warning.setTitle("Warning");
                    warning.setSize(400,100);
                    warning.add(warn, BorderLayout.CENTER);
                    warning.setModal(true);
                    warning.setVisible(true);
                    changing = false;
                    return;
                }
                String[] file = filePath.split("\\\\");
                filePath = null;
                window.setTitle("BiffText - "+file[file.length-1]+" (Copy)");
                changing = false;
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
        text.setText(FileDownloader.loadToString(filePath));
        window.setTitle("BiffText - "+filePath);
    }
    public void open(String filePath){
        this.filePath = filePath;
        open();
    }
}
