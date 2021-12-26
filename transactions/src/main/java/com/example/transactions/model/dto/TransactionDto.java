package com.example.transactions.model.dto;

public record TransactionDto(String transactionPoint, long userId, long serviceId, long masterId) {
}