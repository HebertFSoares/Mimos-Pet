package io.github.hebertfsoares.ms_produtos.domain.exception;

import lombok.Getter;

public class ErrorMicrosericeException extends Exception{
    @Getter
    private Integer status;
    public ErrorMicrosericeException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
