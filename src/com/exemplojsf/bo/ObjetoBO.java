package com.exemplojsf.bo;

import java.util.List;

import javax.persistence.Query;

import com.exemplojsf.entity.Objeto;

public class ObjetoBO extends BO<Objeto> {
	private static final long serialVersionUID = 1L;

	public ObjetoBO() {
		super();
		setClazz(Objeto.class);
	}

	public List<Objeto> listAll() {
		try {
			String jpql = "select o from Objeto o order by o.objeto asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Objeto> listByStatus(String status) {
		try {
			String jpql = "select o from Objeto o where o.status =:status order by o.objeto asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("status", status);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
