package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;

public class RegistroExistenteException extends BusinessException {
    
    public RegistroExistenteException(){
        super(Mensagens.getMensagem("exception.registro.existente"));
    }
    
    public RegistroExistenteException(Throwable cause){
        super(cause, Mensagens.getMensagem("tj.exception.registro.existente"));
    }
    
    public RegistroExistenteException(Throwable cause, String mensagem){
        super(cause, mensagem);
    }
    
}
