package rfikt.busel.laba_3;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainFrame extends JFrame{
    private static final int WIGHT=700;
    private static final int HEIGHT=500;
     private Double[] coefficients;
     private JFileChooser fileChooser=null; //объект диалогового окна
    //элементы меню
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    // Поля ввода для считывания значений переменных
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    //визуализатор ячеек таблицы
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel date; //результат вычеслений

    public MainFrame (Double[] coefficients){
        //вызов конструктора Frame
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients=coefficients;
        setSize(WIDTH,HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,  // оцетриривание окна приложения
                (kit.getScreenSize().height - HEIGHT)/2);
        //создание меню
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);  //сделали меню глвным в приложении
        JMenu fileMenu=new JMenu("Файл");
        menuBar.add(fileMenu);  // добавили файл в главное приложение
        JMenu tableMenu=new JMenu("Таблица");
        menuBar.add(tableMenu);
        


    }

}
