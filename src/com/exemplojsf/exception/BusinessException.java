package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;

public class BusinessException extends Exception{

    private String mensagem = Mensagens.getMensagem("exception.negocio");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public BusinessException(Throwable cause, String mensagem) {
        super(cause);
        this.mensagem = mensagem;
    }
    
    public BusinessException(String mensagem) {
        super();
        this.mensagem = mensagem;
    }
    
    public BusinessException() {
        super();
    }
}
