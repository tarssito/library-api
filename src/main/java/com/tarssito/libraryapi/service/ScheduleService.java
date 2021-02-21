package com.tarssito.libraryapi.service;

import com.tarssito.libraryapi.model.entity.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private static final String CRON_LATE_LOANS = "0 0 0 1/1 * ?";

    private final LoanService loanService;

    @Value("${application.mail.lateloans.message}")
    private String message;
    private EmailService emailService;

    @Scheduled(cron = CRON_LATE_LOANS)
    public void sendMailToLateLoans() {
        List<Loan> allLateLoans = loanService.getAllLateLoans();
        List<String> emails = allLateLoans.stream()
                .map(Loan::getCostumerEmail)
                .collect(Collectors.toList());

        emailService.sendMails(message, emails);

    }
}
