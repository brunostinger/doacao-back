package com.cp5a.doacao.dto.eventdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EventDetailDTO extends EventDTO{
    @JsonFormat(pattern="HH:mm")
    @NotNull(message="Duração inválida")
    private Date duration;
    @Size(min=20, max=1000, message="Descrição inválido")
    @NotBlank(message="Descrição inválida")
    private String description;
}
