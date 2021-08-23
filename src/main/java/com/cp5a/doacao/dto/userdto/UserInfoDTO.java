package com.cp5a.doacao.dto.userdto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class UserInfoDTO extends UserDTO {
    @NotBlank
    @Email
    @Size(min=2, max=255)
    private String email;
    @NotBlank
    @Size(min=11, max=14)
    private String uniqueId;
}
