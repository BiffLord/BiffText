import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ScrollBarAdjustmentListner implements AdjustmentListener {
    JTextArea textbox;
    int yBase;
    JScrollBar scroll;
    public ScrollBarAdjustmentListner(JTextArea text, int yBase, JScrollBar scroll){
        this.textbox = text;
        this.yBase = yBase;
        this.scroll = scroll;
    }
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        textbox.setBounds(textbox.getX(), yBase-scroll.getValue()*2, textbox.getWidth(), textbox.getHeight());
    }
    public void adjustValue(){
        adjustmentValueChanged(new AdjustmentEvent(scroll,1,1,1));
    }
}
