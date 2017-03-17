package com.exemplojsf.bo;

import java.util.List;

import javax.persistence.Query;

import com.exemplojsf.entity.Menu;

public class MenuBO extends BO<Menu> {

	private static final long serialVersionUID = 1L;

	public MenuBO() {
		super();
		setClazz(Menu.class);
	}

	public List<Menu> listAll() {
		try {
			String jpql = "select m from Menu m order by m.nome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Menu> listByStatus(String status) {
		try {
			String jpql = "select m from Menu m where m.status =:status order by m.nome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("status", status);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
