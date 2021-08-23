package com.cp5a.doacao.specification;
import com.cp5a.doacao.dto.eventdto.EventFilterDTO;
import com.cp5a.doacao.model.Event;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

@Component
public class EventSpecification {
    public Specification<Event> getEvents(EventFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("eventStatus").get("id"),1));

            LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("America/Sao_Paulo"));

            predicates.add(criteriaBuilder.greaterThan(root.get("scheduleDate"),
                    now.toDate()
            ));

            System.out.println(now.toDate());
            System.out.println(Instant.now());
            System.out.println(LocalDateTime.now(DateTimeZone.UTC));


            if (!StringUtils.isBlank(filter.getName())){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }

            if (!StringUtils.isBlank(filter.getPrice())){
                if(filter.getPrice().equals("desc"))
                    query.orderBy(criteriaBuilder.desc(root.get("value")));
                else
                    query.orderBy(criteriaBuilder.asc(root.get("value")));
            }else{
                query.orderBy(criteriaBuilder.asc(root.get("scheduleDate")));
            }

            if(!StringUtils.isBlank(filter.getDate())){

                if(filter.getDate().equals("today")){
                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                                    now.toDate(), now.millisOfDay().withMaximumValue().toDate()
                    ));
                }else if(filter.getDate().equals("week")){
                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                            now.toDate(), now.dayOfWeek().withMaximumValue().millisOfDay().withMaximumValue().toDate()
                    ));
                }else if(filter.getDate().equals("month")){

                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                            now.toDate(),
                            now.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate()
                    ));
                }
            }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Event> getEventsToManage(EventFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("America/Sao_Paulo"));

            if (!StringUtils.isBlank(filter.getName())){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }

            if(!StringUtils.isBlank(filter.getDate())){
                if(filter.getDate().equals("today")){
                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                            now.millisOfDay().withMinimumValue().toDate(),
                            now.millisOfDay().withMaximumValue().toDate()
                    ));
                }else if(filter.getDate().equals("week")){
                    DateTime weekEnd = now.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1).toDateTime();

                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                            now.dayOfWeek().withMinimumValue().millisOfDay().withMinimumValue().toDate(),
                            now.dayOfWeek().withMaximumValue().millisOfDay().withMaximumValue().toDate()
                    ));
                }else if(filter.getDate().equals("month")){
                    LocalDateTime endOfMonth = now.dayOfMonth().withMaximumValue();

                    predicates.add(criteriaBuilder.between(root.get("scheduleDate"),
                            now.dayOfMonth().withMinimumValue().millisOfDay().withMinimumValue().toDate(),
                            now.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate()

                    ));
                }
            }

            if (!StringUtils.isBlank(filter.getStatus())){
                predicates.add(criteriaBuilder.equal(root.get("eventStatus").get("id"),filter.getStatus()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}