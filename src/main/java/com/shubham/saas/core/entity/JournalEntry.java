package com.shubham.saas.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "journal_entries")
@NoArgsConstructor
@Data
public class JournalEntry {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private LocalDateTime entryDate;

    @OneToMany(mappedBy = "journalEntry", cascade = CascadeType.ALL)
    private List<JournalLine> lines = new ArrayList<>();

    @PrePersist
    private void validate(){
        BigDecimal totalDebit = lines.stream()
                .map(JournalLine::getDebit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCredit = lines.stream()
                .map(JournalLine::getCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalDebit.compareTo(totalCredit) != 0)
            throw new IllegalStateException("Debits and credits must balance");
    }
}
