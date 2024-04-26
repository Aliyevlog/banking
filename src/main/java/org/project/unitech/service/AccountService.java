package org.project.unitech.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.project.unitech.exception.AccountNotFoundException;
import org.project.unitech.exception.InsufficentFundsException;
import org.project.unitech.model.Account;
import org.project.unitech.model.User;
import org.project.unitech.model.enums.Status;
import org.project.unitech.repository.AccountRepository;
import org.project.unitech.repository.UserRepository;
import org.project.unitech.util.IbanUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Account createAccount(String pin) {
        User user = userRepository.findByPin(pin).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setIban(IbanUtil.generateAzerbaijaniIban());
        account.setStatus(Status.INACTIVE);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
        return account;
    }

    public Account activateAccount(String pin,String iban) {
        User user = userRepository.findByPin(pin).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Optional<Account> optionalAccount = user.getAccounts().stream()
                .filter(account -> iban.equals(account.getIban()))
                .findFirst();

        Account account = optionalAccount.orElseThrow(() ->
                new AccountNotFoundException("Account with IBAN " + iban + " not found or you do not have permission to access it"));

        account.setStatus(Status.ACTIVE);
        return accountRepository.save(account);
    }

    public List<Account> getAccounts(String pin) {
        User user = userRepository.findByPin(pin).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getAccounts().stream()
                .filter(account -> account.getStatus().equals(Status.ACTIVE))
                .toList();
    }

    public Account increaseBalance(String pin, String iban, BigDecimal amount) {
        Account account = accountRepository.findAccountByUserAndIban(pin, iban);
        try {
            account.getBalance();
        } catch (NullPointerException e) {
            throw new AccountNotFoundException("Account with IBAN " + iban + " not found or not activated yet!");
        }
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account decreaseBalance(String pin, String iban, BigDecimal amount) {
        Account account = accountRepository.findAccountByUserAndIban(pin, iban);
        BigDecimal initalBalance;
        try {
            initalBalance = account.getBalance();
        } catch (NullPointerException e) {
            throw new AccountNotFoundException("Account with IBAN " + iban + " not found or not activated yet!");
        }

        initalBalance = initalBalance.subtract(amount);

        if (initalBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficentFundsException("Insufficient funds for transaction.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }
}
