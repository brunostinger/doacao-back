package com.cp5a.doacao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    private Date creationDate;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    private Date scheduleDate;

    private Date duration;
    private Double value;
    private String coverImage;
    private String link;

    @ManyToMany
    @JoinTable(name ="event_has_eventcategory", joinColumns = @JoinColumn(name="event_id"), inverseJoinColumns = @JoinColumn(name="eventcategory_id"))
    private List<EventCategory> eventCategories;

    @OneToOne
    @JoinColumn(name ="EventStatus_id")
    private EventStatus eventStatus;
}
