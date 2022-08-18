package se.lexicon.micke.booklender.model.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;


@ToString
@EqualsAndHashCode
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true)
    int bookId;
    @Column(nullable = false, updatable = false)
    private String title;
    @Column(nullable = false)
    boolean available = true;
    @Column(nullable = false)
    boolean reserved = false;
    @Column(nullable = false)
    private int maxLoanDays ;
    @Column(nullable = false)
    private BigDecimal finePerDay;
    @Column(nullable = false)
    private String description;

    public Book(){
        setMaxLoanDays(20);
    }

    public Book(String title, int maxLoanDays, BigDecimal finePerDay, String description) {
        this.title = title;
        this.maxLoanDays = maxLoanDays;
        this.finePerDay = finePerDay;
        this.description = description;
    }

    public Book(String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {
        this();
        setTitle(title);
        setAvailable(available);
        setReserved(reserved);
        setMaxLoanDays(maxLoanDays);
        setFinePerDay(finePerDay);
        setDescription(description);
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        if(bookId<0) throw new IllegalArgumentException("bookId is not valid");
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title== null || title.equals("")) throw new IllegalArgumentException("title was null or empty");
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        if(maxLoanDays<0) throw new IllegalArgumentException("maxLoanDats must be 0 or more");
        this.maxLoanDays = maxLoanDays;
    }

    public BigDecimal getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(BigDecimal finePerDay) {
        if(finePerDay.compareTo(BigDecimal.ZERO)<=0 ) throw new IllegalArgumentException("finePerDay must be 0 or more");
        this.finePerDay = finePerDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description== null || description.equals("")) throw new IllegalArgumentException("description was null or empty");

        this.description = description;
    }
}
