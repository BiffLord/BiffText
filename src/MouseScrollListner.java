import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseScrollListner implements MouseWheelListener {
    public JScrollBar scroll;
    public MouseScrollListner(JScrollBar scroll){
        this.scroll = scroll;
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll.setValue((int) (scroll.getValue()+e.getPreciseWheelRotation()));
        ((ScrollBarAdjustmentListner) scroll.getAdjustmentListeners()[0]).adjustValue();
    }
}
