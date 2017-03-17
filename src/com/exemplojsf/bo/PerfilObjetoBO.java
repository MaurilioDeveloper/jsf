package com.exemplojsf.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.exemplojsf.entity.Objeto;
import com.exemplojsf.entity.Perfil;
import com.exemplojsf.entity.PerfilObjeto;
import com.exemplojsf.exception.BancoDadosException;

public class PerfilObjetoBO extends BO<PerfilObjeto> {
	private static final long serialVersionUID = 1L;

	public PerfilObjetoBO() {
		super();
		setClazz(PerfilObjeto.class);
	}

	public List<PerfilObjeto> listByIdPerfil(Integer idPerfil) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("perfil.id", idPerfil);
			return listByFields(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<PerfilObjeto> listAll() {
		try {
			String jpql = "select po from PerfilObjeto po order by po.perfil.nome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteByIdPerfil(Integer idPerfil) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE FROM PERFIL_OBJETO WHERE ID_PERFIL = ?1 ");
			Query q = getDao().createNativeQuery(sb.toString());
			q.setParameter(1, idPerfil);
			getDao().executeQuery(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PerfilObjeto> listPerfilObjetos() {
		try {
			PerfilBO perfilBO = new PerfilBO();
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT DISTINCT PER.ID FROM PERFIL_OBJETO POB ");
			sb.append(" INNER JOIN PERFIL PER ON PER.ID = POB.ID_PERFIL ");
			Query q = getDao().createNativeQuery(sb.toString(), Perfil.class);
			List<Perfil> perfis = q.getResultList();
			List<PerfilObjeto> perfilObjetos = new ArrayList<PerfilObjeto>();
			sb = new StringBuilder();
			sb.append(" SELECT OBJ.* FROM PERFIL_OBJETO POB ");
			sb.append(" INNER JOIN OBJETO OBJ ON OBJ.ID = POB.ID_OBJETO ");
			sb.append(" WHERE POB.ID_PERFIL = ?1 ");
			sb.append(" ORDER BY OBJ.OBJETO ASC ");
			for (Perfil p : perfis) {
				try {
					Perfil perfil = perfilBO.find(p.getId());
					q = getDao().createNativeQuery(sb.toString(), Objeto.class);
					q.setParameter(1, p.getId());
					StringBuilder sb1 = new StringBuilder();
					List<Objeto> objetos = q.getResultList();
					PerfilObjeto po = new PerfilObjeto(perfil, objetos);
					perfilObjetos.add(po);
				} catch (BancoDadosException e) {
					e.printStackTrace();
				}
			}
			return perfilObjetos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
