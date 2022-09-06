package se.lexicon.micke.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.micke.booklender.exception.ObjectNotFoundException;
import se.lexicon.micke.booklender.model.dto.LoanDto;
import se.lexicon.micke.booklender.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    private LoanService loanService;
    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> findById(@PathVariable("id") long id){
        try{
            LoanDto loanDto = loanService.findById(id);
            return ResponseEntity.ok().body(loanDto);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping()
    public ResponseEntity<LoanDto> create(@RequestBody LoanDto loanDto){
        LoanDto savedLoan = loanService.create(loanDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);
    }
    @PutMapping()
    public ResponseEntity<LoanDto> update(@RequestBody LoanDto loanDto){
        try {
            LoanDto updatedLoan = loanService.update(loanDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedLoan);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<LoanDto>> find(
            @RequestParam(name = "bookId", defaultValue = "ALL") String bookId,
            @RequestParam(name= "userId", defaultValue = "ALL") String userId,
            @RequestParam(name= "terminated", defaultValue = "ALL") String terminated,
            @RequestParam(name= "getAll", defaultValue = "false") String getAll
            ){
        try{
            if(!bookId.equals("ALL")){

                List<LoanDto> loanDtoList = loanService.findByBookId(new Integer(bookId));
                return ResponseEntity.ok().body(loanDtoList);
            }else if(!userId.equals("ALL")){
                List<LoanDto> loanDtoList = loanService.findByUserId(new Integer(userId));
                return ResponseEntity.ok().body(loanDtoList);
            }
            else if(!terminated.equals("ALL")){
                List<LoanDto> loanDtoList = loanService.findByConcluded(new Boolean(terminated));
                return ResponseEntity.ok().body(loanDtoList);
            }
            else if(getAll.equals("true") ){
                List<LoanDto> loanDtoList = loanService.findAll();
                return ResponseEntity.ok().body(loanDtoList);
            }
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
