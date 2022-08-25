package se.lexicon.micke.booklender.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BookDtoTest {
    private BookDto getBookDto(){
        return  new BookDto(655,"This books title is this",true,false,55,new BigDecimal(222),"description of this excellent book");
    }
    @Test
    void createBookDto(){
        BookDto bookDto = getBookDto();
        Assertions.assertEquals(655, bookDto.getBookId());
        Assertions.assertEquals("This books title is this", bookDto.getTitle());
        Assertions.assertTrue(bookDto.isAvailable());
        Assertions.assertFalse(bookDto.isReserved());
        Assertions.assertEquals(55, bookDto.getMaxLoanDays());
        Assertions.assertEquals(new BigDecimal(222), bookDto.getFinePerDay());
        Assertions.assertEquals("description of this excellent book", bookDto.getDescription());
    }
    @Test
    void setId(){
        BookDto bookDto = getBookDto();
        Assertions.assertThrows(IllegalArgumentException.class, ()->bookDto.setBookId(-1));
        bookDto.setBookId(23);
        Assertions.assertEquals(23, bookDto.getBookId());
    }
    @Test
    void setTitle(){
        BookDto bookDto = getBookDto();
        Assertions.assertThrows(IllegalArgumentException.class,()->bookDto.setTitle(null));
        Assertions.assertThrows(IllegalArgumentException.class,()->bookDto.setTitle(""));
        bookDto.setTitle("lskdjhf");
        Assertions.assertEquals("lskdjhf", bookDto.getTitle());
    }
    @Test
    void setAvailable(){
        BookDto bookDto = getBookDto();
        bookDto.setAvailable(false);
        Assertions.assertFalse(bookDto.isAvailable());
    }
    @Test
    void setReserved(){
        BookDto bookDto = getBookDto();
        bookDto.setReserved(true);
        Assertions.assertTrue(bookDto.isReserved());
    }
    @Test
    void setMaxLoanDays(){
        BookDto bookDto = getBookDto();
        Assertions.assertThrows(IllegalArgumentException.class, ()->bookDto.setMaxLoanDays(-1));
        bookDto.setMaxLoanDays(0);
        Assertions.assertEquals(0, bookDto.getMaxLoanDays());
        bookDto.setMaxLoanDays(1);
        Assertions.assertEquals(1, bookDto.getMaxLoanDays());
    }
    @Test
    void setFinePerDay(){
        BookDto bookDto = getBookDto();
        Assertions.assertThrows(NullPointerException.class, ()->bookDto.setFinePerDay(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->bookDto.setFinePerDay(new BigDecimal(-.1)));
        bookDto.setFinePerDay(new BigDecimal(589.4));
        Assertions.assertEquals(new BigDecimal(589.4), bookDto.getFinePerDay());
    }
    @Test
    void setDescription(){
        BookDto bookDto = getBookDto();
        Assertions.assertThrows(IllegalArgumentException.class, ()->bookDto.setDescription(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->bookDto.setDescription(""));
        bookDto.setDescription("hh");
        Assertions.assertEquals("hh", bookDto.getDescription());
    }
}
