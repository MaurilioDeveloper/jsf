package com.exemplojsf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.exemplojsf.util.Status;
import com.exemplojsf.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "menu")
public class Menu implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "icone")
	private String icone;

	@Column(name = "status")
	private String status;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIcone() {
		return icone;
	}

	public String getIconeImagem() {
		return Utils.getIconeImagem(icone);
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusStr() {
		return status != null && status.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
