package se.lexicon.micke.booklender.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.model.entity.Book;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

    private BookRepository bookRepository;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private Book getbook() {
        return new Book("title of this book", 99, new BigDecimal("12.01"), "about this book here:");

    }

    @Test
    @Order(1)
    void saveBook() {
        Book book = getbook();
        Book savedBook = bookRepository.save(book);
        Assertions.assertEquals(book.getMaxLoanDays(), savedBook.getMaxLoanDays());
        Assertions.assertEquals(book.getTitle(), savedBook.getTitle());
        Assertions.assertEquals(book.getDescription(), savedBook.getDescription());
        Assertions.assertEquals(book.getFinePerDay(), savedBook.getFinePerDay());
    }

    @Test
    @Order(2)
    void deleteBook() {
        Book book = getbook();
        book.setTitle("a new title for this book");
        Book savedBook = bookRepository.save(book);
        bookRepository.delete(savedBook);
        Assertions.assertFalse(bookRepository.findById(savedBook.getBookId()).isPresent());
    }

    @Test
    @Order(3)
    void updateBook() {
        Book book = getbook();
        book.setTitle("a newer title for this book");
        Book savedBook = bookRepository.save(book);

        savedBook.setAvailable(false);
        savedBook.setDescription("som desc");
        savedBook.setMaxLoanDays(220);
        savedBook.setReserved(true);

        bookRepository.save(savedBook);
        Assertions.assertEquals(savedBook, bookRepository.findById(savedBook.getBookId()).get());
    }

    @Test
    @Order(4)
    void findById() {
        Book book = getbook();
        book.setDescription("som desc goes here");
        Book savedBook = bookRepository.save(book);
        Assertions.assertEquals(savedBook, bookRepository.findById(savedBook.getBookId()).get());
    }

    @Test
    @Order(5)
    void findAll() {
        Assertions.assertEquals(3, bookRepository.findAll().size());
    }

    @Test
    @Order(6)
    void findByReservedStatus() {
        List<Book> books = bookRepository.findAllByReserved(true);
        Assertions.assertEquals(1, books.size());
    }

    @Test
    @Order(7)
    void findByAvailableStatus() {
        List<Book> books = bookRepository.findAllByAvailable(true);
        Assertions.assertEquals(2, books.size());
    }

    @Test
    @Order(8)
    void findByTitle() {
        Book book = getbook();
        book.setTitle("title1234");
        Book savedBook = bookRepository.save(book);
        book = getbook();
        book.setTitle("title1234");
        savedBook = bookRepository.save(book);
        book = getbook();
        book.setTitle("title1234");
        savedBook = bookRepository.save(book);
        List<Book> books = bookRepository.findAllByTitle("title1234");
        Assertions.assertEquals(3, bookRepository.findAllByTitle("title1234").size());
    }
}
