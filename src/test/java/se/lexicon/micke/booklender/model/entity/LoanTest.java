package se.lexicon.micke.booklender.model.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class LoanTest {

    private Loan getLoan(){
        Book book = new Book("title of book",23, BigDecimal.ONE,"description");
        LibraryUser user = new LibraryUser(LocalDate.now(), "name is here", "email@.com");
        return new Loan(user,book,  LocalDate.now(), false);
    }
    @Test
    void createNewLoan(){

        Loan loan = getLoan();
        System.out.println(loan);
        System.out.println(loan.getBook().getTitle());
    }
    @Test
    void createLoanWithNullValues(){
        Book book = new Book("title of book",23, BigDecimal.ONE,"description");
        LibraryUser user = new LibraryUser(LocalDate.now(), "name is here", "email@.com");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            Loan loan = new Loan(null,book,  LocalDate.now(), false);
        });
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            Loan loan = new Loan(user,null,  LocalDate.now(), false);
        });
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            Loan loan = new Loan(user,book,  null, false);
        });
    }
    @Test
    void setIdToWrongId(){
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            loan.setId(-1);
        });
    }
    @Test
    void setIdTo11(){
        Loan loan = getLoan();
        loan.setId(11);
        Assertions.assertEquals(11, loan.getId());
    }
    @Test
    void setLoanTakerWithNullValue(){
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            loan.setLoanTaker(null);
        });
    }
    @Test
    void setNewLoanTaker(){
        Loan loan = getLoan();
        LibraryUser user = new LibraryUser(LocalDate.now(), "new user", "new email");
        loan.setLoanTaker(user);
        Assertions.assertEquals(user, loan.getLoanTaker());
    }
    @Test
    void setBookWithNullValue(){
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            loan.setBook(null);
        });
    }
    @Test
    void setNewBook(){
        Loan loan = getLoan();
        Book book = new Book("new title", 220, new BigDecimal("19.908"),"new description for book");
        loan.setBook(book);
        Assertions.assertEquals(book, loan.getBook());
    }

    @Test
    void setLoanDateWithNullValue(){
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            loan.setLoanDate(null);
        });
    }
    @Test
    void setNewLoanDate(){
        Loan loan = getLoan();
        LocalDate localDate = LocalDate.parse("1890-09-01");
        loan.setLoanDate(localDate);
        Assertions.assertEquals(localDate, loan.getLoanDate());
    }
    @Test
    void getConcludedDefaultValue(){
        Loan loan = getLoan();
        Assertions.assertFalse(loan.isConcluded());
    }
    @Test
    void setConcluded(){
        Loan loan = getLoan();
        loan.setConcluded(true);
        Assertions.assertTrue(loan.isConcluded());
    }

    @Test
    void nonAvailableNewBookShouldNotBeSet(){
        Loan loan = getLoan();
        loan.getBook().setAvailable(false);
        Book book  = new Book("another new title", 34, new BigDecimal("44.1231"),"another new description");
        book.setAvailable(false);
        loan.setBook(book);
        Assertions.assertNotEquals(book, loan.getBook());
    }
    @Test
    void availableNewBookShouldBeSet(){
        Loan loan = getLoan();
        loan.getBook().setAvailable(false);
        Book book  = new Book("another new title", 34, new BigDecimal("44.1231"),"another new description");
        loan.setBook(book);
        Assertions.assertEquals(book, loan.getBook());
    }
}
