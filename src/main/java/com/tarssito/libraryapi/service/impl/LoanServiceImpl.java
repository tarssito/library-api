package com.tarssito.libraryapi.service.impl;

import com.tarssito.libraryapi.model.entity.Loan;
import com.tarssito.libraryapi.model.repository.LoanRepository;
import com.tarssito.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }
}
