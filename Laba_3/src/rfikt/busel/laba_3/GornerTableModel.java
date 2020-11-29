package rfikt.busel.laba_3;
import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {

    //переменные для размера таблицы и тд
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double[] coefficients,Double from,Double to,Double step ){
        this.from=from;
        this.to=to;
        this.step=step;
        this.coefficients=coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getStep() {
        return step;
    }

    public void setTo(Double to) {
        this.to = to;
    }

    @Override
    public int getRowCount() { // число строк
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }

    @Override
    public int getColumnCount() { //число столбцов
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double x=from + step*rowIndex;
        if(columnIndex==2){return x;}
        else{
            Double result=0.0;
            for(int i=0;i<rowIndex;i++)
            {
                result+=coefficients[i]*Math.pow(x,i);
            }
            return result;
        }
    }
    //добавляем заглушки
    @Override
    public Class<?> getColumnClass(int col){
        return Double.class;
    }

    @Override
    public String getColumnName(int col){   //название первого и второго столбца
        switch (col){
            case 0:
                return "Значение Х";
            default:
                return "Значение многочлена";
        }
    }

}
