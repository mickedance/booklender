package se.lexicon.micke.booklender.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.micke.booklender.model.entity.LibraryUser;

import java.time.LocalDate;

@SpringBootTest
public class LibraryUserDtoTest {


    LibraryUserDto getLibraryUserDto(){
        return new LibraryUserDto(22, LocalDate.parse("2020-01-01"), "a name", "email@email.com");
    }
    @Test
    void createNewLibraryUserDto(){
        LibraryUserDto libraryUserDto = new LibraryUserDto(11,LocalDate.parse("1900-01-01"), "name", "email.com");
        Assertions.assertEquals(11,libraryUserDto.getId());
        Assertions.assertEquals(LocalDate.parse("1900-01-01"),libraryUserDto.getRegDate());
        Assertions.assertEquals("name",libraryUserDto.getName());
        Assertions.assertEquals("email.com",libraryUserDto.getEmail());
    }
    @Test
    void setId(){
        LibraryUserDto libraryUserDto = getLibraryUserDto();
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setId(-1));
        libraryUserDto.setId(0);
        Assertions.assertEquals(0, libraryUserDto.getId());
    }
    @Test
    void setRegDate(){
        LibraryUserDto libraryUserDto = getLibraryUserDto();
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setRegDate(null));
        libraryUserDto.setRegDate(LocalDate.parse("1980-02-02"));
        Assertions.assertEquals(LocalDate.parse("1980-02-02"), libraryUserDto.getRegDate());
    }
    @Test
    void setName(){
        LibraryUserDto libraryUserDto = getLibraryUserDto();
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setName(null));
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setName(""));
        libraryUserDto.setName("AnotherName");
        Assertions.assertEquals("AnotherName", libraryUserDto.getName());
    }
    @Test
    void setEmail(){
        LibraryUserDto libraryUserDto = getLibraryUserDto();
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setEmail(null));
        Assertions.assertThrows(IllegalArgumentException.class,()->libraryUserDto.setEmail(""));
        libraryUserDto.setEmail("AnotherEmail");
        Assertions.assertEquals("AnotherEmail", libraryUserDto.getEmail());
    }
}
