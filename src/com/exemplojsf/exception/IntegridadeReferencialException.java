package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;

public class IntegridadeReferencialException extends BusinessException {
    
    public IntegridadeReferencialException(){
        super(Mensagens.getMensagem("exception.integridade.violada"));
    }
    
    public IntegridadeReferencialException(Throwable cause){
        super(cause, Mensagens.getMensagem("tj.exception.integridade.violada"));
    }
    
}
