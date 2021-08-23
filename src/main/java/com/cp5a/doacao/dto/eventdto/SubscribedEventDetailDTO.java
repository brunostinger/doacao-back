package com.cp5a.doacao.dto.eventdto;

import lombok.Data;

import java.util.Date;

@Data
public class SubscribedEventDetailDTO extends EventDetailDTO{
    private String link;
    private boolean available;
    private Date availableFrom;
}
