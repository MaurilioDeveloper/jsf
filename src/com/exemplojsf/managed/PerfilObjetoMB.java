package com.exemplojsf.managed;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.ObjetoBO;
import com.exemplojsf.bo.PerfilBO;
import com.exemplojsf.bo.PerfilObjetoBO;
import com.exemplojsf.entity.Objeto;
import com.exemplojsf.entity.Perfil;
import com.exemplojsf.entity.PerfilObjeto;
import com.exemplojsf.util.Mensagens;
import com.exemplojsf.util.Status;

@ManagedBean
@ViewScoped
public class PerfilObjetoMB extends GenericManagedBean<PerfilObjeto> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<Perfil> perfis;

	private List<Objeto> objetos, objetosSelecionados;

	private Perfil perfilSelecionado;

	private String statusSelecionado;

	private PerfilBO perfilBO;

	private ObjetoBO objetoBO;

	public PerfilObjetoMB() {
		setBo(new PerfilObjetoBO());
		setClazz(PerfilObjeto.class);
		objetoBO = new ObjetoBO();
		perfilBO = new PerfilBO();
		carregarEntityList();
		carregarObjetos();
		carregarPerfis();
	}

	private void carregarEntityList() {
		try {
			setEntityList(((PerfilObjetoBO) getBo()).listPerfilObjetos());
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarObjetos() {
		try {
			perfis = perfilBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void setEntity(PerfilObjeto entity) {
		objetosSelecionados = entity.getObjetos();
		perfilSelecionado = entity.getPerfil();
		super.setEntity(entity);
	}

	public void carregarPerfis() {
		try {
			objetos = objetoBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			((PerfilObjetoBO) getBo()).deleteByIdPerfil(perfilSelecionado.getId());
			List<PerfilObjeto> pos = new ArrayList<>();
			objetosSelecionados.forEach(o -> {
				pos.add(new PerfilObjeto(perfilSelecionado, o));
			});
			((PerfilObjetoBO) getBo()).persistBatch(pos);
			carregarEntityList();
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
			e.printStackTrace();
		}
	}

	@Override
	public void actionRemove(ActionEvent event) {
		try {
			((PerfilObjetoBO) getBo()).deleteByIdPerfil(perfilSelecionado.getId());
			carregarEntityList();
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

	public List<Objeto> getObjetosSelecionados() {
		return objetosSelecionados;
	}

	public void setObjetosSelecionados(List<Objeto> objetosSelecionados) {
		this.objetosSelecionados = objetosSelecionados;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public String getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(String statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}
}
