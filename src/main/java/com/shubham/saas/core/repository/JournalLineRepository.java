package com.shubham.saas.core.repository;

import com.shubham.saas.core.entity.JournalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface JournalLineRepository extends JpaRepository<JournalLine, Long> {

    @Query("SELECT COALESCE(SUM(jl.debit), 0) FROM JournalLine jl WHERE jl.account.id = :account_id")
    public Optional<BigDecimal> sumDebitByAccountId(@Param("account_id") Long accountId);

    @Query("SELECT COALESCE(SUM(jl.credit), 0) FROM JournalLine jl WHERE jl.account.id = :account_id")
    public Optional<BigDecimal> sumCreditByAccountId(@Param("account_id") Long accountId);
}
