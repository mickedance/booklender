package se.lexicon.micke.booklender.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class LoanDtoTest {

    private LibraryUserDto getLibraryUserDto() {
        return new LibraryUserDto(300, LocalDate.parse("2000-09-09"), "user name", "emai@.se");
    }

    private BookDto getBookDto() {
        return new BookDto(43, "title of book", true, false, 22, new BigDecimal(45), "description of book");
    }

    private LoanDto getLoanDto() {
        return new LoanDto(44, getLibraryUserDto(), getBookDto(), LocalDate.parse("2009-09-09"), false);
    }

    @Test
    void createLonDto() {
        LoanDto loanDto = getLoanDto();
        Assertions.assertEquals(44, loanDto.getId());
        Assertions.assertEquals(getLibraryUserDto().getEmail(), loanDto.getLoanTaker().getEmail());
        Assertions.assertEquals("title of book", loanDto.getBook().getTitle());
        Assertions.assertEquals(LocalDate.parse("2009-09-09"), loanDto.getLoanDate());
        Assertions.assertEquals(false, loanDto.isConcluded());

    }

    @Test
    void setId() {
        LoanDto loanDto = getLoanDto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanDto.setId(-1));
        loanDto.setId(22);
        Assertions.assertEquals(22, loanDto.getId());
    }

    @Test
    void setLibraryUserDto() {
        LoanDto loanDto = getLoanDto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanDto.setLoanTaker(null));
        LibraryUserDto libraryUserDto = new LibraryUserDto(99, LocalDate.parse("1200-01-01"), "newname", "newmaile.com");
        loanDto.setLoanTaker(libraryUserDto);
        Assertions.assertEquals(libraryUserDto, loanDto.getLoanTaker());
    }

    @Test
    void setBook() {
        LoanDto loanDto = getLoanDto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanDto.setBook(null));
        BookDto bookDto = new BookDto(189, "title of this book", true, false, 44, new BigDecimal(100), "about this book");
        loanDto.setBook(bookDto);
        Assertions.assertEquals(bookDto, loanDto.getBook());
    }

    @Test
    void setLoanDate() {
        LoanDto loanDto = getLoanDto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> loanDto.setLoanDate(null));
        loanDto.setLoanDate(LocalDate.parse("1999-06-06"));
        Assertions.assertEquals(LocalDate.parse("1999-06-06"), loanDto.getLoanDate());
    }

    @Test
    void setConcluded() {
        LoanDto loanDto = getLoanDto();
        loanDto.setConcluded(true);
        Assertions.assertTrue(loanDto.isConcluded());
    }
}
