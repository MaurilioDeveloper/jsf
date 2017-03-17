package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;

@SuppressWarnings("serial")
public class UsuarioLoginExistenteException extends BusinessException {
    public UsuarioLoginExistenteException() {
        super(Mensagens.getMensagem("exception.usuario.login.existente"));
    }

    public UsuarioLoginExistenteException(
            Throwable cause) {
        super(cause, Mensagens.getMensagem("exception.usuario.login.existente"));
    }

    public UsuarioLoginExistenteException(
            Throwable cause, String mensagem) {
        super(cause, mensagem);
    }
}
