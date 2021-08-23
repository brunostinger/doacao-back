package com.cp5a.doacao.dto.userdto;

import com.cp5a.doacao.model.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagedUserDTO extends EditUserDTO{
    @NotNull(message="Status inválido")
    private UserStatus userStatus;
}
