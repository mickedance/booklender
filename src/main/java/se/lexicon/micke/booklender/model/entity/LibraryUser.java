package se.lexicon.micke.booklender.model.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true)
    private int id;
    @Column(nullable = false, updatable = false)
    private LocalDate regDate;
    @Column(updatable = false,nullable = false)
    private String name;
    @Column(updatable = false,nullable = false, unique = true)
    private String email;


    public LibraryUser(LocalDate regDate, String name, String email) {
        setRegDate(regDate);
        setName(name);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id<0) throw new IllegalArgumentException("id must be Zero or above");
        this.id = id;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        if(regDate==null)  throw new IllegalArgumentException("regDate was null");
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null) throw new IllegalArgumentException("Name was null");
        if(name.equals("")) throw new IllegalArgumentException("Name was empty");

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email==null) throw new IllegalArgumentException("Email was null");
        if(email.equals("")) throw new IllegalArgumentException("Email was empty");

        this.email = email;
    }
}
