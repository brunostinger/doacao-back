package com.cp5a.doacao.dto.userdto;
import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
public class LoggedUserDTO {
    @NotBlank
    private String sub;
    @NotBlank
    private String role;
    @NotBlank
    private Integer id;
    @NotBlank
    private Long exp;
}
