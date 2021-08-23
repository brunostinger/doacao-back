package com.cp5a.doacao.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eventstatus")
public class EventStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
}
