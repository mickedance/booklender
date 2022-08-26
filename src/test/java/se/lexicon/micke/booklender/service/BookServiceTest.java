package se.lexicon.micke.booklender.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.BookDto;

import java.math.BigDecimal;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {
    private BookService bookService;

    @Autowired
    public BookServiceTest(BookService bookService) {
        this.bookService = bookService;
    }

    BookDto getBookDto() {
        return new BookDto("a title for this book", true, false, 33, new BigDecimal(49.34), "des for this book");
    }

    @Test
    @Order(1)
    void create() {
        BookDto bookDto = new BookDto("a title", true, false, 33, new BigDecimal(44.4), "des");
        BookDto savedBookDto = bookService.create(bookDto);
        Assertions.assertEquals("a title", savedBookDto.getTitle());
        Assertions.assertEquals(new BigDecimal(44.4), savedBookDto.getFinePerDay());
        Assertions.assertEquals(33, savedBookDto.getMaxLoanDays());
        Assertions.assertEquals("des", savedBookDto.getDescription());
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.create(null));

    }

    @Test
    @Order(2)
    void update() {
        BookDto bookDto = getBookDto();
        BookDto savedBookDto = bookService.create(bookDto);
        savedBookDto.setDescription("hisandhers");
        savedBookDto.setTitle("newTitle");
        savedBookDto.setFinePerDay(new BigDecimal(99.09));
        savedBookDto.setReserved(true);
        savedBookDto.setAvailable(false);
        savedBookDto.setMaxLoanDays(100);
        BookDto updatedBookDto = bookService.update(savedBookDto);
        Assertions.assertEquals("hisandhers", updatedBookDto.getDescription());
        Assertions.assertEquals("newTitle", updatedBookDto.getTitle());
        Assertions.assertEquals(new BigDecimal(99.09), updatedBookDto.getFinePerDay());
        Assertions.assertTrue(updatedBookDto.isReserved());
        Assertions.assertFalse(updatedBookDto.isAvailable());
        Assertions.assertEquals(100, updatedBookDto.getMaxLoanDays());

        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.update(null));

    }

    @Test
    @Order(3)
    void deleteByBookId() {
        BookDto bookDto = getBookDto();
        BookDto savedBook = bookService.create(bookDto);
        try {
            Assertions.assertTrue(bookService.deleteById(savedBook.getBookId()));
        } catch (Exception e) {
        }

        Assertions.assertThrows(ObjectNotFoundException.class, () -> bookService.deleteById(2398745));
    }

    @Test
    @Order(4)
    void findAll() {
        Assertions.assertEquals(2, bookService.findAll().size());
    }

    @Test
    @Order(5)
    void findById() {
        BookDto bookDto = getBookDto();
        BookDto savedBookDto = bookService.create(bookDto);
        try {
            BookDto foundBookDto = bookService.findById(savedBookDto.getBookId());
            Assertions.assertThrows(ObjectNotFoundException.class, () -> bookService.findById(9393841));
        } catch (Exception e) {
        }
    }

    @Test
    @Order(6)
    void findByTitle() {
        BookDto bookDto1 = getBookDto();
        BookDto bookDto2 = getBookDto();
        BookDto bookDto3 = getBookDto();
        bookDto1.setTitle("titletosearchfor");
        bookDto2.setTitle("titletosearchfor");
        bookDto3.setTitle("titletosearchfor");
        bookService.update(bookDto1);
        bookService.update(bookDto2);
        bookService.update(bookDto3);

        Assertions.assertEquals(3, bookService.findByTitle("titletosearchfor").size());
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.findByTitle(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.findByTitle(""));
    }

    @Test
    @Order(7)
    void findByAvailable() {
        Assertions.assertEquals(5, bookService.findByAvailable(true).size());
        Assertions.assertEquals(1, bookService.findByAvailable(false).size());
    }

    @Test
    @Order(8)
    void findByReserved() {
        Assertions.assertEquals(1, bookService.findByReserved(true).size());
        Assertions.assertEquals(5, bookService.findByReserved(false).size());
    }
}
