package se.lexicon.micke.booklender.model.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class LoanTest {

    @Test
    void createNewLoan(){
        Book book = new Book("title of book",23, BigDecimal.ONE,"description");
        LibraryUser user = new LibraryUser(LocalDate.now(), "name is here", "email@.com");
        Loan loan = new Loan(user,book, LocalDate.now(),false);
        System.out.println(loan);
        System.out.println(loan.getBook().getTitle());
    }
}
