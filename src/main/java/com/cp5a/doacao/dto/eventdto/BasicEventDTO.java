package com.cp5a.doacao.dto.eventdto;
import com.cp5a.doacao.model.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class BasicEventDTO {
    private Integer id;
    @Size(min=2, max=145, message="Nome inválido")
    @NotBlank(message="Nome inválido")
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @NotNull(message="Data inválida")
    private Date scheduleDate;
    private EventStatus eventStatus;
}
