package com.shubham.saas.core.controller;

import com.shubham.saas.core.dto.TrialBalanceDto;
import com.shubham.saas.core.entity.JournalEntry;
import com.shubham.saas.core.service.AccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounting")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingService accountingService;

    @PostMapping("/journal-entry")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry){
        JournalEntry saved = accountingService.createJournalEntry(journalEntry);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/trial-balance")
    public ResponseEntity<List<TrialBalanceDto>> getTrialBalance(){
        List<TrialBalanceDto> trialBalance = accountingService.getTrialBalance();
        return ResponseEntity.ok(trialBalance);
    }
}
