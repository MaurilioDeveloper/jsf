package com.exemplojsf.util;

import java.util.ArrayList;
import java.util.List;

public enum StatusLoader {
    CONFIGURACAO("CONFIGURAÇÃO", 'C'), IMPLANTACAO("IMPLANTAÇÃO",'I'), PRODUCAO("PRODUÇÃO",'P');
    private String nome;

    private Character valor;

    private static List<StatusLoader> list;

    private StatusLoader(
            String nome, Character valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public Character getValor() {
        return valor;
    }

    public static void setList(List<StatusLoader> list) {
        StatusLoader.list = list;
    }

    public static List<StatusLoader> getList() {
        if (list == null) {
            list = new ArrayList<StatusLoader>();
            for (StatusLoader uf : values()) {
                list.add(uf);
            }
        }
        return list;
    }
}
