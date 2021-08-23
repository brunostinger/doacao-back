package com.cp5a.doacao.dto.organizationdto;

import lombok.Data;

@Data
public class OrganizationDTO extends OrgLocationDTO{
    private Integer id;
    private String name;
    private String bio;
    private String profileImage;
}
