package com.cp5a.doacao.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transactiontype")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
}
