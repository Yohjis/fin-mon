package com.example.transactions.model;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
public final class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private TransactionPoint transactionPoint;
    @NotNull
    private long userId;
    @NotNull
    private long serviceId;
    private Timestamp timestamp;
    @NotNull
    private long masterId;

    public Transaction() {

    }

    public Transaction(TransactionPoint transactionPoint, long userId, long serviceId, long masterId) {
        this.transactionPoint = transactionPoint;
        this.userId = userId;
        this.serviceId = serviceId;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.masterId = masterId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionPoint getTransactionPoint() {
        return transactionPoint;
    }

    public void setTransactionPoint(TransactionPoint transactionPoint) {
        this.transactionPoint = transactionPoint;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }
}