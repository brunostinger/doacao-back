package com.cp5a.doacao.controller;

import com.cp5a.doacao.dto.eventdto.*;
import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.model.Event;
import com.cp5a.doacao.service.EventService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Api(tags = {"Endpoint '/event'"})
public class EventController {

    EventService eventService;
    @ApiOperation(value = "Cria um novo evento - Disponível para admin")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEventDTO createEvent(@RequestBody @Validated NewEventDTO newEventDTO){
        Event event = new Event();
        BeanUtils.copyProperties(newEventDTO, event);
        return eventService.newEvent(event);
    }

    @ApiOperation(value = "Retorna as informações do evento através do ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDetailDTO getEventById(@PathVariable Integer id){
        return eventService.getEventById(id);
    }

    @ApiOperation(value = "Retorna os dados do evento para ser gerenciado - Disponível para admin")
    @GetMapping("/manage/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubscribedEventDetailDTO getEventToManage(@PathVariable Integer id){
        return eventService.getEventToManage(id);
    }

    @ApiOperation(value = "Retorna uma lista contendo os eventos cadastrados e ativos")
    @GetMapping("/list/{page}/{qty}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getEvents(@PathVariable Integer page, @PathVariable Integer qty,
                                    @RequestParam  String filter){
        EventFilterDTO filterDTO = new EventFilterDTO();
        Gson gson = new Gson();
        try{
            filterDTO = gson.fromJson(filter, EventFilterDTO.class);
        }catch (JsonSyntaxException e){
            throw new FieldValidationException("","Filtro inválido");
        }

        return eventService.getEvents(page, qty, filterDTO);
    }

    @ApiOperation(value = "Retorna uma lista contendo os eventos cadastrados - Disponível para admin")
    @GetMapping("manage/list/{page}/{qty}")
    @ResponseStatus(HttpStatus.OK)
    public List<BasicEventDTO> getEventsToManage(@PathVariable Integer page, @PathVariable Integer qty,
                                                 @ApiParam(name = "filter",
                                                         value = "{\"name\":\"\",\"date\":\"\",\"status\":\"\"} \n " +
                                                                 "(status: 0 = Inativo status: 1 = Ativo)", defaultValue = "")
                                    @RequestParam  String filter){
        EventFilterDTO filterDTO = new EventFilterDTO();
        Gson gson = new Gson();
        try{
            filterDTO = gson.fromJson(filter, EventFilterDTO.class);
        }catch (JsonSyntaxException e){
            throw new FieldValidationException("","Filtro inválido");
        }
        return eventService.getEventsToManage(page, qty, filterDTO);
    }

    @ApiOperation(value = "Retorna os dados para assistir o evento comprado")
    @GetMapping("/{id}/play")
    @ResponseStatus(HttpStatus.OK)
    public SubscribedEventDetailDTO getSubscribedEventBy(@PathVariable Integer id){
        return eventService.getSubscribedEventById(id);
    }

    @ApiOperation(value = "Retorna uma lista contendo os eventos comprados")
    @GetMapping("/list/subscribed/{page}/{qty}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getSubscribedEvents(@PathVariable Integer page, @PathVariable Integer qty){
        return eventService.getSubscribedEvents(page, qty);
    }

    @ApiOperation(value = "Atualiza um evento - Disponível para admin")
    @PatchMapping("/manage/update")
    @ResponseStatus(HttpStatus.OK)
    public NewEventDTO updateEvent(@RequestBody @Validated NewEventDTO newEventDTO){
        Event event = new Event();
        BeanUtils.copyProperties(newEventDTO, event);
        return eventService.updateEvent(event);
    }
}
