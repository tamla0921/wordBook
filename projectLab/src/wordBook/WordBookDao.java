package wordBook;

import java.util.Date;
import java.util.List;

public interface WordBookDao {
    
    List<WordBook> read();
    WordBook read(Integer no);
    List<WordBook> read(int type, String keyword);
    
    Integer update(WordBook wrd);
    Integer create(WordBook wrd);
    Integer delete(Integer no);
    
    WordBook next(Integer no);
    WordBook previous(Integer no);
    Integer birthday(Integer no);
    Date days(Integer no);
    
    Integer nextNo(Integer value);
    Integer previousNo(Integer value);
    List<WordBook> get10words(String from, String to);
    
    String[] getYear();
    
//    Integer marked(String ox);
}
