package com.example.transactions.repostory;

import com.example.transactions.model.Card;
import com.example.transactions.model.Transaction;
import com.example.transactions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
