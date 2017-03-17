package com.exemplojsf.bo;

import java.util.List;

import javax.persistence.Query;

import com.exemplojsf.entity.Perfil;

public class PerfilBO extends BO<Perfil> {
	private static final long serialVersionUID = 1L;

	public PerfilBO() {
		super();
		setClazz(Perfil.class);
	}

	public List<Perfil> listAll() {
		try {
			String jpql = "select p from Perfil p order by p.nome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Perfil> listByStatus(String status) {
		try {
			String jpql = "select p from Perfil p where p.status =:status order by p.nome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("status", status);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
