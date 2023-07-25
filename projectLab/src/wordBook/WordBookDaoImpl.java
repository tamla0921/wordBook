package wordBook;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import oracle.jdbc.OracleDriver;
import static wordBook.OracleLogin.*; // 같은 src 폴더에 있는 경우, src하위 폴더부터 시작.
import static wordBook.WordBookSql.*;
import static wordBook.WordBook.Entity.*;

public class WordBookDaoImpl implements WordBookDao {

    // 싱글톤
    private static WordBookDaoImpl instance = null;

    private WordBookDaoImpl() {
    }

    public static WordBookDaoImpl getInstance() {
        if (instance == null) {
            instance = new WordBookDaoImpl();
        }
        return instance;

    }

// (1) 전체 읽기
// ------------------------------------------------------------------------------------------------------------------------------ //
    @Override
    public List<WordBook> read() {
        List<WordBook> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_READ_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Integer n = rs.getInt(COL_NO);
                String w = rs.getString(COL_WORD);
                String r = rs.getString(COL_RADICAL);
                String m = rs.getString(COL_MEANING);
                String p = rs.getString(COL_PRONUNCIATION);
                Integer g = rs.getInt(COL_GRADE);
                Date d = rs.getDate(COL_DAY);

                WordBook wordBook = new WordBook(n, w, r, m, p, g, d);

                list.add(wordBook);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return list;
    }

//(2) 자세히 보기
// ---------------------------------------------------------------------------------------------------------------------------------------------- //        
    @Override
    public WordBook read(Integer no) {
        WordBook wb = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_READ_BY);
            stmt.setInt(1, no);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Integer n = rs.getInt(COL_NO);
                String w = rs.getString(COL_WORD);
                String r = rs.getString(COL_RADICAL);
                String m = rs.getString(COL_MEANING);
                String p = rs.getString(COL_PRONUNCIATION);
                Integer g = rs.getInt(COL_GRADE);
                Date d = rs.getDate(COL_DAY);

                wb = new WordBook(n, w, r, m, p, g, d);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return wb;
    }

// (3) 수정하기
// ------------------------------------------------------------------------------------------------------------- //
    public Integer update(WordBook wrd) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setString(1, wrd.getMeaning());
            stmt.setString(2, wrd.getPronunciation());
            stmt.setInt(3, wrd.getGrade());
            stmt.setInt(4, wrd.getNo());

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public Integer create(WordBook wrd) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_CREATE);

            stmt.setString(1, wrd.getWord());
            stmt.setString(2, wrd.getRadical());
            stmt.setString(3, wrd.getMeaning());
            stmt.setString(4, wrd.getPronunciation());
            stmt.setInt(5, wrd.getGrade());

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

// (4) 삭제하기 
// -------------------------------------------------------------------------------------------------- //
    @Override
    public Integer delete(Integer no) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setInt(1, no);

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }

// (5) 검색하기
    @Override
    public List<WordBook> read(int type, String keyword) {
        List<WordBook> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            switch (type) {
            case 0: // 단어
                stmt = conn.prepareStatement(SQL_READ_BY_WORD);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            case 1: // 음
                stmt = conn.prepareStatement(SQL_READ_BY_PRONUNCIATION);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            case 2: // 훈
                stmt = conn.prepareStatement(SQL_READ_BY_MEANING);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            case 3: // 부수
                stmt = conn.prepareStatement(SQL_READ_BY_RADICAL);
                stmt.setString(1, "%" + keyword.toLowerCase() + "%");
                break;
            case 4: // 급수
                stmt = conn.prepareStatement(SQL_READ_BY_GRADE);
                stmt.setString(1, keyword);
                break;
            default:
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Integer n = rs.getInt(COL_NO);
                String w = rs.getString(COL_WORD);
                String r = rs.getString(COL_RADICAL);
                String m = rs.getString(COL_MEANING);
                String p = rs.getString(COL_PRONUNCIATION);
                Integer g = rs.getInt(COL_GRADE);
                Date d = rs.getDate(COL_DAY);

                WordBook wrd = new WordBook(n, w, r, m, p, g, d);
                list.add(wrd);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return list;
    }

// (6) 자세히 보기에 있는 다음 버튼
    @Override
    public WordBook next(Integer no) {
        WordBook wrd = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_PREVIOUS_NO);
            stmt.setInt(1, no);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int a = rs.getInt(COL_NO);
                stmt.close();
                rs.close();
                stmt = conn.prepareStatement(SQL_READ_BY);
                stmt.setInt(1, a);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    int n = rs.getInt(COL_NO);
                    String w = rs.getString(COL_WORD);
                    String r = rs.getString(COL_RADICAL);
                    String m = rs.getString(COL_MEANING);
                    String p = rs.getString(COL_PRONUNCIATION);
                    int g = rs.getInt(COL_GRADE);
                    Date d = rs.getDate(COL_DAY);

                    wrd = new WordBook(n, w, r, m, p, g, d);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return wrd;
    }

    public WordBook previous(Integer no) {
        WordBook wrd = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            stmt = conn.prepareStatement(SQL_PREVIOUS);
            stmt.setInt(1, no);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int n = rs.getInt(COL_NO);
                String w = rs.getString(COL_WORD);
                String r = rs.getString(COL_RADICAL);
                String m = rs.getString(COL_MEANING);
                String p = rs.getString(COL_PRONUNCIATION);
                int g = rs.getInt(COL_GRADE);
                Date d = rs.getDate(COL_DAY);

                wrd = new WordBook(n, w, r, m, p, g, d);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return wrd;
    }

// (7) 배운 날짜 생성하기
// -------------------------------------------------------------------------------------------------- //
    @Override
    public Integer birthday(Integer no) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_UPDATE_DATE);

            Date now = new Date();

            SimpleDateFormat now2 = new SimpleDateFormat("yyyy-MM-dd");
            String now3 = now2.format(now);

            stmt.setInt(1, no);

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // TODO Auto-generated method stub
        return result;
    }

    @Override
    public Date days(Integer no) {

        Date day = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_READ_DATE);

            stmt.setInt(1, no);

            rs = stmt.executeQuery();
            if (rs.next()) {
                day = rs.getDate(COL_DAY);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return day;
    }

    @Override
    public Integer nextNo(Integer value) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_NEXT_NO);
            stmt.setInt(1, value);
            rs = stmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(COL_NO);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return result;
    }

    @Override
    public Integer previousNo(Integer value) {
        int result = 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_PREVIOUS_NO);
            stmt.setInt(1, value);
            rs = stmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(COL_NO);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return result;
    }

    @Override
    public List<WordBook> get10words(String from, String to) {
        List<WordBook> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_GET_10WORDS);
            stmt.setString(1, from);
            stmt.setString(2, to);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Integer n = rs.getInt(COL_NO);
                String w = rs.getString(COL_WORD);
                String r = rs.getString(COL_RADICAL);
                String m = rs.getString(COL_MEANING);
                String p = rs.getString(COL_PRONUNCIATION);
                Integer g = rs.getInt(COL_GRADE);
                Date d = rs.getDate(COL_DAY);

                WordBook wordBook = new WordBook(n, w, r, m, p, g, d);

                list.add(wordBook);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return list;

    }

    @Override
    public String[] getYear() {
        List<String> a = new ArrayList<>();
        String[] list = new String[0];

        int i = 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_GET_YEAR);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(COL_DAY) != null) {
                    a.add(rs.getString(COL_DAY));
                }
            }

            list = a.toArray(new String[0]);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return list;
    }

} // DAOIMPL 닫음
