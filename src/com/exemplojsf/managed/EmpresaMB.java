package com.exemplojsf.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.EmpresaBO;
import com.exemplojsf.entity.Empresa;
import com.exemplojsf.util.Mensagens;
import com.exemplojsf.util.Status;
import com.exemplojsf.util.UF;

@ManagedBean
@ViewScoped
public class EmpresaMB extends GenericManagedBean<Empresa> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status, ufs;

	public EmpresaMB() {
		setBo(new EmpresaBO());
		setClazz(Empresa.class);
		carregarEntityList();
		carregarStatus();
		carregarUfs();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	public void carregarUfs() {
		ufs = new ArrayList<>();
		UF.getList().forEach(u -> ufs.add(new SelectItem(u.getChave(), u.getValor())));
	}

	private void carregarEntityList() {
		try {
			setEntityList(((EmpresaBO) getBo()).listAll());
		} catch (Exception e) {
			addError("Erro ao carregar Objetos.", "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			((EmpresaBO) getBo()).persist(getEntity());
			carregarEntityList();
			carregarEmpresas();
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
			((EmpresaBO) getBo()).remove(getEntity());
			carregarEntityList();
			carregarEmpresas();
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public List<SelectItem> getStatus() {
		return status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}

	public List<SelectItem> getUfs() {
		return ufs;
	}

	public void setUfs(List<SelectItem> ufs) {
		this.ufs = ufs;
	}
}
