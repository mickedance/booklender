package se.lexicon.micke.booklender.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LoanDto;
import se.lexicon.micke.booklender.model.entity.Loan;
import se.lexicon.micke.booklender.repository.LoanRepository;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, ModelMapper modelMapper) {
        this.loanRepository = loanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto findById(long id) throws ObjectNotFoundException {
        if (id < 0) throw new IllegalArgumentException("id must be 0 or more");
        Loan loan = loanRepository.findById(id);
        if (loan == null) throw new ObjectNotFoundException("object could not be found");
        return modelMapper.map(loan, LoanDto.class);
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) throws ObjectNotFoundException {
        if (bookId < 0) throw new IllegalArgumentException("bookId must be 0 or more");
        List<Loan> list = loanRepository.findByBookBookId(bookId);
        if (list.isEmpty() || list == null) throw new ObjectNotFoundException("object not found");
        return modelMapper.map(list, new TypeToken<List<LoanDto>>() {
        }.getType());
    }

    @Override
    public List<LoanDto> findByUserId(int id) {

        if (id < 0) throw new IllegalArgumentException("id of user must be 0 or more");
        List<Loan> list = loanRepository.findByLoanTakerId(id);

        return modelMapper.map(list, new TypeToken<List<LoanDto>>() {
        }.getType());
    }

    @Override
    public List<LoanDto> findByConcluded(boolean status) {
        List<Loan> list = loanRepository.findByConcluded(status);

        return modelMapper.map(list, new TypeToken<List<LoanDto>>() {
        }.getType());
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> list = loanRepository.findAll();
        return modelMapper.map(list, new TypeToken<List<LoanDto>>() {
        }.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanDto create(LoanDto loanDto) {
        if (loanDto == null) throw new IllegalArgumentException("loanDto was null");
        Loan loan = modelMapper.map(loanDto, Loan.class);
        Loan savedLoan = loanRepository.save(loan);

        return modelMapper.map(savedLoan, LoanDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanDto update(LoanDto loanDto) throws ObjectNotFoundException {
        if (loanDto == null) throw new IllegalArgumentException("loanDto was null");
        if (!loanRepository.existsById(loanDto.getId()))
            throw new ObjectNotFoundException("loan with this id was not found");
        Loan toBeUpdated = modelMapper.map(loanDto, Loan.class);
        Loan updatedLoan = loanRepository.save(toBeUpdated);

        return modelMapper.map(updatedLoan, LoanDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(long id) throws ObjectNotFoundException {
        if (!loanRepository.existsById(id)) throw new ObjectNotFoundException("loan with this id was not found");
        loanRepository.deleteById(id);
        return true;
    }
}
