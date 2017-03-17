package com.exemplojsf.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.exemplojsf.util.Status;

@SuppressWarnings("serial")
@Entity
@Table(name = "perfil_objeto")
public class PerfilObjeto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "id_objeto")
	private Objeto objeto;

	@Transient
	private List<Objeto> objetos;

	public PerfilObjeto() {
	}

	public PerfilObjeto(Perfil perfil, List<Objeto> objetos) {
		this.perfil = perfil;
		this.objetos = objetos;
	}

	public PerfilObjeto(Perfil perfil, Objeto objeto) {
		this.perfil = perfil;
		this.objeto = objeto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

}
