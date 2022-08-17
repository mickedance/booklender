package se.lexicon.micke.booklender.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class LibraryUserTest {

    @Test
    void createEmptyLibraryUser(){
        LibraryUser user = new LibraryUser();
        Assertions.assertNotNull(user);
    }
    @Test
    void createUserWithAllArgsButId(){
        LibraryUser user = new LibraryUser(LocalDate.parse("1990-09-09"), "Anders", "email@.com");
        String expected = "email@.com";
        Assertions.assertEquals(expected, user.getEmail());
        Assertions.assertEquals(LocalDate.parse("1990-09-09"), user.getRegDate());
        expected = "Anders";
        Assertions.assertEquals(expected, user.getName());

    }
    @Test
    void setUserNameWithCorrectParam(){
        LibraryUser user = new LibraryUser();
        String expected = "Susan";
        user.setName(expected);
        Assertions.assertEquals(expected, user.getName());
    }
    @Test
    void setUserNameWithNullParam(){
        LibraryUser user = new LibraryUser();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            user.setName(null);
        });
    }
    @Test
    void setUserNameWithEmptyName(){
        LibraryUser user = new LibraryUser();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            user.setName("");
        });
    }
    @Test
    void setUserEmailWithNullParam(){
        LibraryUser user = new LibraryUser();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            user.setEmail(null);
        });
    }
    @Test
    void setUserEmailWithEmptyEmail(){
        LibraryUser user = new LibraryUser();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            user.setEmail("");
        });
    }
    @Test
    void setUserRegdateWithNullParam(){
        LibraryUser user = new LibraryUser();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            user.setRegDate(null);;
        });
    }
}
