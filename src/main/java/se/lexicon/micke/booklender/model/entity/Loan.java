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
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true)
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private LibraryUser loanTaker;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Book book;
    @Column(nullable = false, updatable = false)
    private LocalDate loanDate = LocalDate.now();
    @Column(nullable = false)
    private boolean concluded;

    public Loan(LibraryUser loanTaker, Book book, LocalDate loanDate, boolean concluded) {
        setLoanTaker(loanTaker);
        setBook(book);
        setLoanDate(loanDate);
        setConcluded(concluded);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id<0) throw new IllegalArgumentException("id must be 0 or more");
        this.id = id;
    }

    public LibraryUser getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUser loanTaker) {
        if(loanTaker==null) throw new IllegalArgumentException("loanTaker was null");
        this.loanTaker = loanTaker;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        if(book==null) throw new IllegalArgumentException("book was null");
        if(book.isAvailable())
            this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        if(loanDate==null) throw new IllegalArgumentException("loanDate was null");
        this.loanDate = loanDate;
    }

    public boolean isConcluded() {
        return concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }

    public boolean isOverDue(){
        LocalDate loanDays = LocalDate.of(loanDate.getYear(), loanDate.getMonth(), loanDate.getDayOfMonth()).plusDays(book.getMaxLoanDays());
        return loanDays.compareTo(LocalDate.now())>=0 ? false: true;
    }

    public boolean extendLoanDays(){
        return !book.isReserved();
    }
}
