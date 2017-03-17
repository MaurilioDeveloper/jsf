package com.exemplojsf.managed;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.exemplojsf.bo.LogAcessoBO;
import com.exemplojsf.bo.UsuarioBO;
import com.exemplojsf.entity.Usuario;
import com.exemplojsf.exception.SenhaInvalidaException;
import com.exemplojsf.exception.TechnicalException;
import com.exemplojsf.exception.UsuarioBloqueadoException;
import com.exemplojsf.exception.UsuarioNaoEncontradoException;
import com.exemplojsf.util.JSFHelper;
import com.exemplojsf.util.Mensagens;

@ManagedBean
@RequestScoped
public class LoginMB extends GenericManagedBean<Usuario> {
	private static Logger log = Logger.getLogger(LoginMB.class);

	private LogAcessoBO logAcessoBO;

	public LoginMB() {
		setClazz(Usuario.class);
		setUsuarioLogado(null);
		JSFHelper.getSession().invalidate();
		setBo(new UsuarioBO());
		logAcessoBO = new LogAcessoBO();
		String logoff = JSFHelper.getRequestParameterMap("logoff");
		if (logoff != null && logoff.equals("true")) {
			setUsuarioLogado(null);
			JSFHelper.redirect("login.xhtml");
		}
	}

	private void validaUsuarioLogadoDuplicado(Usuario usuarioLogin) {
		Map<String, Object> context = JSFHelper.getApplicationMap();
		if (!isUsuarioAdministrador()) {
			if (context.get("" + usuarioLogin.getId()) != null) {
				try {
					((HttpSession) context.get("" + usuarioLogin.getId())).invalidate();
					context.remove("" + usuarioLogin.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			context.put("" + usuarioLogin.getId(), JSFHelper.getSession());
		}
	}

	public String actionLogin() {
		try {
			Usuario usuarioLogando = ((UsuarioBO) getBo()).login(getEntity().getLogin(), getEntity().getSenha());
			if (usuarioLogando != null) {
				setUsuarioLogado(usuarioLogando);
				validaUsuarioLogadoDuplicado(usuarioLogando);
				setEmpresaLogada(usuarioLogando.getEmpresa());
				log.info("Usuário logando: " + usuarioLogando.getNome());
				getSessionManaged().carregarMenu();
				JSFHelper.redirect("dashboard.jsf");
			} else {
				addError(Mensagens.getMensagem(Mensagens.LOGIN_ERRO_GENERICO), "");
			}
		} catch (UsuarioNaoEncontradoException e) {
			addError(Mensagens.getMensagem("exception.usuario.nao.encontrado"), "");
			e.printStackTrace();
		} catch (UsuarioBloqueadoException e) {
			addError(Mensagens.getMensagem("exception.usuario.bloqueado"), "");
			e.printStackTrace();
		} catch (SenhaInvalidaException e) {
			addError(Mensagens.getMensagem("exception.senha.invalida"), "");
			e.printStackTrace();
		} catch (TechnicalException e) {
			addError(Mensagens.getMensagem(Mensagens.LOGIN_ERRO_GENERICO), "");
			e.printStackTrace();
		}
		return "";
	}

	public void redirectToRecuperar() {
		JSFHelper.redirect("esqueceu.jsf");
	}

	public void actionRecuperar() {
		try {
			if (getEntity().getEmail() != null && !getEntity().getEmail().equals("")) {
				String senha = ((UsuarioBO) getBo()).resetSenha(getEntity().getEmail());
				if (!senha.equals("")) {
					addInfo(Mensagens.getMensagem("login.reset.sucesso"), "");
				} else {
					addError(Mensagens.getMensagem("login.reset.nao.encontrado"), "");
				}
			} else {
				addError(Mensagens.getMensagem("login.reset.login.obrigatorio"), "");
			}
		} catch (Exception e) {
			addError(Mensagens.getMensagem("login.reset.erro"), "");
			log.error("Erro ao efetuar login.", e);
		}
	}
}
