package se.lexicon.micke.booklender.model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LibraryUserDto {
    private int id;
    private LocalDate regDate;
    private String name;
    private String email;

    public LibraryUserDto(LocalDate regDate, String name, String email) {

        setRegDate(regDate);
        setName(name);
        setEmail(email);
    }

    public LibraryUserDto(int id, LocalDate regDate, String name, String email) {
        setId(id);
        setRegDate(regDate);
        setName(name);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("id must be 0 or more");
        this.id = id;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        if (regDate == null) throw new IllegalArgumentException("regDate was null");
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("name was empty or null");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.equals("")) throw new IllegalArgumentException("email was empty or null");
        this.email = email;
    }
}
