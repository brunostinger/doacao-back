package com.cp5a.doacao.dto.transactiondto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DonationDTO {
    @NotBlank(message = "Número do cartão não foi informado")
    @Digits(integer = 16, fraction = 0, message="Número do cartão inválido")
    private String cardNumber;
    @NotBlank(message = "CPF/CNPJ não foi informado")
    @Size(min=11, max=14, message="CPF/CNPJ inválido")
    private String uniqueId;
    @NotBlank(message = "Data de validade não foi informada")
    @Size(min=4, max=4, message="Data de validade inválida")
    private String expiration;
    @NotBlank(message = "Nome do titular não foi informado")
    @Size(min=2, max=145, message="Nome do titular inválido")
    private String owner;
    @NotBlank(message = "CVV não foi informado")
    @Digits(integer = 3, fraction = 0, message="CVV inválido")
    private String securityCode;
    @NotNull(message = "Instituição não foi informada")
    @Digits(integer = 9, fraction = 0, message="Instituição inválida")
    private Integer toUser;
    @NotNull(message = "Valor da doação não foi informado")
    @Digits(integer = 9, fraction = 2, message="Valor da doação inválido")
    private Double value;
}
