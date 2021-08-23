package com.cp5a.doacao.repository;

import com.cp5a.doacao.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByFromUserIdOrderByIdDesc(Integer id, Pageable pageable);
    List<Transaction> findByToUserIdOrderByIdDesc(Integer id, Pageable pageable);
}
