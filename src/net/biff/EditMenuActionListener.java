package net.biff;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenuActionListener implements ActionListener {
    JTextArea text;
    UndoManager UM;
    public EditMenuActionListener(JTextArea text,UndoManager UM) {
        this.text = text;
        this.UM=UM;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Undo":
                if (UM.canUndo()){
                    UM.undo();
                }
                break;
            case "Redo":
                if (UM.canRedo()){
                    UM.redo();
                }
        }
    }
}
