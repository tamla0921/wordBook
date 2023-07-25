package wordBook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import wordBook.Paper.PaperListener;
import wordBook.WordBookDetailFrame.UpdateListener;
import wordBook.WordBookNewFrame.NewListener;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class wordMain implements UpdateListener, NewListener, PaperListener, ActionListener {
    private static final String[] COLUMN_NAMES = { "no", "단어", "뜻", "급수", "배운 날짜" };
    private static final String[] COLUMN_NAMES2 = { "no", "한자", "뜻", "O/X" };
    private DefaultTableModel model;

    private WordBookDaoImpl dao;

    private JFrame frame;
    private JTable table;
    private JTextField textSearch;
    private JComboBox sadari;
    private JButton btnNew;
    private JButton btnTest;

    private List<WordBook> randomList;
    private List<String> ckList;
    private List<String> answerList;
    private JButton btnReset;
    private JPanel panel_1;

    private JPopupMenu popupMenu;
    private JMenuItem menuItemShow;
    private JMenuItem menuItemRemove;

    private List<WordBook> list;

    // private EventObject event;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    wordMain window = new wordMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public wordMain() {
        initialize();
        dao = WordBookDaoImpl.getInstance();
        initializeTable();

    }

// 메서드  ------------------------------------------------------------------------------- //  

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(600, 150, 307, 617);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 291, 72);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        String[] comboBoxItems = { "단어", "음", "훈", "부수", "급수" };
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxItems);

        sadari = new JComboBox<>();
        sadari.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (textSearch.getText().equals("")) {
                    initializeTable(); // TODO -- (1)
                } else {
                    searchWordByKeyword(); // TODO -- (2)
                }
            }
        });

        sadari.setBounds(6, 41, 51, 21);
        panel.add(sadari);

        sadari.setModel(comboBoxModel);
        sadari.setSelectedIndex(0);

        textSearch = new JTextField();
        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (textSearch.getText().equals("")) {
                    initializeTable();
                } else {
                    searchWordByKeyword();
                }
            }
        });
//        textSearch.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                searchWordByKeyword();
//            }
//        });
        textSearch.setBounds(63, 41, 221, 21);
        panel.add(textSearch);
        textSearch.setColumns(10);

        btnNew = new JButton("＋");
        btnNew.addContainerListener(new ContainerAdapter() {

        });
        btnNew.setBounds(232, 9, 51, 22);
        panel.add(btnNew);
        btnNew.setFont(new Font("굴림", Font.BOLD, 12));

        JLabel lblNewLabel = new JLabel("나만의 단어장");
        lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(4, 9, 139, 22);
        panel.add(lblNewLabel);
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNew();
            }
        });

        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(0, 72, 291, 472);
        frame.getContentPane().add(scrollPane);

        popupMenu = new JPopupMenu();
        menuItemShow = new JMenuItem("자세히 보기");
        menuItemRemove = new JMenuItem("삭제하기");

        menuItemShow.addActionListener(this);
        menuItemRemove.addActionListener(this);

        popupMenu.add(menuItemShow);
        popupMenu.add(menuItemRemove);

        table = new JTable();
        table.setComponentPopupMenu(popupMenu);
        table.addMouseListener(new PopUp(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                }
                if (e.getClickCount() == 2) {
                    showDetail();
                }
                if (e.getButton() == 3) {
                    int column = table.columnAtPoint(e.getPoint());
                    int row = table.rowAtPoint(e.getPoint());
                    table.changeSelection(row, column, false, false);

                }
            }

        });

        scrollPane.setViewportView(table);

        panel_1 = new JPanel();
        panel_1.setBounds(0, 544, 291, 33);
        frame.getContentPane().add(panel_1);

        btnTest = new JButton("TEST");
        btnTest.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        panel_1.add(btnTest);

        btnReset = new JButton("🔁");
        panel_1.add(btnReset);
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializeTable();
            }
        });
        btnTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTable();

            }
        });
    }

// ------------------------------------------------------------------------------- //
    // (1) 테이블 새로고침
    private void initializeTable() {

        list = dao.read();

        showme(list);
    }

