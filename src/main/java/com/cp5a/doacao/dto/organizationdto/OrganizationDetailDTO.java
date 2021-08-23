package com.cp5a.doacao.dto.organizationdto;

import lombok.Data;

@Data
public class OrganizationDetailDTO extends OrganizationDTO{
    private String site;
    private String email;
    private String phone;
}
