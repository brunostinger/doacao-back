package com.cp5a.doacao.dto.userdto;
import com.cp5a.doacao.model.OccupationArea;
import com.cp5a.doacao.model.UserType;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RegisterUserDTO extends UserDTO {

    @NotBlank
    @Size(min=8, max=20, message="Senha inválida")
    private String password;
    @NotBlank
    @Email
    @Size(min=2, max=255, message="Email inválido")
    private String email;
    @NotBlank
    @Size(min=11, max=14, message="CPF/CNPJ inválido")
    private String uniqueId;
    @NotNull(message="Tipo de usuário inválido")
    private UserType userType;
}
