package com.cp5a.doacao.dto.userdto;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class EditUserDTO extends UserDTO {
    @Size(max=20, message="senha inválida")
    private String password;
    @Size(max=20, message="Nova senha inválida")
    private String newPassword;
    @Size(max=20, message="Nova senha inválida")
    private String confirmPassword;
}
