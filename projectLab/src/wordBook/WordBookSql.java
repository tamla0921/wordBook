package wordBook;

import static wordBook.WordBook.Entity.*; // 변수 선언 없이 import하려면 static!!)

public interface WordBookSql {
    // select 문

    String SQL_READ_ALL = String.format("select * from %s order by %s", TBL_WORDBOOK, COL_NO);
    String SQL_READ_BY = String.format("select * from %s where %s = ?", TBL_WORDBOOK, COL_NO);

    String SQL_READ_BY_WORD = String.format("select * from %s where lower(%s) like ?", TBL_WORDBOOK, COL_WORD);
    String SQL_READ_BY_RADICAL = String.format("select * from %s where lower(%s) like ?", TBL_WORDBOOK, COL_RADICAL);
    String SQL_READ_BY_MEANING = String.format("select * from %s where lower(%s) like ?", TBL_WORDBOOK, COL_MEANING);
    String SQL_READ_BY_PRONUNCIATION = String.format("select * from %s where lower(%s) like ?", TBL_WORDBOOK,
            COL_PRONUNCIATION);

    String SQL_READ_BY_GRADE = String.format("select * from %s where lower(%s) like ?", TBL_WORDBOOK, COL_GRADE);

    // insert 문
    String SQL_CREATE = String.format("insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)", TBL_WORDBOOK,
            COL_WORD, COL_RADICAL, COL_MEANING, COL_PRONUNCIATION, COL_GRADE);

    // update 문
    String SQL_UPDATE = String.format("update %s set %s = ?, %s = ?, %s = ? where %s = ?", TBL_WORDBOOK, COL_MEANING,
            COL_PRONUNCIATION, COL_GRADE, COL_NO);

    // delete문
    String SQL_DELETE = String.format("delete from %s where %s = ?", TBL_WORDBOOK, COL_NO);

    String SQL_PREVIOUS_NO = String.format("select %s from %s where %s < ? order by %s desc", COL_NO, TBL_WORDBOOK,
            COL_NO, COL_NO);

    String SQL_NEXT_NO = String.format("select %s from %s where %s > ? and rownum <= 1", COL_NO, TBL_WORDBOOK, COL_NO);

    String SQL_PREVIOUS = String.format(
            "select * from (select rownum as num, %s, %s, %s, %s, %s, %s, %s from %s) t where t.num = ?", COL_NO,
            COL_WORD, COL_RADICAL, COL_MEANING, COL_PRONUNCIATION, COL_GRADE, COL_DAY, TBL_WORDBOOK);

    String SQL_UPDATE_DATE = String.format("update %s set %s = sysdate where %s = ?", TBL_WORDBOOK, COL_DAY, COL_NO);
    String SQL_READ_DATE = String.format("select %s from %s where %s = ?", COL_DAY, TBL_WORDBOOK, COL_NO);

    String SQL_WORDBOOK_NO = String.format("select %s from %s", COL_NO, TBL_WORDBOOK);

    String SQL_GET_10WORDS = String.format(
            "select * from (select * from %s where to_char(%s, 'YYYY/MM/DD') >= ? and to_char(%s, 'YYYY/MM/DD') <= ? order by dbms_random.value ) where rownum <= 10",
            TBL_WORDBOOK, COL_DAY, COL_DAY);

    String SQL_GET_YEAR = String.format(
            "select to_char(%s, 'YYYY') as %s from %s group by to_char(%s, 'YYYY') order by %s", COL_DAY, COL_DAY,
            TBL_WORDBOOK, COL_DAY, COL_DAY);
}
