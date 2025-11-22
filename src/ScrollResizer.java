import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScrollResizer extends KeyAdapter {
    JScrollBar scroll;
    JTextArea text;
    public ScrollResizer(JScrollBar scroll, JTextArea text){
        super();
        this.scroll = scroll;
        this.text = text;
    }
    @Override
    public void keyTyped(KeyEvent e){
        System.out.println(text.getHeight());
        System.out.println(text.getWidth());
    }
}
