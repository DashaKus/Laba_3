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
    private static final int WIDTH=700;
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
    private GornerTableModel data; //результат вычеслений


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

        Action saveToTextAction = new AbstractAction("Сохранить в файл") {
            //@Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());

            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToTextAction);

        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        // По умолчанию пункт меню является недоступным (данных ещѐ нет)
        saveToTextMenuItem.setEnabled(false);

        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
        public void actionPerformed(ActionEvent event){
            String value =
                    JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска",
                            "Поиск значения", JOptionPane.QUESTION_MESSAGE);
            renderer.setNeedle(value);
            getContentPane().repaint(); // Обновить таблицу
        }
    };
        searchValueMenuItem = tableMenu.add(searchValueAction); //добавление действия в меню таблицы
        searchValueMenuItem.setEnabled(false);   //недоступно
        JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
        JLabel labelForTo = new JLabel("до:");
        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
        JLabel labelForStep = new JLabel("с шагом:");

        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));

        hboxRange.add(labelForTo);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));

        hboxRange.add(textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());
// Установить предпочтительный размер области равным удвоенному
// минимальному, чтобы при компоновке область совсем не сдавили
        hboxRange.setPreferredSize(new Dimension(

        new Double(hboxRange.getMaximumSize().getWidth()).intValue(),
                new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(hboxRange, BorderLayout.NORTH);

        JButton buttonCalc = new JButton("Вычислить");

        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double from =
                            Double.parseDouble(textFieldFrom.getText());
                    Double to =
                            Double.parseDouble(textFieldTo.getText());
                    Double step =
                            Double.parseDouble(textFieldStep.getText());
                    GornerTableModel data = new GornerTableModel( from,to, step,MainFrame.this.coefficients);
// Создать новый экземпляр таблицы
                    JTable table = new JTable(data);
// Установить в качестве визуализатора ячеек для класса Double разработанный визуализатор
                    table.setDefaultRenderer(Double.class,
                            renderer);

                    table.setRowHeight(30);
// Удалить все вложенные элементы из контейнера
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));

                    getContentPane().validate();    // Обновить область содержания главного окна
// Пометить ряд элементов меню как доступных
                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");

        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");

                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
// Пометить элементы меню как недоступные
                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
// Обновить область содержания главного окна
                getContentPane().validate();
            }
        });
// Поместить созданные кнопки в контейнер
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
// Установить предпочтительный размер области равным удвоенному
      //  минимальному, чтобы при
// компоновке окна область совсем не сдавили
        hboxButtons.setPreferredSize(new Dimension(new
                Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new
                Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2));

        getContentPane().add(hboxButtons, BorderLayout.SOUTH);
// Область для вывода результата пока что пустая
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());

        getContentPane().add(hBoxResult, BorderLayout.CENTER);
    }
    protected void saveToGraphicsFile(File selectedFile) {

        try {
            DataOutputStream out = new DataOutputStream(new
                    FileOutputStream(selectedFile));

            for (int i = 0; i < data.getRowCount(); i++) {
                out.writeDouble((Double) data.getValueAt(i, 0));
                out.writeDouble((Double) data.getValueAt(i, 1));
            }
// Закрыть поток вывода
            out.close();
        } catch (Exception e) {

        }

    }
    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            out.println("Результатц табулирования многочлена по схеме Горнера");
            out.println("Многочлен: ");
            for (int i = 0; i < coefficients.length; i++) {
                out.print(coefficients[i] + "X^" + (coefficients.length - i - 1));
                if (i != coefficients.length - 1) {
                    out.print(" + ");
                }
                out.println("");
                out.println("Интервал от " + data.getTo() + " до " +
                        data.getTo() + " с шагом " + data.getStep());
                out.println("====================================================");

                for (int j = 0; i < data.getRowCount(); j++) {    // Записать в поток вывода значения в точках
                    out.println("Значение в точке " + data.getValueAt(j, 0)
                            + " равно " + data.getValueAt(j, 1));
                }

                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
            public static void main( String[] args) {

                if (args.length==0) {
                    System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
                    System.exit(-1);
                }

                Double[] coefficients = new Double[args.length];
                int i = 0;
                try {

                    for (String arg: args) {        // Перебрать аргументы, пытаясь преобразовать их в Double
                        coefficients[i++] = Double.parseDouble(arg);
                    }
                }
                catch (NumberFormatException ex) {

                    System.out.println("Ошибка преобразования строки '" +
                            args[i] + "' в число типа Double");
                    System.exit(-2);
                }
                MainFrame frame = new MainFrame(coefficients);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        }



