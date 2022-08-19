package se.lexicon.micke.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.micke.booklender.model.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findAll();
    List<Loan> findAllByLoanTakerId(int id);
    List<Loan> findByConcluded(boolean concluded);
    List<Loan> findByBookBookId(int id);
}
