package se.lexicon.micke.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LibraryUserDto;
import se.lexicon.micke.booklender.service.LibraryUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class LibraryUserController {
    private LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LibraryUserDto> findById(@PathVariable("id") Integer id) {
        try {
            LibraryUserDto libraryUserDto = libraryUserService.findById(id);
            return ResponseEntity.ok().body(libraryUserDto);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LibraryUserDto> findByEmail(@PathVariable("email") String email){
        try {
            LibraryUserDto libraryUserDto = libraryUserService.findByEmail(email);
            return ResponseEntity.ok().body(libraryUserDto);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LibraryUserDto>> findAll() {
        return ResponseEntity.ok().body(libraryUserService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<LibraryUserDto> create(@RequestBody LibraryUserDto user) {
        LibraryUserDto libraryUserDto = libraryUserService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserDto);
    }

    @PutMapping()
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto user) {
        try {
            LibraryUserDto updatedUser = libraryUserService.update(user);
            return ResponseEntity.ok().body(updatedUser);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();

        }
    }
}
