package com.cp5a.doacao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uniqueId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String addressNumber;
    private String addressComplement;
    private String district;
    private String city;
    private String state;
    private String zipCode;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    private Date lastLogin;
    private String bio;
    private String site;
    private String profileImage;

    @OneToOne
    @JoinColumn(name ="UserType_id")
    private UserType userType;

    @OneToOne
    @JoinColumn(name ="UserStatus_id")
    private UserStatus userStatus;

    @ManyToMany()
    @JoinTable(name ="user_has_occupationarea", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="occupationarea_id"))
    private List<OccupationArea> occupationAreas;

    private Double balance;
}
