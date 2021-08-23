package com.cp5a.doacao.service;

import com.cp5a.doacao.dto.eventdto.*;
import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.exception.InvalidAccessException;
import com.cp5a.doacao.model.Event;
import com.cp5a.doacao.model.EventStatus;
import com.cp5a.doacao.repository.EventRepository;
import com.cp5a.doacao.security.AuthorizationFilter;
import com.cp5a.doacao.specification.EventSpecification;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
    EventRepository eventRepository;
    EventSpecification eventSpecification;


    public Event getEventInfo(Integer id){
        Optional<Event> event = eventRepository.findById(id);
        if(!event.isPresent()) {
            throw new FieldValidationException("","Evento inválido");
        }
        return event.get();
    }

    public Event checkSubscribedEventById(Integer enventId){
        return eventRepository.findSubscribedEvent(AuthorizationFilter.getLoggedUserId(),enventId);
    }

    public EventDetailDTO getEventById(Integer id){
        Optional<Event> event = eventRepository.findById(id);
        if(!event.isPresent()) {
            throw new FieldValidationException("","Evento inválido");
        }

        EventDetailDTO eventDetailDTO = new EventDetailDTO();
        BeanUtils.copyProperties(event.get(), eventDetailDTO);
        return eventDetailDTO;
    }

    public SubscribedEventDetailDTO getEventToManage(Integer id){
        Optional<Event> event = eventRepository.findById(id);
        if(!event.isPresent()) {
            throw new FieldValidationException("","Evento inválido");
        }

        SubscribedEventDetailDTO subscribedEventDetailDTO = new SubscribedEventDetailDTO();
        BeanUtils.copyProperties(event.get(), subscribedEventDetailDTO);
        return subscribedEventDetailDTO;
    }

    public SubscribedEventDetailDTO getSubscribedEventById(Integer enventId){
        Event event = eventRepository.findSubscribedEvent(AuthorizationFilter.getLoggedUserId(),enventId);
        if(event==null)
            throw new InvalidAccessException("","Acesso inválido");

        SubscribedEventDetailDTO subscribedEventDetailDTO = new SubscribedEventDetailDTO();
        BeanUtils.copyProperties(event, subscribedEventDetailDTO);
        LocalDateTime now = LocalDateTime.now(DateTimeZone.forID("America/Sao_Paulo")).plusMinutes(15);
        if(now.isAfter(LocalDateTime.fromDateFields(subscribedEventDetailDTO.getScheduleDate())) && !StringUtils.isBlank(subscribedEventDetailDTO.getLink()))
            subscribedEventDetailDTO.setAvailable(true);
        else{
            subscribedEventDetailDTO.setAvailable(false);
            subscribedEventDetailDTO.setLink("");
        }

        subscribedEventDetailDTO.setAvailableFrom(
                LocalDateTime.fromDateFields(subscribedEventDetailDTO.getScheduleDate()).minusMinutes(15).toDate(TimeZone.getTimeZone("America/Sao_Paulo")));

        return subscribedEventDetailDTO;
    }

    public List<EventDTO> getEvents(Integer page, Integer qty, EventFilterDTO filter){
        Pageable pageable = PageRequest.of(page, qty);

        List<Event> events = eventRepository.findAll(eventSpecification.getEvents(filter), pageable).stream().collect(Collectors.toList());
        List<EventDTO> eventsDTO =  new ArrayList<>();
        events.forEach(event ->{
            EventDTO newDto =  new EventDTO();
            BeanUtils.copyProperties(event, newDto);
            eventsDTO.add(newDto);
        });

        return eventsDTO;
    }


    public List<BasicEventDTO> getEventsToManage(Integer page, Integer qty, EventFilterDTO filter){
        Pageable pageable = PageRequest.of(page, qty);

        List<Event> events = eventRepository.findAll(eventSpecification.getEventsToManage(filter), pageable).stream().collect(Collectors.toList());
        List<BasicEventDTO> eventsDTO =  new ArrayList<>();
        events.forEach(event ->{
            BasicEventDTO newDto =  new BasicEventDTO();
            BeanUtils.copyProperties(event, newDto);
            eventsDTO.add(newDto);
        });

        return eventsDTO;
    }

    public List<EventDTO> getSubscribedEvents(Integer page, Integer qty){
        Pageable pageable = PageRequest.of(page, qty);
        List<Event> events = eventRepository.findSubscribedEvents(AuthorizationFilter.getLoggedUserId(), pageable).stream().collect(Collectors.toList());
        List<EventDTO> eventsDTO =  new ArrayList<>();
        events.forEach(event ->{
            EventDTO newDto =  new EventDTO();
            BeanUtils.copyProperties(event, newDto);
            eventsDTO.add(newDto);
        });
        return eventsDTO;
    }

    public NewEventDTO newEvent(Event event){
        event =  eventRepository.save(event);
        NewEventDTO newEventDTO = new NewEventDTO();
        BeanUtils.copyProperties(event, newEventDTO);
        return newEventDTO;
    }

    public NewEventDTO updateEvent(Event newEventData){
        Event event = getEventInfo(newEventData.getId());
        if(event==null)
            throw new FieldValidationException("","Evento inválido");

        event.setCoverImage(newEventData.getCoverImage());
        event.setDescription(newEventData.getDescription());
        event.setDuration(newEventData.getDuration());
        event.setEventStatus(newEventData.getEventStatus());
        event.setLink(newEventData.getLink());
        event.setName(newEventData.getName());
        event.setScheduleDate(newEventData.getScheduleDate());
        event.setValue(newEventData.getValue());

        event =  eventRepository.save(event);
        NewEventDTO newEventDTO = new NewEventDTO();
        BeanUtils.copyProperties(event, newEventDTO);
        return newEventDTO;
    }
}
