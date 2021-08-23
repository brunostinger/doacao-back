package com.cp5a.doacao.repository;

import com.cp5a.doacao.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    @Query("SELECT e FROM Event e inner join Transaction t on e.id=t.toEvent.id where t.fromUser.id=:id")
    List<Event> findSubscribedEvents(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT e FROM Event e inner join Transaction t on e.id=t.toEvent.id where t.fromUser.id=:userId and e.id=:eventId")
    Event findSubscribedEvent(@Param("userId") Integer userId, @Param("eventId") Integer eventId);

    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
}
