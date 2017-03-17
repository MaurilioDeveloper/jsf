package com.exemplojsf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.exemplojsf.util.Status;
import com.exemplojsf.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "objeto")
public class Objeto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "objeto")
	private String objeto;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "status")
	private String status;

	@Column(name = "icone")
	private String icone;

	@ManyToOne
	@JoinColumn(name = "id_menu")
	private Menu menu;

	public Objeto() {
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatusStr() {
		return status != null && status.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
		Objeto other = (Objeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