// (2) 데이터 검색
    protected void searchWordByKeyword() {

        int type = getIndexForSearch();

        String keyword = textSearch.getText();

        System.out.println("type = " + type + ", keyword = " + keyword);

        list = dao.read(type, keyword);

        showme(list); // TODO --- (3)
    }

// (3) 찾고 보여주세요.
    private void showme(List<WordBook> list) {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        table.getColumn("no").setWidth(0);
        table.getColumn("no").setMinWidth(0);
        table.getColumn("no").setMaxWidth(0);
        table.getColumn("단어").setPreferredWidth(0);
        table.getColumn("급수").setPreferredWidth(0);

        for (WordBook w : list) {
            Object[] rowData = { w.getNo(), w.getWord(), w.getMeaning() + " " + w.getPronunciation(), w.getGrade(),
                    w.getDay() };
            model.addRow(rowData);
        }

    }

    protected void clearTable() {
        model = new DefaultTableModel();
        table.setModel(model);
        hidebuttons();
        WordBookTestFrame.testFrame(frame, wordMain.this);

    }

    private void hidebuttons() {
        btnNew.setEnabled(false);

        btnTest.setEnabled(false);
        sadari.setEnabled(false);
        textSearch.setEnabled(false);
        btnReset.setEnabled(false);

    }

    private void reopenButtons() {
        btnNew.setEnabled(true);

        btnTest.setEnabled(true);
        sadari.setEnabled(true);
        textSearch.setEnabled(true);
        btnReset.setEnabled(true);
    }

// 데이터 변경 메서드 모음

    private int getIndexForSearch() {

        int type = sadari.getSelectedIndex();
        return type;
    }

    // 단어 삭제
    protected void delete() {
        int result = 0;
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "한자를 클릭해주세요!!");
            return;
        }

        Integer value = (Integer) model.getValueAt(row, 0);
        int choice = JOptionPane.showConfirmDialog(frame, "진짜 삭제할까요?", "경고!!", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {

            result = dao.delete(value);
        } else {
            return;
        }

        if (result == 1) {
            JOptionPane.showMessageDialog(frame, "삭제완료");
            initializeTable();
        } else {
            JOptionPane.showMessageDialog(frame, "불가...");
        }

    }

    // 단어 추가
    protected void showNew() {
        WordBookNewFrame.showNew(frame, wordMain.this);

    }

    // 단어 자세히 보기
    private void showDetail() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "한자를 클릭해주세요!!");
            return;
        }

        Integer value = (Integer) model.getValueAt(row, 0);
        WordBookDetailFrame.showDetail(frame, value, wordMain.this);

    }

    // 단어 변경 완료
    @Override
    public void updateNotify() {
        showme(list);

    }

    // 단어 추가 완료
    @Override
    public void newNorify() {
        initializeTable();

    }

    @Override
    public void paperNotify(List<String> answerList, List<WordBook> randomList, List<String> ckList) {
        this.answerList = answerList;
        this.randomList = randomList;
        this.ckList = ckList;
        reopenButtons();
        model = new DefaultTableModel(null, COLUMN_NAMES2);

        for (int i = 0; i < randomList.size(); i++) {
            if (answerList.get(i).replace(" ", "")
                    .equals((randomList.get(i).getMeaning() + randomList.get(i).getPronunciation()))) {
                System.out.println("정답입니다!");
                ckList.add("O");
            } else {
                ckList.add("X");
            }

        }

        for (int i = 0; i < randomList.size(); i++) {
            Object[] rowdata = { randomList.get(i).getNo(), randomList.get(i).getWord(),
                    randomList.get(i).getMeaning() + " " + randomList.get(i).getPronunciation(), ckList.get(i) };
            model.addRow(rowdata);

        }

        table.setModel(model);
        table.getColumn("no").setWidth(0);
        table.getColumn("no").setMinWidth(0);
        table.getColumn("no").setMaxWidth(0);
    }

    @Override
    public void paperNotify() {
        reopenButtons();
        initializeTable();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menu = (JMenuItem) e.getSource();
        if (menu == menuItemShow) {
            showDetail();
        } else if (menu == menuItemRemove) {
            delete();
        }
    }

}
