package com.cp5a.doacao.dto.userdto;
import com.cp5a.doacao.model.OccupationArea;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UserDTO extends BasicUserInfoDTO{
    @Size(min=2, max=145, message="Nome inválido")
    @NotBlank(message="Nome inválido")
    private String name;
    @NotBlank(message="Telefone inválido")
    @Size(min=10, max=11, message="Telefone inválido")
    private String phone;
    @Size(max=255, message="Site inválido")
    private String site;
    @Size(min=8, max=8, message="CEP inválido")
    private String zipCode;
    @Size(min=2, max=45, message="Endereço inválido")
    private String address;
    @Size(max=45, message="Número inválido")
    private String addressNumber;
    @Size(max=45, message="Complemento inválido")
    private String addressComplement;
    @Size(min=2, max=45, message="Bairro inválido")
    @NotBlank( message="Bairro inválido")
    private String district;
    @Size(min=2, max=45, message="Cidade inválida")
    @NotBlank(message="Cidade inválida")
    private String city;
    @Size(min=2, max=2, message="Estado inválido")
    @NotBlank(message="Estado inválido")
    private String state;
    @Size(min=0, max=1000, message="Bio inválida")
    private String bio;
    private List<OccupationArea> occupationAreas;
    private String profileImage;
}
