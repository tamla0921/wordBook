package wordBook;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class WordBookNewFrame extends JFrame {
    public interface NewListener {
        void newNorify();
    }

    private JPanel contentPane;

    private Component parent;
    private NewListener listener;
    private JTextField textWord;
    private JTextField textMeaning;
    private JTextField textPronunciation;
    private JTextField textRadical;

    private WordBookDaoImpl dao;
    private JComboBox cbGrade;

    /**
     * Launch the application.
     */
    public static void showNew(Component parent, NewListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                WordBookNewFrame frame = new WordBookNewFrame(parent, listener);
                frame.setVisible(true);

            }
        });
    }

    /**
     * Create the frame.
     */

    public WordBookNewFrame(Component parent, NewListener listener) {
        dao = WordBookDaoImpl.getInstance();
        this.parent = parent;
        this.listener = listener;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        int x = parent.getX();
        int y = parent.getY();

        setBounds(x + 300, y, 247, 258);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWord = new JLabel("단어");
        lblWord.setFont(new Font("굴림", Font.BOLD, 12));
        lblWord.setBounds(12, 21, 57, 15);
        contentPane.add(lblWord);

        JLabel lblMeaning = new JLabel("뜻");
        lblMeaning.setFont(new Font("굴림", Font.BOLD, 12));
        lblMeaning.setBounds(12, 56, 57, 15);
        contentPane.add(lblMeaning);

        JLabel lblPronunciation = new JLabel("음");
        lblPronunciation.setFont(new Font("굴림", Font.BOLD, 12));
        lblPronunciation.setBounds(12, 92, 57, 15);
        contentPane.add(lblPronunciation);

        JLabel lblGrade = new JLabel("부수");
        lblGrade.setFont(new Font("굴림", Font.BOLD, 12));
        lblGrade.setBounds(12, 125, 57, 15);
        contentPane.add(lblGrade);

        textWord = new JTextField();
        textWord.setBounds(93, 18, 116, 21);
        contentPane.add(textWord);
        textWord.setColumns(10);

        textMeaning = new JTextField();
        textMeaning.setColumns(10);
        textMeaning.setBounds(93, 53, 116, 21);
        contentPane.add(textMeaning);

        textPronunciation = new JTextField();
        textPronunciation.setColumns(10);
        textPronunciation.setBounds(93, 89, 116, 21);
        contentPane.add(textPronunciation);

        textRadical = new JTextField();
        textRadical.setColumns(10);
        textRadical.setBounds(93, 122, 116, 21);
        contentPane.add(textRadical);

        JButton btnNew = new JButton("√");
        btnNew.setFont(new Font("굴림", Font.BOLD, 12));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnNew.setBounds(162, 190, 57, 23);
        contentPane.add(btnNew);

        JLabel lblRadical = new JLabel("급수");
        lblRadical.setFont(new Font("굴림", Font.BOLD, 12));
        lblRadical.setBounds(12, 162, 57, 15);
        contentPane.add(lblRadical);

        cbGrade = new JComboBox();
        cbGrade.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        cbGrade.setBounds(93, 158, 116, 23);
        contentPane.add(cbGrade);
    }

    protected void save() {
        int result = 0;
        String w = textWord.getText();
        String r = textRadical.getText();
        String m = textMeaning.getText();
        String p = textPronunciation.getText();
        Integer g = Integer.parseInt((String) cbGrade.getSelectedItem());

        if (w.equals("") || r.equals("") || m.equals("") || p.equals("")) {
            JOptionPane.showMessageDialog(this, "빈 칸은 입력할 수 없습니다.");
            return;
        }

        WordBook wrd4 = new WordBook(null, w, r, m, p, g, null);

        result = dao.create(wrd4);

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "생성 완료!");
            dispose();
            listener.newNorify();
        } else {
            JOptionPane.showMessageDialog(this, "???");
        }

    }
}
