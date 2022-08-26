package se.lexicon.micke.booklender.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LibraryUserDto;

import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryUserServiceTest {
    private LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserServiceTest(LibraryUserServiceImpl libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    LibraryUserDto getUserDto() {
        return new LibraryUserDto(LocalDate.parse("2020-02-02"), "thisname", "thisemail@.com" + new Random());
    }

    @Test
    @Order(1)
    void createUserDto() {
        LibraryUserDto libraryUserDto = libraryUserService.create(getUserDto());
        Assertions.assertEquals(LocalDate.parse("2020-02-02"), libraryUserDto.getRegDate());
        Assertions.assertEquals("thisname", libraryUserDto.getName());
        System.out.println("Saved user:" + libraryUserDto);
    }

    @Test
    @Order(2)
    void updateUserDto() {
        LibraryUserDto libraryUserDto = libraryUserService.create(getUserDto());
        libraryUserDto.setEmail("thisnewEmailhere@@");
        libraryUserDto.setName("thinewName");
        libraryUserDto.setRegDate(LocalDate.parse("2000-01-01"));
        try {
            LibraryUserDto updatedUser = libraryUserService.update(libraryUserDto);
            Assertions.assertEquals("thisnewEmailhere@@", updatedUser.getEmail());
            Assertions.assertEquals("thinewName", updatedUser.getName());
            Assertions.assertEquals(LocalDate.parse("2000-01-01"), updatedUser.getRegDate());
            Assertions.assertThrows(IllegalArgumentException.class, () -> libraryUserService.update(null));
            updatedUser.setId(9890);
            Assertions.assertThrows(ObjectNotFoundException.class, () -> libraryUserService.update(updatedUser));
        } catch (Exception e) {

        }
    }

    @Test
    @Order(3)
    void findById() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> libraryUserService.findById(-1));
        Assertions.assertThrows(ObjectNotFoundException.class, () -> libraryUserService.findById(237892345));
        LibraryUserDto userToSave = getUserDto();
        LibraryUserDto savedDtoUSer = libraryUserService.create(userToSave);
        Assertions.assertEquals(userToSave.getEmail(), savedDtoUSer.getEmail());
        Assertions.assertEquals(userToSave.getName(), savedDtoUSer.getName());
        Assertions.assertEquals(userToSave.getRegDate(), savedDtoUSer.getRegDate());
    }

    @Test
    @Order(4)
    void findAll() {
        System.out.println(libraryUserService.findAll().size());
        Assertions.assertEquals(3, libraryUserService.findAll().size());
    }

    @Test
    @Order(5)
    void findByEmail() {
        LibraryUserDto toBeSaved = getUserDto();
        LibraryUserDto savedUserDto = libraryUserService.create(toBeSaved);
        try {
            Assertions.assertEquals(savedUserDto, libraryUserService.findByEmail(savedUserDto.getEmail()));
            Assertions.assertThrows(IllegalArgumentException.class, () -> libraryUserService.findByEmail(""));
            Assertions.assertThrows(IllegalArgumentException.class, () -> libraryUserService.findByEmail(null));
        } catch (Exception e) {

        }
    }

    @Test
    @Order(6)
    void deleteById() {
        LibraryUserDto toBeSaved = getUserDto();
        LibraryUserDto savedUserDto = libraryUserService.create(toBeSaved);
        Assertions.assertThrows(ObjectNotFoundException.class, () -> libraryUserService.delete(12343291));
        Assertions.assertThrows(IllegalArgumentException.class, () -> libraryUserService.delete(-1));
        try {
            Assertions.assertTrue(libraryUserService.delete(savedUserDto.getId()));
        } catch (Exception e) {
        }
    }
}
