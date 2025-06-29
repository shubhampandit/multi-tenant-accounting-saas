package com.shubham.saas.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.math.BigDecimal;

@Entity
@Table(name = "journal_lines")
@NoArgsConstructor
@Data
public class JournalLine {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "journal_entry_id")
    private JournalEntry journalEntry;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
}
