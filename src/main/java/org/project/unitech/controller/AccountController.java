package org.project.unitech.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.unitech.dto.response.MessageResponse;
import org.project.unitech.model.Account;
import org.project.unitech.service.AccountService;
import org.project.unitech.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Account account = accountService.createAccount(jwtUtil.getPinFromToken(token));
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/activate/{iban}")
    public ResponseEntity<?> activateAccount(HttpServletRequest request, @PathVariable String iban) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Account account = accountService.activateAccount(jwtUtil.getPinFromToken(token), iban);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAccounts(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<Account> accounts = accountService.getAccounts(jwtUtil.getPinFromToken(token));
            return ResponseEntity.status(HttpStatus.OK).body(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/addMoney/{amount}/{iban}")
    public ResponseEntity<?> addMoney(HttpServletRequest request, @PathVariable BigDecimal amount, @PathVariable String iban) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            accountService.increaseBalance(jwtUtil.getPinFromToken(token), iban, amount);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Successfully added " + amount + " to account " + iban));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/subtractMoney/{amount}/{iban}")
    public ResponseEntity<?> subtractMoney(HttpServletRequest request, @PathVariable BigDecimal amount, @PathVariable String iban) {
        String authorizationHeader = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            accountService.decreaseBalance(jwtUtil.getPinFromToken(token), iban, amount);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Succesfully subtracted " + amount + " from account " + iban));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }
}
