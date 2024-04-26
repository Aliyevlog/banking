package org.project.unitech.repository;

import org.project.unitech.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.user.pin = :pin and a.iban = :iban")
    Account findAccountByUserAndIban(@Param("pin") String pin, @Param("iban") String iban);

    Optional<Account> findAccountByIban(String iban);
}

