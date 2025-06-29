package com.shubham.saas.core.service;

import com.shubham.saas.core.dto.TrialBalanceDto;
import com.shubham.saas.core.entity.Account;
import com.shubham.saas.core.entity.JournalEntry;
import com.shubham.saas.core.repository.AccountRepository;
import com.shubham.saas.core.repository.JournalEntryRepository;
import com.shubham.saas.core.repository.JournalLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AccountRepository accountRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final JournalLineRepository journalLineRepository;

    @Transactional
    public JournalEntry createJournalEntry(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    @Transactional(readOnly = true)
    public List<TrialBalanceDto> getTrialBalance(){
        List<Account> accounts = accountRepository.findAll();
        List<TrialBalanceDto> result = new ArrayList<>();
        for (Account account:accounts) {
            BigDecimal totalDebit = journalLineRepository.sumDebitByAccountId(account.getId()).orElse(BigDecimal.ZERO);
            BigDecimal totalCredit = journalLineRepository.sumCreditByAccountId(account.getId()).orElse(BigDecimal.ZERO);
            result.add(new TrialBalanceDto(account.getId(), account.getName(), totalDebit, totalCredit));
        }
        return result;
    }
}
