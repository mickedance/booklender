package se.lexicon.micke.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.micke.booklender.model.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();
}
