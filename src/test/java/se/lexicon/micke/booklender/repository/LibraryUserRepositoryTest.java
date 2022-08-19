package se.lexicon.micke.booklender.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.model.entity.LibraryUser;

import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryUserRepositoryTest {

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserRepositoryTest(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    private LibraryUser getUser() {
        return new LibraryUser(LocalDate.now(), "user has a name", "useremail@.com" + new Random().toString());
    }

    @Test
    @Order(1)
    void saveLibraryUser() {
        LibraryUser user = getUser();
        LibraryUser savedUser = libraryUserRepository.save(user);
        System.out.println("saved" + savedUser);
        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(user.getRegDate(), savedUser.getRegDate());
    }

    @Test
    @Order(2)
    void findById() {
        LibraryUser user = getUser();
        user.setName("another name");
        LibraryUser savedUser = libraryUserRepository.save(user);
        Assertions.assertEquals(savedUser, libraryUserRepository.findById(savedUser.getId()).get());
    }

    @Test
    @Order(3)
    void doNotFindById() {
        LibraryUser user = getUser();
        user.setName("another name again");
        LibraryUser savedUser = libraryUserRepository.save(user);
        Assertions.assertFalse(libraryUserRepository.findById(savedUser.getId() + 1).isPresent());
    }

    @Test
    @Order(4)
    void deleteById() {
        LibraryUser user = getUser();
        user.setName("to delete");
        LibraryUser savedUser = libraryUserRepository.save(user);
        libraryUserRepository.deleteById(savedUser.getId());
        LibraryUser findUser = libraryUserRepository.findById(savedUser.getId()).orElse(null);
        Assertions.assertNull(findUser);
    }

    @Test
    @Order(5)
    void findAll() {
        Assertions.assertEquals(3, libraryUserRepository.findAll().size());
    }

    @Test
    @Order(6)
    void findByEmail() {
        LibraryUser user = getUser();
        LibraryUser savedUser = libraryUserRepository.save(user);
        LibraryUser result = libraryUserRepository.findByEmail(user.getEmail()).orElse(null);
        Assertions.assertNotNull(result);
    }
}
