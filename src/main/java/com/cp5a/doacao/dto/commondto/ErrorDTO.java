package com.cp5a.doacao.dto.commondto;
import lombok.Data;

@Data
public class ErrorDTO {
    private String field;
    private String defaultMessage;

    public ErrorDTO(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }
}
