package wordBook;

import static wordBook.WordBook.Entity.COL_DAY;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.net.http.WebSocket.Listener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.util.Date;
import javax.swing.border.CompoundBorder;

public class WordBookDetailFrame extends JFrame {
    public interface UpdateListener {
        void updateNotify();
    }

    private UpdateListener listener;
    private WordBookDaoImpl dao;

    private JPanel contentPane;
    private JTextField textWord;
    private JTextField textGrade;
    private JTextField textMeaning;
    private JTextField textRadical;

    private Integer value;
    private Component parent;
    private JTextField textPronun;
    private JButton btnDay;

    private WordBook wrd;
    private JButton btnReset;

    /**
     * Launch the application.
     */

    public static void showDetail(Component parent, Integer value, UpdateListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                WordBookDetailFrame frame = new WordBookDetailFrame(parent, value, listener);
                frame.setVisible(true);

            }
        });
    }

    /**
     * Create the frame.
     */
    public WordBookDetailFrame(Component parent, Integer value, UpdateListener listener) {
        dao = WordBookDaoImpl.getInstance();
        this.parent = parent;
        this.value = value;
        this.listener = listener;
        initialize();

        detail();
    }

    void initialize() {
        setTitle("한자 자세히 보기");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        int x = parent.getX();
        int y = parent.getY();

        setBounds(x + 300, y, 260, 330);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        textWord = new JTextField();
        textWord.setBorder(null);
        textWord.setHorizontalAlignment(SwingConstants.CENTER);
        textWord.setFont(new Font("굴림", Font.BOLD, 99));
        textWord.setEditable(false);
        textWord.setBounds(62, 41, 120, 120);
        contentPane.add(textWord);
        textWord.setColumns(10);

        textGrade = new JTextField();
        textGrade.setBorder(null);
        textGrade.setHorizontalAlignment(SwingConstants.RIGHT);
        textGrade.setBounds(65, 10, 30, 21);
        contentPane.add(textGrade);
        textGrade.setColumns(10);

        textMeaning = new JTextField();
        textMeaning.setBorder(null);
        textMeaning.setHorizontalAlignment(SwingConstants.CENTER);
        textMeaning.setBounds(62, 171, 120, 21);
        contentPane.add(textMeaning);
        textMeaning.setColumns(10);

        textRadical = new JTextField();
        textRadical.setHorizontalAlignment(SwingConstants.TRAILING);
        textRadical.setBorder(null);
        textRadical.setColumns(10);
        textRadical.setBounds(124, 222, 58, 21);
        contentPane.add(textRadical);

        JButton btnOk = new JButton("√");
        btnOk.setBackground(Color.LIGHT_GRAY);
        btnOk.setBorder(null);
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        btnOk.setFont(new Font("굴림", Font.BOLD, 10));
        btnOk.setBounds(198, 263, 41, 23);
        contentPane.add(btnOk);

        JLabel lblRadical = new JLabel("부수: ");
        lblRadical.setBounds(62, 225, 58, 15);
        contentPane.add(lblRadical);

        textPronun = new JTextField();
        textPronun.setBorder(null);
        textPronun.setHorizontalAlignment(SwingConstants.CENTER);
        textPronun.setColumns(10);
        textPronun.setBounds(62, 194, 120, 21);
        contentPane.add(textPronun);

        JButton btnNext = new JButton("▶");
        btnNext.setBackground(Color.LIGHT_GRAY);
        btnNext.setHorizontalAlignment(SwingConstants.TRAILING);
        btnNext.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // (위, 왼쪽, 아래, 오른쪽)

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shownext();

            }
        });
        btnNext.setBounds(225, 88, 20, 40);
        contentPane.add(btnNext);

        JButton btnPrevious = new JButton("◀");
        btnPrevious.setBackground(Color.LIGHT_GRAY);
        btnPrevious.setHorizontalAlignment(SwingConstants.LEADING);
        btnPrevious.setBorder(new EmptyBorder(0, 5, 0, 0));
        btnPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrevious();
            }
        });
        btnPrevious.setBounds(0, 88, 20, 40);
        contentPane.add(btnPrevious);

        btnDay = new JButton();
        btnDay.setBackground(Color.LIGHT_GRAY);
        btnDay.setBorder(null);

        if (dao.days(value) == null) {
            btnDay.setText("날짜 생성");
        } else {
            btnDay.setText(dao.days(value).toString());
        }
        btnDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                birthday();
                btnDay.setText(dao.days(value).toString());
            }
        });
        btnDay.setBounds(5, 263, 113, 23);
        contentPane.add(btnDay);

        JLabel lblNewLabel = new JLabel("급");
        lblNewLabel.setBounds(96, 10, 30, 21);
        contentPane.add(lblNewLabel);

        btnReset = new JButton("🔁");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                detail();
            }
        });
        btnReset.setFont(new Font("DialogInput", Font.PLAIN, 12));
        btnReset.setBorder(null);
        btnReset.setBackground(Color.LIGHT_GRAY);
        btnReset.setBounds(134, 262, 41, 23);
        contentPane.add(btnReset);
    }

    // ---------- 외운 날짜 버튼 ---------- //
    protected void birthday() {
        WordBook wrd = dao.read(value);
        Integer n = wrd.getNo();
        dao.birthday(n);
    }

    // ---------- 자세히 보는 창 ---------- //
    private void detail() {
        wrd = dao.read(value);

        textGrade.setText(wrd.getGrade().toString());
        textWord.setText(wrd.getWord());
        textMeaning.setText(wrd.getMeaning());
        textPronun.setText(wrd.getPronunciation());
        textRadical.setText(wrd.getRadical());

    }

