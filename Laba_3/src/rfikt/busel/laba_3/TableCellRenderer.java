package rfikt.busel.laba_3;

import javax.swing.*;
import java.awt.*;

public interface TableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object
           value, boolean isSelected, boolean hasFocus, int row, int col);
}
