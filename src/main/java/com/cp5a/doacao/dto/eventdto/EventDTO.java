package com.cp5a.doacao.dto.eventdto;
import lombok.Data;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class EventDTO extends BasicEventDTO{
    @Digits(integer = 9, fraction = 2, message="Valor inválido")
    private Double value;
    @Size(min=5, max=145, message="link da imagem da capa inválido")
    @NotBlank(message="link da imagem da capa inválido")
    private String coverImage;
}
