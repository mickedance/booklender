package se.lexicon.micke.booklender.model.entity;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanTest {

    private Loan getLoan() {
        Book book = new Book("title of book", 23, BigDecimal.ONE, "description");
        LibraryUser user = new LibraryUser(LocalDate.now(), "name is here", "email@.com");
        return new Loan(user, book, LocalDate.now(), false);
    }

    @Test
    @Order(1)
    void createNewLoan() {

        Loan loan = getLoan();
        System.out.println(loan);
        System.out.println(loan.getBook().getTitle());
    }

    @Test
    @Order(2)
    void createLoanWithNullValues() {
        Book book = new Book("title of book", 23, BigDecimal.ONE, "description");
        LibraryUser user = new LibraryUser(LocalDate.now(), "name is here", "email@.com");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(null, book, LocalDate.now(), false);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(user, null, LocalDate.now(), false);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan(user, book, null, false);
        });
    }

    @Test
    @Order(3)
    void setIdToWrongId() {
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loan.setId(-1);
        });
    }

    @Test
    @Order(4)
    void setIdTo11() {
        Loan loan = getLoan();
        loan.setId(11);
        Assertions.assertEquals(11, loan.getId());
    }

    @Test
    @Order(5)
    void setLoanTakerWithNullValue() {
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loan.setLoanTaker(null);
        });
    }

    @Test
    @Order(6)
    void setNewLoanTaker() {
        Loan loan = getLoan();
        LibraryUser user = new LibraryUser(LocalDate.now(), "new user", "new email");
        loan.setLoanTaker(user);
        Assertions.assertEquals(user, loan.getLoanTaker());
    }

    @Test
    @Order(7)
    void setBookWithNullValue() {
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loan.setBook(null);
        });
    }

    @Test
    @Order(8)
    void setNewBook() {
        Loan loan = getLoan();
        Book book = new Book("new title", 220, new BigDecimal("19.908"), "new description for book");
        loan.setBook(book);
        Assertions.assertEquals(book, loan.getBook());
    }

    @Test
    @Order(9)
    void setLoanDateWithNullValue() {
        Loan loan = getLoan();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loan.setLoanDate(null);
        });
    }

    @Test
    @Order(10)
    void setNewLoanDate() {
        Loan loan = getLoan();
        LocalDate localDate = LocalDate.parse("1890-09-01");
        loan.setLoanDate(localDate);
        Assertions.assertEquals(localDate, loan.getLoanDate());
    }

    @Test
    @Order(11)
    void getConcludedDefaultValue() {
        Loan loan = getLoan();
        Assertions.assertFalse(loan.isConcluded());
    }

    @Test
    @Order(12)
    void setConcluded() {
        Loan loan = getLoan();
        loan.setConcluded(true);
        Assertions.assertTrue(loan.isConcluded());
    }

    @Test
    @Order(13)
    void nonAvailableNewBookShouldNotBeSet() {
        Loan loan = getLoan();
        loan.getBook().setAvailable(false);
        Book book = new Book("another new title", 34, new BigDecimal("44.1231"), "another new description");
        book.setAvailable(false);
        loan.setBook(book);
        Assertions.assertNotEquals(book, loan.getBook());
    }

    @Test
    @Order(14)
    void availableNewBookShouldBeSet() {
        Loan loan = getLoan();
        loan.getBook().setAvailable(false);
        Book book = new Book("another new title", 34, new BigDecimal("44.1231"), "another new description");
        loan.setBook(book);
        Assertions.assertEquals(book, loan.getBook());
    }

    @Test
    @Order(15)
    void checkIfLoanIsOverDue() {
        LibraryUser user = getLoan().getLoanTaker();
        Book book = new Book("title of book", 10, new BigDecimal("10.01"), "desc");
        Loan loanOverDue = new Loan(user, book, LocalDate.now().minusDays(11), false);
        Assertions.assertTrue(loanOverDue.isOverDue());
        book.setAvailable(true);
        loanOverDue = new Loan(user, book, LocalDate.now().minusDays(10), false);
        Assertions.assertFalse(loanOverDue.isOverDue());
    }

    @Test
    @Order(16)
    void checkIfBookLoanTimeCanBeExtended() {
        Loan loan = getLoan();
        Assertions.assertTrue(loan.extendLoanDays());
        loan.getBook().setReserved(true);
        Assertions.assertFalse(loan.extendLoanDays());
    }
    @Test
    @Order(17)
    void getFine(){
        Loan loan = getLoan();
        loan.setLoanDate( LocalDate.now().minusDays(30) );
        loan.getBook().setMaxLoanDays(10);
        loan.getBook().setFinePerDay(new BigDecimal("10.5"));
        Assertions.assertEquals(new BigDecimal(210), loan.getFine());
    }
}
