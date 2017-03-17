package com.exemplojsf.bo;

import java.util.List;

import javax.persistence.Query;

import com.exemplojsf.entity.Empresa;

public class EmpresaBO extends BO<Empresa> {
	private static final long serialVersionUID = 1L;

	public EmpresaBO() {
		super();
		setClazz(Empresa.class);
	}

	public List<Empresa> listAll() {
		try {
			String jpql = "select e from Empresa e order by e.nome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
