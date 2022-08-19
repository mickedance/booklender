package se.lexicon.micke.booklender.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.model.entity.Book;
import se.lexicon.micke.booklender.model.entity.LibraryUser;
import se.lexicon.micke.booklender.model.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanRepositoryTest {

    private LoanRepository loanRepository;
    private LibraryUserRepository libraryUserRepository;
    private BookRepository bookRepository;

    @Autowired
    public LoanRepositoryTest(LoanRepository loanRepository, LibraryUserRepository libraryUserRepository, BookRepository bookRepository) {
        this.libraryUserRepository = libraryUserRepository;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    private Book getBook() {
        return new Book("book title", 23, new BigDecimal("14.42"), "description of book");
    }

    private LibraryUser getUser() {
        return new LibraryUser(LocalDate.now(), "username goes here so", "email@.com" + new Random().toString());
    }

    private Loan getLoan() {
        return new Loan(getUser(), getBook(), LocalDate.now(), false);
    }

    @Test
    @Order(1)
    void saveLoan() {
        Loan loan = getLoan();
        Loan savedLoan = loanRepository.save(loan);
        Assertions.assertEquals(loan.getLoanDate(), savedLoan.getLoanDate());
        Assertions.assertEquals(loan.getBook(), savedLoan.getBook());
        Assertions.assertEquals(loan.getLoanTaker().getEmail(), savedLoan.getLoanTaker().getEmail());
    }

    @Test
    @Order(2)
    void updateBookDescription() {
        Loan loan = getLoan();
        Loan savedLoan = loanRepository.save(loan);
        Book book = bookRepository.findById(savedLoan.getBook().getBookId()).orElse(null);
        book.setDescription("different description");
        Book updated = bookRepository.save(book);
        Assertions.assertEquals("different description", loanRepository.findById(savedLoan.getId()).get().getBook().getDescription());
    }

    @Test
    @Order(3)
    void deleteLoan() {
        Loan loan = getLoan();
        Loan saved = loanRepository.save(loan);
        loanRepository.delete(saved);
        Assertions.assertFalse(loanRepository.findById(saved.getId()).isPresent());
    }

    @Test
    @Order(4)
    void updateLoan() {
        Loan loan = getLoan();
        Loan saved = loanRepository.save(loan);
        saved.setConcluded(true);
        loanRepository.save(saved);
        Assertions.assertTrue(loanRepository.findById(saved.getId()).get().isConcluded());
    }

    @Test
    @Order(5)
    void findAllByUserId() {
        Loan loan = new Loan(getUser(), getBook(), LocalDate.now(), false);
        loanRepository.save(loan);
        List<Loan> loanList = loanRepository.findAllByLoanTakerId(1);
        Assertions.assertEquals(1, loanList.size());
    }

    @Test
    @Order(6)
    void findByConcludedStatus() {
        Assertions.assertEquals(1, loanRepository.findByConcluded(true).size());
        Assertions.assertEquals(3, loanRepository.findByConcluded(false).size());
    }

    @Test
    @Order(7)
    void findByBookId() {
        Assertions.assertEquals(1, loanRepository.findByBookBookId(1).size());
    }
}
