package rfikt.busel.laba_3;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer{
    private String needle=null;  // искомое значение

    public void setNbeedle(String nbeedle) {
        this.needle = needle;
    }

    private JPanel panel=new JPanel();
    private JLabel label=new JLabel();

    public DecimalFormat formatter=(DecimalFormat) NumberFormat.getInstance();
    public GornerTableCellRenderer(){
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        formatter.setMaximumFractionDigits(5);// Показывать только 5 знаков после запятой
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int col) {
        String formattedDouble=formatter.format(value);  //преобразование числа в строку
        label.setText(formattedDouble); // Установить текст надписи равным строковому представлению числа
        if(col==1 && needle!=null && needle.equals(formattedDouble) ){
            panel.setBackground(Color.PINK);
        }
        else{panel.setBackground(Color.WHITE);}
        return panel;
    }
}
