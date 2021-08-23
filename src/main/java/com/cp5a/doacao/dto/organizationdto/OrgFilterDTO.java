package com.cp5a.doacao.dto.organizationdto;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class OrgFilterDTO {
    private String name;
    private String city;
    private String category;
}
