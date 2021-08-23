package com.cp5a.doacao.dto.commondto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class ResponseDTO {
    String code;
    ArrayList<ErrorDTO> errors;
    private Date timestamp;

    public ResponseDTO(String code, ArrayList<ErrorDTO>  errors) {
        this.code = code;
        this.errors = errors;
        this.timestamp = new Date();
    }
}
