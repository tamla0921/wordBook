package wordBook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wordBook.Paper.PaperListener;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class WordBookTestFrame extends JFrame {

    private WordBookDaoImpl dao;

    private JPanel contentPane;

    private Component parent;
    private PaperListener l;
    private JComboBox cbFromMonth;
    private JComboBox cbFromDay;
    private JComboBox cbToMonth;
    private JComboBox cbToDay;
    private JComboBox cbFromYear;
    private JComboBox cbToYear;

    /**
     * Launch the application.
     */
    public static void testFrame(Component parent, PaperListener l) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                WordBookTestFrame frame = new WordBookTestFrame(parent, l);
                frame.setVisible(true);

            }
        });
    }

    /**
     * Create the frame.
     */
    public WordBookTestFrame(Component parent, PaperListener l) {
        dao = WordBookDaoImpl.getInstance();
        this.parent = parent;
        this.l = l;

        initialize();

    }

    private void initialize() {

        int x = parent.getX();
        int y = parent.getY();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(x + 300, y, 346, 204);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lbl1 = new JLabel("날짜를 입력해주세요.");
        panel.add(lbl1);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(null);

        JLabel lblNewLabel = new JLabel("년");
        lblNewLabel.setBounds(99, 14, 12, 15);
        panel_1.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("년");
        lblNewLabel_1.setBounds(99, 67, 12, 15);
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("월");
        lblNewLabel_1_1.setBounds(191, 14, 12, 15);
        panel_1.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("월");
        lblNewLabel_1_1_1.setBounds(191, 67, 12, 15);
        panel_1.add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("일");
        lblNewLabel_1_1_1_1.setBounds(293, 14, 12, 15);
        panel_1.add(lblNewLabel_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_1 = new JLabel("일");
        lblNewLabel_1_1_1_1_1.setBounds(293, 67, 12, 15);
        panel_1.add(lblNewLabel_1_1_1_1_1);

        JLabel lblNewLabel_2 = new JLabel("~");
        lblNewLabel_2.setBounds(148, 43, 18, 15);
        panel_1.add(lblNewLabel_2);

        JButton btnStart = new JButton("시작!");
        btnStart.setBackground(Color.LIGHT_GRAY);
        btnStart.setFont(new Font("굴림", Font.BOLD, 12));
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        btnStart.setBounds(230, 97, 75, 23);
        panel_1.add(btnStart);

        cbFromMonth = new JComboBox();
        cbFromMonth.setModel(new DefaultComboBoxModel(
                new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cbFromMonth.setBounds(121, 10, 58, 23);
        panel_1.add(cbFromMonth);

        cbFromDay = new JComboBox();
        cbFromDay.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31" }));
        cbFromDay.setBounds(215, 10, 66, 23);
        panel_1.add(cbFromDay);

        cbToMonth = new JComboBox();
        cbToMonth.setModel(new DefaultComboBoxModel(
                new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        cbToMonth.setBounds(121, 63, 58, 23);
        panel_1.add(cbToMonth);

        cbToDay = new JComboBox();
        cbToDay.setModel(new DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
                "27", "28", "29", "30", "31" }));
        cbToDay.setBounds(215, 63, 66, 23);
        panel_1.add(cbToDay);

        // String[] year = {"2222", "3333"};
        String[] year = dao.getYear();

        cbFromYear = new JComboBox();
        cbFromYear.setModel(new DefaultComboBoxModel(year));
        cbFromYear.setBounds(12, 10, 75, 23);
        panel_1.add(cbFromYear);

        cbToYear = new JComboBox();
        cbToYear.setModel(new DefaultComboBoxModel(year));
        cbToYear.setBounds(12, 63, 75, 23);
        panel_1.add(cbToYear);

        JButton btnCancel = new JButton("←");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                l.paperNotify();
            }
        });
        btnCancel.setBackground(new Color(192, 192, 192));
        btnCancel.setFont(new Font("굴림", Font.BOLD, 12));
        btnCancel.setBounds(12, 97, 51, 23);
        panel_1.add(btnCancel);
    }

    protected void start() {
        String fromYear = (String) cbFromYear.getSelectedItem();
        String fromMonth = (String) cbFromMonth.getSelectedItem();
        String fromDay = (String) cbFromDay.getSelectedItem();
        String toYear = (String) cbToYear.getSelectedItem();
        String toMonth = (String) cbToMonth.getSelectedItem();
        String toDay = (String) cbToDay.getSelectedItem();

//        if (fromYear.equals("") || fromMonth.equals("") || fromDay.equals("") || toYear.equals("") || toMonth.equals("")
//                || toDay.equals("")) {
//            JOptionPane.showMessageDialog(this, "빈 칸은 입력할 수 없습니다.");
//            return;
//        }
        
        
        LocalDate fromDate = LocalDate.of(Integer.parseInt(fromYear),Integer.parseInt(fromMonth),Integer.parseInt(fromDay));
        LocalDate toDate = LocalDate.of(Integer.parseInt(toYear),Integer.parseInt(toMonth),Integer.parseInt(toDay));
        
        if (fromDate.isAfter(toDate)) {
            JOptionPane.showMessageDialog(this, "시작하는 날짜가 끝나는 날짜보다 늦을 수 없습니다.");
            return;
        }
        
        System.out.println(fromDate);

        String from = String.format(fromYear + "/" + fromMonth + "/" + fromDay);
        String to = String.format(toYear + "/" + toMonth + "/" + toDay);

        Paper.paper(parent, from, to, l);

        dispose();

        System.out.println("dd: " + from);
        System.out.println("ee: " + to);

    }
}
