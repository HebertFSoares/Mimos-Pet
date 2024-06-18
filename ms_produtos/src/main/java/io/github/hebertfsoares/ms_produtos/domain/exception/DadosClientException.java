package io.github.hebertfsoares.ms_produtos.domain.exception;

public class DadosClientException extends Exception{
    public DadosClientException() {
        super("Dados do cliente não encontrado!");
    }
}
