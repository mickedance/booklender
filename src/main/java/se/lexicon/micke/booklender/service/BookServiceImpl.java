package se.lexicon.micke.booklender.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.BookDto;
import se.lexicon.micke.booklender.model.entity.Book;
import se.lexicon.micke.booklender.repository.BookRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDto> findByReserved(boolean reserved) {
        List<Book> list = bookRepository.findAllByReserved(reserved);
        return modelMapper.map(list,
                new TypeToken<List<BookDto>>() {
                }.getType());
    }

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        List<Book> list = bookRepository.findAllByAvailable(available);
        return modelMapper.map(list,
                new TypeToken<List<BookDto>>() {
                }.getType());
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        if (title == null || title.equals("")) throw new IllegalArgumentException("title was null or empty");
        List<Book> list = bookRepository.findAllByTitle(title);
        return modelMapper.map(list,
                new TypeToken<List<BookDto>>() {
                }.getType());
    }

    @Override
    public BookDto findById(int bookId) throws ObjectNotFoundException {
        if (bookId < 0) throw new IllegalArgumentException("bookId ust be 0 or more");
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent())
            throw new ObjectNotFoundException("book with id was not found");
        return modelMapper.map(bookRepository.findById(bookId).get(), BookDto.class);

    }

    @Override
    public List<BookDto> findAll() {
        List<Book> list = bookRepository.findAll();
        return modelMapper.map(list,
                new TypeToken<List<BookDto>>() {
                }.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDto create(BookDto bookDto) {
        if (bookDto == null) throw new IllegalArgumentException("bookDto was null");
        Book bookToSave = modelMapper.map(bookDto, Book.class);
        Book savedBook = bookRepository.save(bookToSave);
        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDto update(BookDto bookDto) {
        if (bookDto == null) throw new IllegalArgumentException("bookDto was null");
        Book updatedBook = bookRepository.save(modelMapper.map(bookDto, Book.class));

        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(int bookId) throws ObjectNotFoundException {
        if (!bookRepository.existsById(bookId)) throw new ObjectNotFoundException("Object not found");
        bookRepository.deleteById(bookId);
        return true;
    }
}
