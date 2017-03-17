package com.exemplojsf.exception;

import com.exemplojsf.util.Mensagens;


public class UsuarioNaoEncontradoException extends BusinessException {
    
    private static String mensagem = Mensagens.getMensagem("exception.usuario.nao.encontrado");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
