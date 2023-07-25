package wordBook;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FinalResultFrame extends JFrame {
    
    private DefaultTableModel model;
    private static final String[] COLUMN_NAMES = { "한자", "뜻", "O/X" };
    List<WordBook> randomList;
    List<String> ckList;
    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void showResult(List<WordBook> randomList, List<String> ckList) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                FinalResultFrame frame = new FinalResultFrame(randomList, ckList);
                frame.setVisible(true);

            }
        });
    }

    /**
     * Create the frame.
     */
    public FinalResultFrame(List<WordBook> randomList, List<String> ckList) {
        this.randomList = randomList;
        this.ckList = ckList;
        initialize();
        initializeTable();
    }

    private void initializeTable() {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);

        for (int i = 0; i < randomList.size(); i++) {
            Object[] rowdata = { randomList.get(i).getWord(),
                    randomList.get(i).getMeaning() + " " + randomList.get(i).getPronunciation(), ckList.get(i) };
            model.addRow(rowdata);

        }

    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("결과를 확인해봐요!");
        panel.add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);
    }
}
