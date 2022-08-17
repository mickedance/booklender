package se.lexicon.micke.booklender.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BookTest {
    @Test
    void createEmptyBook(){
        Book book = new Book();
        Assertions.assertNotNull(book);
    }
    @Test
    void setBookTitleWithCorrectParam(){
        Book book = new Book();
        book.setTitle("title1");
        Assertions.assertEquals("title1", book.getTitle());
    }
    @Test
    void setBookTitleWithNullParam(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            book.setTitle(null);
        });
    }
    @Test
    void setBookTitleWithEmptyTitle(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            book.setTitle("");
        });
    }
    @Test
    void geDefaultAvailableValue(){
        Book book = new Book();
        Assertions.assertTrue(book.isAvailable());
    }
    @Test
    void setAvailableToFalse(){
        Book book = new Book();
        book.setAvailable(false);
        Assertions.assertFalse(book.isAvailable());
    }
    @Test
    void seeDefaultReservedValue(){
        Book book = new Book();
        Assertions.assertFalse(book.isReserved());
    }
    @Test
    void setReservedToTrue(){
        Book book = new Book();
        book.setReserved(true);
        Assertions.assertTrue(book.isReserved());
    }
    @Test
    void getDefaultMaxLoanDaysValue(){
        Book book = new Book();
        Assertions.assertEquals(20, book.getMaxLoanDays());
    }
    @Test
    void setMaxLoanDaysValue(){
        Book book = new Book();
        book.setMaxLoanDays(10);
        Assertions.assertEquals(10, book.getMaxLoanDays());
    }
    @Test
    void setMaxLoanDaysToWrongValue(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            book.setMaxLoanDays(-1);
        });
    }
    @Test
    void setFinePerDay(){
        Book book = new Book();
        book.setFinePerDay(new BigDecimal("12.01"));
        Assertions.assertEquals( new BigDecimal("12.01"), book.getFinePerDay());
    }
    @Test
    void setFinePerDayWithNullValue(){
        Book book = new Book();
        Assertions.assertThrows(NullPointerException.class, ()->{
            book.setFinePerDay(null);
        });
    }
    @Test
    void setFinePerDayWithBelowZeroValue(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            book.setFinePerDay(new BigDecimal("-1"));
        });
    }
    @Test
    void setDescription(){
        Book book = new Book();
        book.setDescription("descr");
        Assertions.assertEquals("descr", book.getDescription());
    }
    @Test
    void setDescriptionWithNullValue(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            book.setDescription(null);
        });
    }
    @Test
    void setDescriptionWithEmptyDescription(){
        Book book = new Book();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            book.setDescription("");
        });
    }
}
