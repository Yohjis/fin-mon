package com.example.transactions.service;


import com.example.transactions.model.Transaction;
import com.example.transactions.model.TransactionPoint;
import com.example.transactions.model.dto.ServiceDto;
import com.example.transactions.model.dto.UserDto;
import com.example.transactions.repostory.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class TransactionService {
    private final String serviceUrlAddress = "http://service-services:8083/services";
    private final String usersUrlAddress = "http://service-identity:8081/users";
    private final TransactionRepository transactionRepository;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getById(long id) {
        final Optional<Transaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent())
            return transaction.get();
        else
            throw new IllegalArgumentException("Invalid transaction ID");
    }

    public long add(TransactionPoint transactionPoint, long userId, long serviceId, long masterId) {
        if (checkUserType(userId, "CLIENT")) {
            if (checkUserType(masterId, "MASTER")) {
                final Transaction transaction = new Transaction(transactionPoint, userId, serviceId, masterId);
                final Transaction newTransaction = transactionRepository.save(transaction);
                return newTransaction.getId();
            } else throw new IllegalArgumentException("Invalid master!");
        }
        throw new IllegalArgumentException("Invalid client!");
    }

    public void update(long id, TransactionPoint transactionPoint, long userId, long serviceId, long masterId) {
        Optional<Transaction> oldTransaction = transactionRepository.findById(id);

        if (oldTransaction.isEmpty())
            throw new IllegalArgumentException("Invalid order ID");

        final Transaction transaction = oldTransaction.get();
        transaction.setTransactionPoint(transactionPoint);
        transaction.setServiceId(serviceId);
        transaction.setUserId(userId);
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
        transaction.setMasterId(masterId);
        transactionRepository.save(transaction);
    }

    public UserDto getUserByTransactionId(long transactionId, String userType) {
        final Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {
            if (userType.equals("client") || userType.equals("master")) {
                final long userId;

                if (userType.equals("client")) {
                    userId = transaction.get().getUserId();
                } else {
                    userId = transaction.get().getMasterId();
                }

                final RestTemplate restTemplate = new RestTemplate();
                final HttpEntity<Long> userRequest = new HttpEntity<>(userId);

                final ResponseEntity<UserDto> userResponse = restTemplate
                        .exchange(usersUrlAddress + "/" + userId + "/get",
                                HttpMethod.GET, userRequest, UserDto.class);

                if (userResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                    return userResponse.getBody();
                else
                    throw new IllegalArgumentException("User not found!");
            } else {
                throw new IllegalArgumentException("User type not found!");
            }
        } else
            throw new IllegalArgumentException("Invalid order ID");
    }

    public ServiceDto getServiceByTransactionId(long orderId) {
        final Optional<Transaction> transaction = transactionRepository.findById(orderId);

        if (transaction.isPresent()) {
            final long serviceId = transaction.get().getServiceId();
            final RestTemplate restTemplate = new RestTemplate();
            final HttpEntity<Long> serviceRequest = new HttpEntity<>(serviceId);

            final ResponseEntity<ServiceDto> serviceResponse = restTemplate
                    .exchange(serviceUrlAddress + "/" + serviceId,
                            HttpMethod.GET, serviceRequest, ServiceDto.class);

            if (serviceResponse.getStatusCode() != HttpStatus.NOT_FOUND)
                return serviceResponse.getBody();
            else
                throw new IllegalArgumentException("Service not found!");
        } else
            throw new IllegalArgumentException("Invalid order ID");
    }

    public boolean checkUserType(long id, String userType) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> userRequest = new HttpEntity<>(id);

        final ResponseEntity<UserDto> userResponse = restTemplate
                .exchange(usersUrlAddress + "/" + id + "/get",
                        HttpMethod.GET, userRequest, UserDto.class);

        if (userResponse.getStatusCode() != HttpStatus.NOT_FOUND){
            System.out.println(Objects.requireNonNull(userResponse.getBody()).userType());
            return Objects.requireNonNull(userResponse.getBody()).userType().equals(userType);
        }

        else
            throw new IllegalArgumentException("User not found!");
    }

    public void delete(long id) {
        transactionRepository.deleteById(id);
    }
}
