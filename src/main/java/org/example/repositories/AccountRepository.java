package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByAccountNumber(String accountNumber);
}
