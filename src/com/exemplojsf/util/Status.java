package com.exemplojsf.util;

import java.util.ArrayList;
import java.util.List;

public enum Status {
	BLOQUEADO("B", "BLOQUEADO"), NORMAL("N", "NORMAL");
	public static final String STATUS_BLOQUEADO = "B";

	public static final String STATUS_NORMAL = "N";

	private String chave;

	private String valor;

	private static List<Status> list;

	private Status(String chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public static List<Status> getList() {
		if (list == null) {
			list = new ArrayList<Status>();
			for (Status uf : values()) {
				list.add(uf);
			}
		}
		return list;
	}

	public String getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}
}
