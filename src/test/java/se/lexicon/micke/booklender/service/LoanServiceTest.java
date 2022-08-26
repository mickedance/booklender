package se.lexicon.micke.booklender.service;

import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.BookDto;
import se.lexicon.micke.booklender.model.dto.LibraryUserDto;
import se.lexicon.micke.booklender.model.dto.LoanDto;
import se.lexicon.micke.booklender.model.entity.LibraryUser;
import se.lexicon.micke.booklender.model.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoanServiceTest {
    private LoanService loanService;
    private ModelMapper modelMapper;
    private BookService bookService;
    private LibraryUserService libraryUserService;

    @Autowired
    public LoanServiceTest(LoanServiceImpl loanService, ModelMapper modelMapper, BookService bookService
            , LibraryUserService libraryUserService) {
        this.loanService = loanService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.libraryUserService = libraryUserService;
    }

    private LibraryUserDto getSavedUser() {
        LibraryUserDto toBeSaved = new LibraryUserDto(LocalDate.parse("2020-02-02"), "name of user 1", "email@@.com" + new Random());
        LibraryUserDto savedUser = libraryUserService.create(toBeSaved);
        return savedUser;
    }

    private BookDto getSavedBookDto() {
        BookDto toBeSaved = new BookDto("bookTitle", true, false, 44, new BigDecimal(66.982), "description");
        BookDto savedBook = bookService.create(toBeSaved);
        return savedBook;
    }

    private LoanDto getSavedLoanDto() {
        LoanDto toBeSaved = new LoanDto(getSavedUser(), getSavedBookDto(), LocalDate.now(), false);
        return loanService.create(toBeSaved);
    }

    @Test
    @Order(1)
    void create() {
        LoanDto savedLoanDto = getSavedLoanDto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanService.create(null));

        Assertions.assertEquals("bookTitle", savedLoanDto.getBook().getTitle());
        Assertions.assertEquals(LocalDate.parse("2020-02-02"), savedLoanDto.getLoanTaker().getRegDate());
        Assertions.assertEquals("name of user 1", savedLoanDto.getLoanTaker().getName());
        Assertions.assertEquals("bookTitle", savedLoanDto.getBook().getTitle());
        Assertions.assertEquals(LocalDate.now(), savedLoanDto.getLoanDate());
        System.out.println("saved:" + savedLoanDto);
    }

    @Test
    @Order(2)
    void findAll() {
        Assertions.assertEquals(1, loanService.findAll().size());
    }

    @Test
    @Order(3)
    void findById() {
        try {
            LoanDto loanDto = loanService.findById(1);
            Assertions.assertNotNull(loanDto);
            Assertions.assertThrows(ObjectNotFoundException.class, () -> loanService.findById(12341));
            Assertions.assertThrows(IllegalArgumentException.class, () -> loanService.findById(-1));
        } catch (Exception e) {
            Assertions.assertEquals("this exception should not be viewed: " + e, "");
        }

    }

    @Test
    @Order(4)
    void findByBookId() {
        LoanDto loan1 = getSavedLoanDto();
        LoanDto loan2 = getSavedLoanDto();
        LoanDto loan3 = getSavedLoanDto();
        loan2.setBook(loan1.getBook());
        loan3.setBook(loan1.getBook());
        try {
            loanService.update(loan1);
            loanService.update(loan2);
            loanService.update(loan3);
            Assertions.assertEquals(3, loanService.findByBookId(loan1.getBook().getBookId()).size());
            Assertions.assertThrows(IllegalArgumentException.class, () -> loanService.findByBookId(-1));
            Assertions.assertThrows(ObjectNotFoundException.class, () -> loanService.findByBookId(3999999));

        } catch (Exception e) {
            Assertions.assertEquals("this exception should not be viewed:" + e, "");
        }

    }

    @Test
    @Order(5)
    void findByUserId() {
        LibraryUserDto user = loanService.findAll().stream().findFirst().orElse(null).getLoanTaker();
        Assertions.assertNotNull(user);
        Assertions.assertThrows(IllegalArgumentException.class,()->loanService.findByUserId(-1));
        LoanDto loan1 = getSavedLoanDto();
        LoanDto loan2 = getSavedLoanDto();
        LoanDto loan3 = getSavedLoanDto();
        loan1.setLoanTaker(user);
        loan2.setLoanTaker(user);
        loan3.setLoanTaker(user);
        try {
            loanService.update(loan1);
            loanService.update(loan2);
            loanService.update(loan3);
            Assertions.assertEquals(4, loanService.findByUserId(loan1.getLoanTaker().getId()).size());
        } catch (Exception e) {
            Assertions.assertEquals("this exception should not be viewed: " + e, "");

        }
    }

    @Test
    @Order(6)
    void findByConcluded() {
        List<LoanDto> list = loanService.findAll();
        list.stream().forEach(loanDto -> loanDto.setConcluded(true));
        try {
            for (LoanDto l : list) {
                loanService.update(l);
            }
            System.out.println("list size::" + list.size());
            Assertions.assertEquals(7, loanService.findByConcluded(true).size());
            LoanDto loanToUpdate = loanService.findAll().stream().findFirst().map(loanDto -> {
                loanDto.setConcluded(false);
                return loanDto;
            }).orElse(null);
            loanService.update(loanToUpdate);
            Assertions.assertEquals(1, loanService.findByConcluded(false).size());
        } catch (Exception e) {
            Assertions.assertEquals("this exception should not be viewed:" + e, "");
        }

    }
}
