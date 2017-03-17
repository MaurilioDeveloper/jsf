package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;


public class SenhaInvalidaException extends BusinessException{
    
    private String mensagem = Mensagens.getMensagem("exception.senha.invalida");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