// ---------- 이전 항목 보기 버튼 ---------- //
    protected void showPrevious() {
        int value2 = value;
        WordBook wrd3 = wrd;
        value = dao.previousNo(value);
        wrd = dao.read(value);

        try {

            textGrade.setText(wrd.getGrade().toString());
            textWord.setText(wrd.getWord());
            textMeaning.setText(wrd.getMeaning());
            textPronun.setText(wrd.getPronunciation());
            textRadical.setText(wrd.getRadical());
            if (dao.days(value) == null) {
                btnDay.setText("날짜 생성");
            } else {
                btnDay.setText(dao.days(value).toString());
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "첫 페이지입니다...");
            value = value2;
            wrd = wrd3;

        }

    }

// ---------- 다음 항목 보기 버튼 ---------- //
    protected void shownext() {
        int value2 = value;
        WordBook wrd3 = wrd;
        value = dao.nextNo(value);
        wrd = dao.read(value);

        try {

            textGrade.setText(wrd.getGrade().toString());
            textWord.setText(wrd.getWord());
            textMeaning.setText(wrd.getMeaning());
            textPronun.setText(wrd.getPronunciation());
            textRadical.setText(wrd.getRadical());
            if (dao.days(value) == null) {
                btnDay.setText("날짜 생성");
            } else {
                btnDay.setText(dao.days(value).toString());
            }

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "마지막 페이지입니다...");
            value = value2;
            wrd = wrd3;

        }

    }

// ---------- 수정하기 버튼 ---------- //
    private void update() {
        String w = textWord.getText();
        String r = textRadical.getText();
        String m = textMeaning.getText();
        String p = textPronun.getText();
        Integer g = Integer.parseInt(textGrade.getText());

        // TODO
        WordBook wrd2 = new WordBook(value, w, r, m, p, g, null);
        System.out.println(wrd2);

        if (!wrd.getWord().equals(wrd2.getWord()) || !wrd.getRadical().equals(wrd2.getRadical())
                || !wrd.getMeaning().equals(wrd2.getMeaning())
                || !wrd.getPronunciation().equals(wrd2.getPronunciation()) || !wrd.getGrade().equals(wrd2.getGrade())) {

            int result = dao.update(wrd2);

            if (result == 1) {
                JOptionPane.showMessageDialog(this, "수정성공");
                dispose();
                listener.updateNotify();
            } else {
                JOptionPane.showMessageDialog(this, "변경할 수 없습니다.");

            }

        } else {
            listener.updateNotify();
            dispose();
        }

    }
}
