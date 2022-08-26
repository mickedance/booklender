package se.lexicon.micke.booklender.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookDto {
    private int bookId;
    private String title;
    private boolean available;
    private boolean reserved;
    private int maxLoanDays;
    private BigDecimal finePerDay;
    private String description;

    public BookDto(String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {

        setTitle(title);
        setAvailable(available);
        setReserved(reserved);
        setMaxLoanDays(maxLoanDays);
        setFinePerDay(finePerDay);
        setDescription(description);
    }

    public BookDto(int bookId, String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {
        setBookId(bookId);
        setTitle(title);
        setAvailable(available);
        setReserved(reserved);
        setMaxLoanDays(maxLoanDays);
        setFinePerDay(finePerDay);
        setDescription(description);
    }

    public void setBookId(int bookId) {
        if (bookId < 0) throw new IllegalArgumentException("bookId must be 0 or more, was:" + bookId);
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        if (title == null || title.equals("")) throw new IllegalArgumentException("title was null or empty");
        this.title = title;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        if (maxLoanDays < 0) throw new IllegalArgumentException("maxLoanDats must be 0 or more");
        this.maxLoanDays = maxLoanDays;
    }

    public void setFinePerDay(BigDecimal finePerDay) {
        if (finePerDay.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("finePerDay must be 0 or more");
        this.finePerDay = finePerDay;
    }

    public void setDescription(String description) {
        if (description == null || description.equals(""))
            throw new IllegalArgumentException("description was null or empty");
        this.description = description;
    }
}
