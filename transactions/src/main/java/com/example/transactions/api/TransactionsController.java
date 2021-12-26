package com.example.transactions.api;


import com.example.transactions.model.Transaction;
import com.example.transactions.model.TransactionPoint;
import com.example.transactions.model.dto.ServiceDto;
import com.example.transactions.model.dto.TransactionDto;
import com.example.transactions.model.dto.UserDto;
import com.example.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/transactions")
@RestController
public final class TransactionsController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> showAll() {
        final List<Transaction> transactions = transactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> showById(@PathVariable long id) {
        try {
            final Transaction transaction = transactionService.getById(id);

            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/service")
    public ResponseEntity<ServiceDto> getServiceByTransactionId(@PathVariable long id) {
        try {
            final ServiceDto serviceDto = transactionService.getServiceByTransactionId(id);

            return ResponseEntity.ok(serviceDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/user/{userType}")
    public ResponseEntity<UserDto> getUserByTransactionId(@PathVariable(name = "id") long id,
                                                      @PathVariable(name = "userType") String userType) {
        try {
            final UserDto userDto = transactionService.getUserByTransactionId(id, userType);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TransactionDto transactionDto) {
        TransactionPoint transactionPoint = TransactionPoint.valueOf(transactionDto.transactionPoint());
        long userId = TransactionDto.userId();
        long masterId = TransactionDto.masterId();
        long serviceId = TransactionDto.serviceId();

        try {
            final long transactionId = transactionService.add(transactionPoint, userId, serviceId, masterId);
            final String transactionUri = String.format("/orders/%d", transactionId);

            return ResponseEntity.created(URI.create(transactionUri)).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody TransactionDto transactionDto) {
        TransactionPoint transactionPoint = TransactionPoint.valueOf(TransactionDto.transactionPoint());
        long userId = transactionDto.userId();
        long masterId = transactionDto.masterId();
        long serviceId = transactionDto.serviceId();

        try {
            transactionService.update(id, transactionPoint, userId, serviceId, masterId);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}