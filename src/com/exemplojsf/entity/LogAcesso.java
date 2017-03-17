package com.exemplojsf.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "log_acesso")
public class LogAcesso implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "data_acesso")
	private Date data = Calendar.getInstance().getTime();

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "objeto")
	private String objeto;

	@Column(name = "ip")
	private String ip;

	public LogAcesso() {
	}

	public LogAcesso(Usuario usuario, Date data, String descricao) {
		this.usuario = usuario;
		this.data = data;
		this.descricao = descricao;
	}

	public LogAcesso(Usuario usuario, String objeto, String ip, String descricao) {
		this.usuario = usuario;
		this.objeto = objeto;
		this.ip = ip;
		this.descricao = descricao;
	}

	public LogAcesso(Usuario usuario, String objeto, String ip) {
		this.usuario = usuario;
		this.objeto = objeto;
		this.ip = ip;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		LogAcesso other = (LogAcesso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
