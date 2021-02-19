package com.tarssito.libraryapi.service.impl;

import com.tarssito.libraryapi.api.dto.LoanFilterDTO;
import com.tarssito.libraryapi.exception.BusinessException;
import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.model.repository.LoanRepository;
import com.tarssito.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public Loan save(Loan loan) {
        if (loanRepository.existsByBookNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }
        return loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> getById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public Loan update(Loan loan) {
        return null;
    }

    @Override
    public Page<Loan> find(LoanFilterDTO filterDTO, Pageable pageable) {
        return null;
    }
}
