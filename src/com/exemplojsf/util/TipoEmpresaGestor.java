package com.exemplojsf.util;

import java.util.ArrayList;
import java.util.List;

public enum TipoEmpresaGestor {
    DEALER("DEALER", "D"), FORNECEDOR("FORNECEDOR", "F");
    private String nome;

    private String valor;

    private static List<TipoEmpresaGestor> list;

    private TipoEmpresaGestor(
            String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    public static void setList(List<TipoEmpresaGestor> list) {
        TipoEmpresaGestor.list = list;
    }

    public static List<TipoEmpresaGestor> getList() {
        if (list == null) {
            list = new ArrayList<TipoEmpresaGestor>();
            for (TipoEmpresaGestor uf : values()) {
                list.add(uf);
            }
        }
        return list;
    }
}
