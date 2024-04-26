package org.project.unitech.service;

import jakarta.transaction.Transactional;
import org.project.unitech.dto.request.TransferRequestDto;
import org.project.unitech.dto.response.TransferResponseDto;
import org.project.unitech.exception.AccountNotFoundException;
import org.project.unitech.model.Account;
import org.project.unitech.model.enums.Status;
import org.project.unitech.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public TransactionService(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public TransferResponseDto makeTransfer(String pin,TransferRequestDto transferRequestDto) {
        String fromIban = transferRequestDto.getFromIban();
        String toIban = transferRequestDto.getToIban();
        BigDecimal amount = transferRequestDto.getAmount();

        if (fromIban.equals(toIban)) {
            throw new IllegalArgumentException("Cannot transfer to the same account!");
        }

        Account fromAccount = accountRepository.findAccountByIban(fromIban).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account " + fromIban));
        if (fromAccount.getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("Receiving account is not activated!");
        }

        Account toAccount = accountRepository.findAccountByIban(fromIban).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account " + fromIban));

        if (toAccount.getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("Your account is not activated!");
        }

        accountService.decreaseBalance(pin, fromIban, amount);
        accountService.increaseBalance(pin, toIban, amount);
        return new TransferResponseDto(fromIban, toIban, amount, "Transfer successful.");
    }
}
