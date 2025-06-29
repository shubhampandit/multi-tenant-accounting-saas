package com.shubham.saas.core.repository;

import com.shubham.saas.core.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
