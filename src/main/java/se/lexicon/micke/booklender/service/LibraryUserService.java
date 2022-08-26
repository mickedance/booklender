package se.lexicon.micke.booklender.service;

import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LibraryUserDto;

import java.util.List;

public interface LibraryUserService {
    LibraryUserDto findById(int id) throws ObjectNotFoundException;

    LibraryUserDto findByEmail(String email) throws ObjectNotFoundException;

    List<LibraryUserDto> findAll();

    LibraryUserDto create(LibraryUserDto libraryUserDto);

    LibraryUserDto update(LibraryUserDto libraryUserDto) throws ObjectNotFoundException;

    boolean delete(int id) throws ObjectNotFoundException;
}
