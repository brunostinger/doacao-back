package com.cp5a.doacao.dto.eventdto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventFilterDTO {
    private String name;
    private String date;
    private String price;
    private String status;
}
