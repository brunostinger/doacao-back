package com.cp5a.doacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "occupationarea")
@NoArgsConstructor
public class OccupationArea {

    public OccupationArea(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "occupationAreas", cascade = CascadeType.ALL)
    private List<User> users;
}
