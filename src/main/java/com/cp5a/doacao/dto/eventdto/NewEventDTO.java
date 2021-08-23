package com.cp5a.doacao.dto.eventdto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewEventDTO extends EventDetailDTO {
    @Size(min=5, max=145, message="Link inválido")
    @NotBlank(message="Link inválido")
    private String link;
}
