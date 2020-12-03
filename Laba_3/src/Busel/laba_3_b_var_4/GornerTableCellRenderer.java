package Busel.laba_3_b_var_4;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);// Показывать только 5 знаков после запятой
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');// Установить в качестве разделителя дробной части точку, а не  запятую.
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);// Разместить надпись внутри панели
    }
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        String formattedDouble = "";// Преобразовать double в строку
        if (col == 1 || col == 0) {
            formattedDouble = formatter.format(value);
            label.setText(formattedDouble);
        }
        if (col==1 && needle!=null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.PINK);
        } else if(col == 0) {
            panel.setBackground(Color.WHITE);
        }
        return panel;
    }
    public void setNeedle(String needle) {
        this.needle = needle;}
    }
