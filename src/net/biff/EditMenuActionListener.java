package net.biff;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenuActionListener implements ActionListener {
    JTextArea text;
    UndoManager UM;
    int start;
    String select;
    public EditMenuActionListener(JTextArea text,UndoManager UM) {
        this.text = text;
        this.UM=UM;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Undo"-> {
                if (UM.canUndo()) {
                    UM.undo();
                }
            }
            case "Redo"-> {
                if (UM.canRedo()) {
                    UM.redo();
                }
            }
            case "Uppercase"-> {
                selectedText();
                caseChange(String::toUpperCase);
                unselect();
            }
            case "Lowercase"-> {
                selectedText();
                caseChange(String::toLowerCase);
                unselect();
            }
            case "Capitalize"-> {
                selectedText();
                caseChange(x -> {
                    String[] phrase = x.split(" ");
                    StringBuilder sb = new StringBuilder();
                    for (String word:phrase){
                        if (word.matches("[A-Za-z]")){
                            sb.append(word.toUpperCase()).append(" ");
                            continue;
                        }else if (word.matches("[a-zA-Z][a-zA-Z1-90\\-\\\\.?/!@#$%^&*(){}\\[\\]|\"'\n\t]+")){

                            sb.append(word.substring(0,1).toUpperCase()).append(word.substring(1)).append(" ");

                            continue;
                        } else if (word.matches("[1-90\\-\\\\.?/!@#$%^&*(){}\\[\\]|\"'\n\t]")){
                            sb.append(word).append(" ");
                            continue;
                        }
                        boolean found = false;
                        for (char letter : word.toCharArray()){
                            if ("abcdefghijklmnop".indexOf(letter) == -1 || found){
                                sb.append(letter);continue;}
                            found = true;
                            sb.append(String.valueOf(letter).toUpperCase());
                        }
                        sb.append(" ");

                    }
                    String line = sb.toString();
                    return line.substring(0,line.length()-2);
                });
                unselect();
            }
            case "Title Case"->{
                selectedText();
                selectedText();
                caseChange(x -> {
                    String[] phrase = x.split(" ");
                    StringBuilder sb = new StringBuilder();
                    String[] badWords = new String[]{"and","as","at","but","by","for","if","in",
                    "nor","off","of","on","or","so","to","yet"};

                    for (String word:phrase){
                        boolean match = false;
                        for (String bad : badWords){
                            if (word.toLowerCase().matches(bad+"[.,\\-;/]?")){
                                sb.append(word.toLowerCase()).append(" ");
                                match = true;
                                break;
                            }
                        }
                        if (word.matches("a[,\\-;/]?")){
                            sb.append(word.toLowerCase()).append(" ");
                            match = true;
                        }
                        if (match){continue;}
                        if (word.matches("[A-Za-z]")){
                            sb.append(word.toUpperCase()).append(" ");
                            continue;
                        }else if (word.matches("[a-zA-Z][a-zA-Z1-90\\-\\\\.?/!@#$%^&*(){}\\[\\]|\"'\n\t]+")){

                            sb.append(word.substring(0,1).toUpperCase()).append(word.substring(1)).append(" ");

                            continue;
                        } else if (word.matches("[1-90\\-\\\\.?/!@#$%^&*(){}\\[\\]|\"'\n\t]")){
                            sb.append(word).append(" ");
                            continue;
                        }
                        boolean found = false;
                        for (char letter : word.toCharArray()){
                            if ("abcdefghijklmnop".indexOf(letter) == -1 || found){
                                sb.append(letter);continue;}
                            found = true;
                            sb.append(String.valueOf(letter).toUpperCase());
                        }
                        sb.append(" ");

                    }
                    String line = sb.toString();
                    unselect();
                    return line.substring(0,line.length()-2);
                });
            }
            case "Sentence Case"->{
                selectedText();

                caseChange(x->{
                    StringBuilder sb = new StringBuilder();
                    String[] lines = x.split("\n");
                    for (String line :lines){
                        String[] sentences = line.split("\\.( )?");
                        for (String sentence : sentences){
                            if (!sentence.isEmpty()) {
                                sb.append(sentence.substring(0, 1).toUpperCase());
                                if (sentence.length() > 1) {
                                    sb.append(sentence.substring(1));
                                }
                                sb.append(". ");
                            }
                        }
                        sb.append("\n");
                    }
                    String changed = sb.toString();
                    return changed.substring(0,changed.length()-2);
                });
            }
        }
    }
    private void selectedText(){
        start = text.getSelectionStart();
        select = text.getSelectedText();
    }
    private void unselect(){
        start = 0;
        select = null;
    }
    private void caseChange(StringChanger sc){
        String contents = text.getText();
        if (select == null){return;}
        String start = contents.substring(0,this.start);
        String changed = sc.change(contents.substring(this.start,this.start+select.length()));
        String end = contents.substring(this.start+select.length());
        text.setText(start+changed+end);
        //text.setCaretPosition(this.start+select.length()-1);
    }
    @FunctionalInterface
    interface StringChanger{
        public String change(String x);
    }
}
