package com.cp5a.doacao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double value;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    @JoinColumn(name ="fromUser")
    private User fromUser;

    @OneToOne
    @JoinColumn(name ="TransactionType_id")
    private TransactionType transactionType;

    @OneToOne
    @JoinColumn(name ="toUser")
    private User toUser;

    @OneToOne
    @JoinColumn(name ="toEvent")
    private Event toEvent;

    private Double balance;

    private Double toUserBalance;

}
