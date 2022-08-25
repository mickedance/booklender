package se.lexicon.micke.booklender.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoanDto {
    private long id;
    private LibraryUserDto loanTaker;
    private BookDto book;
    private LocalDate loanDate;
    private boolean concluded;

    public LoanDto(long id, LibraryUserDto loanTaker, BookDto book, LocalDate loanDate, boolean concluded) {
        setId(id);
        setLoanTaker(loanTaker);
        setBook(book);
        setLoanDate(loanDate);
        setConcluded(concluded);
    }

    public void setId(long id) {
        if(id<0) throw new IllegalArgumentException("id must be 0 or more");
        this.id = id;
    }

    public void setLoanTaker(LibraryUserDto loanTaker) {
        if(loanTaker==null )throw new IllegalArgumentException("loanTaker was null");
        this.loanTaker = loanTaker;
    }

    public void setLoanDate(LocalDate loanDate) {
        if(loanDate==null) throw new IllegalArgumentException("loanDate was null");
        this.loanDate = loanDate;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }
    public void setBook(BookDto book) {
        if(book==null) throw new IllegalArgumentException("book was null");
        this.book = book;
    }
}
