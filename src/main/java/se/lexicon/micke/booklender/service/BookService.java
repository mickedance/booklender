package se.lexicon.micke.booklender.service;

import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findByReserved(boolean reserved);

    List<BookDto> findByAvailable(boolean available);

    List<BookDto> findByTitle(String title);

    BookDto findById(Integer bookId) throws ObjectNotFoundException;

    List<BookDto> findAll();

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto) throws ObjectNotFoundException;

    boolean deleteById(Integer bookId) throws ObjectNotFoundException;
}
