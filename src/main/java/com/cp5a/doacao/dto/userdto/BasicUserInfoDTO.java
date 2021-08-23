package com.cp5a.doacao.dto.userdto;

import com.cp5a.doacao.model.UserStatus;
import com.cp5a.doacao.model.UserType;
import lombok.Data;
@Data
public class BasicUserInfoDTO {
    private Integer id;
    private String name;
    private UserStatus userStatus;
    private UserType userType;
}
