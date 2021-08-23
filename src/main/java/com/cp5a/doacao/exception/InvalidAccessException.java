package com.cp5a.doacao.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidAccessException extends RuntimeException{
    private String field;
    private String defaultMessage;
}
