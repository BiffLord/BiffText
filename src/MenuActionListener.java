import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.bifflib.files.FileDownloader;
import net.bifflib.files.FileUploader;

public class MenuActionListener implements ActionListener {
    JTextArea text;
    String filePath = null;
    public MenuActionListener(JTextArea text){
        this.text = text;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open file")){
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
                filePath = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(filePath);
                StringBuilder contents = new StringBuilder();
                List<String> file = FileDownloader.loadFile(filePath);
                file.forEach(x->contents.append(x).append("\n"));
                text.setText(contents.toString());

            }
        }
        else if (e.getActionCommand().equals("save")){
            if (filePath != null){
                net.bifflib.files.FileUploader.uploadFile(filePath,text.getText());
            } else{
                actionPerformed(new ActionEvent(e.getSource(), e.getID(), "save as"));
            }
        }

        else if (e.getActionCommand().equals("save as")){
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
                filePath = chooser.getSelectedFile().getAbsolutePath();
                try{
                    FileUploader.uploadFile(filePath, text.getText());
                } catch (RuntimeException RE){
                    makeNewFile();
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
}
