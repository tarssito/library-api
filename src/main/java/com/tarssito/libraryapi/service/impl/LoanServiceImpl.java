package com.tarssito.libraryapi.service.impl;

import com.tarssito.libraryapi.exception.BusinessException;
import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.model.repository.LoanRepository;
import com.tarssito.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;

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
        return Optional.empty();
    }

    @Override
    public Loan update(Loan loan) {
        return null;
    }
}
