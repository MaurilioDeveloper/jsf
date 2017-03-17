package com.exemplojsf.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.EmpresaBO;
import com.exemplojsf.bo.PerfilBO;
import com.exemplojsf.bo.UsuarioBO;
import com.exemplojsf.entity.Perfil;
import com.exemplojsf.entity.Usuario;
import com.exemplojsf.util.CryptMD5;
import com.exemplojsf.util.GeradorSenha;
import com.exemplojsf.util.Gmail;
import com.exemplojsf.util.Mensagens;
import com.exemplojsf.util.Status;

@ManagedBean
@SessionScoped
public class UsuarioMB extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status;

	private List<Perfil> perfis;

	private PerfilBO perfilBO;

	private boolean editar;

	private EmpresaBO empresaBO;

	public UsuarioMB() {
		setBo(new UsuarioBO());
		setClazz(Usuario.class);
		perfilBO = new PerfilBO();
		empresaBO = new EmpresaBO();
		carregarStatus();
		carregarPerfis();
		carregarEntityList();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	public void clickButtonEditar() {
		editar = true;
	}

	public void carregarPerfis() {
		try {
			perfis = perfilBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public void carregarEntityList() {
		try {
			setEntityList(((UsuarioBO) getBo()).listByIdEmpresaAndStatus(getEmpresaSelecionada().getId(),
					Status.STATUS_NORMAL));
			carregarPerfis();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			if (((UsuarioBO) getBo()).findUsuarioByLoginOrEmail(getEntity().getLogin(),
					getEntity().getEmail()) != null) {
				addError(Mensagens.getMensagem(Mensagens.USUARIO_DUPLICADO), "");
				return;
			}
			String senha = GeradorSenha.gerarSenha();
			getEntity().setSenha(CryptMD5.encrypt(getEntity().getLogin().toUpperCase(), senha.toUpperCase()));
			getEntity().setLogin(getEntity().getLogin().toUpperCase());
			getEntity().setEmpresa(getEmpresaSelecionada());
			((UsuarioBO) getBo()).persist(getEntity());
			String conteudo = Mensagens.getMensagem("novo.usuario.senha.conteudo");
			conteudo = conteudo.replace("[login]", getEntity().getLogin());
			conteudo = conteudo.replace("[senha]", senha);
			Gmail.getInstance(getEntity().getEmail(), Mensagens.getMensagem("novo.usuario.senha.titulo"), conteudo)
					.enviarEmail();
			if (getUsuarioSelecionado().getId() == getEntity().getId()) {
				setUsuarioSelecionado(getEntity());
			}
			if (getUsuarioLogado().getId() == getEntity().getId()) {
				setUsuarioLogado(getEntity());
			}
			actionNew(event);
			carregarEntityList();
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			e.printStackTrace();
		}
	}

	@Override
	public void actionRemove(ActionEvent event) {
		try {
			((UsuarioBO) getBo()).remove(getEntity());
			carregarEntityList();
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(null);
	}

	public List<SelectItem> getStatus() {
		return status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}
