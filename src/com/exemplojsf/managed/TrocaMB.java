package com.exemplojsf.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.UsuarioBO;
import com.exemplojsf.entity.Usuario;
import com.exemplojsf.util.CryptMD5;
import com.exemplojsf.util.JSFHelper;
import com.exemplojsf.util.Mensagens;

@ManagedBean
@ViewScoped
public class TrocaMB extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private String senhaAntiga, senhaNova;

	public TrocaMB() {
		setBo(new UsuarioBO());
		setClazz(Usuario.class);
		setEntity(getUsuarioSelecionado());
		if (getUsuarioLogado() == null) {
			JSFHelper.redirect("login.jsf");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			if (getEntity().getSenha().equals(CryptMD5.encrypt(getEntity().getLogin(), senhaAntiga.toUpperCase()))) {
				getEntity().setSenha(CryptMD5.encrypt(getEntity().getLogin(), senhaNova.toUpperCase()));
				((UsuarioBO) getBo()).merge(getEntity());
				addInfo(Mensagens.getMensagem(Mensagens.SENHA_ALTERADA_COM_SUCESSO), "");
			} else {
				addError(Mensagens.getMensagem(Mensagens.SENHA_ATUAL_INVALIDA), "");
			}
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
			e.printStackTrace();
		}
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
}
