package se.lexicon.micke.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.BookDto;
import se.lexicon.micke.booklender.service.BookService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(bookService.findById(id));

        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    private ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.create(bookDto);
        return ResponseEntity.ok().body(savedBook);
    }

    @PutMapping()
    private ResponseEntity<BookDto> update( @RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = bookService.update(bookDto);
            return ResponseEntity.ok().body(updatedBook);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<BookDto>> find(
            @RequestParam(name = "title", defaultValue = "ALL") final String title,
            @RequestParam(name = "available", defaultValue = "ALL") final String available,
            @RequestParam(name = "reserved", defaultValue = "ALL") final String reserved,
            @RequestParam(name = "getAll", defaultValue = "false") final String getAll

    ) {
        List<BookDto> bookDtoList = new ArrayList<>();
        if (!title.equals("ALL")) {
            System.out.println("title is:"+ title);
            bookDtoList = bookService.findByTitle(title);
            return ResponseEntity.ok().body(bookDtoList);
        } else if (!available.equals("ALL")) {
            boolean b = new Boolean(available);
             bookDtoList = bookService.findByAvailable(b);
            return ResponseEntity.ok().body(bookDtoList);
        } else if (!reserved.equals("ALL")) {
            boolean b = new Boolean(reserved);
             bookDtoList = bookService.findByReserved(b);
            return ResponseEntity.ok().body(bookDtoList);
        }else if (getAll.equals("true")) {
            bookDtoList = bookService.findAll();
            return ResponseEntity.ok().body(bookDtoList);
        }
        return ResponseEntity.ok().body(bookDtoList);
    }
}
