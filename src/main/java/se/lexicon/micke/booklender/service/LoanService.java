package se.lexicon.micke.booklender.service;

import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LoanDto;

import java.util.List;

public interface LoanService {
    LoanDto findById(long id) throws ObjectNotFoundException;

    List<LoanDto> findByBookId(int bookId) throws ObjectNotFoundException;

    List<LoanDto> findByUserId(int id);

    List<LoanDto> findByConcluded(boolean status);

    List<LoanDto> findAll();

    LoanDto create(LoanDto loanDto);

    LoanDto update(LoanDto loanDto) throws ObjectNotFoundException;

    boolean deleteById(long id) throws ObjectNotFoundException;
}
