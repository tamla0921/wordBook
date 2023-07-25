package wordBook;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class PopUp extends MouseAdapter{
    
    private JTable table;
    
    public PopUp(JTable table) {
        this.table = table;
        
    }
    
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
    }
}
