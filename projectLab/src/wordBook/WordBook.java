package wordBook;

import java.util.Date;
import java.time.LocalDateTime;

public class WordBook {
    // 인터페이스
    public interface Entity {
        String TBL_WORDBOOK = "WORDBOOK";
        String COL_NO = "NO";
        String COL_WORD = "WORD";
        String COL_RADICAL = "RADICAL";
        String COL_MEANING = "MEANING";
        String COL_PRONUNCIATION = "PRONUNCIATION";
        String COL_GRADE = "GRADE";
        String COL_DAY = "DAY";

    } // 인터페이스 닫음

    // 멤버
    private Integer no;
    private String word;
    private String radical;
    private String meaning;
    private String pronunciation;
    private Integer grade;
    private Date day;

    // 생성자
    public WordBook() {
    }

    public WordBook(Integer no, String word, String radical, String meaning, String pronunciation, Integer grade,
            Date day) {
        this.no = no;
        this.word = word;
        this.radical = radical;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.grade = grade;
        this.day = day;

    }

    public Date getDay() {
        return day;
    }

    public Integer getNo() {
        return no;
    }

    public String getWord() {
        return word;
    }

    public String getRadical() {
        return radical;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public Integer getGrade() {
        return grade;
    }

    // toString

    @Override
    public String toString() {
        return String.format("Information(단어 = %s, 부수 = %s, 뜻 = %s, %s, 급수 = %d)", this.word, this.radical,
                this.meaning, this.pronunciation, this.grade);
    }

} // 클래스 닫음
