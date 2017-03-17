package com.exemplojsf.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.exemplojsf.entity.Empresa;
import com.exemplojsf.entity.Usuario;
import com.exemplojsf.exception.SenhaInvalidaException;
import com.exemplojsf.exception.TechnicalException;
import com.exemplojsf.exception.UsuarioBloqueadoException;
import com.exemplojsf.exception.UsuarioNaoEncontradoException;
import com.exemplojsf.util.CryptMD5;
import com.exemplojsf.util.GeradorSenha;
import com.exemplojsf.util.Gmail;
import com.exemplojsf.util.Mensagens;
import com.exemplojsf.util.Status;

public class UsuarioBO extends BO<Usuario> {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UsuarioBO.class);

	public UsuarioBO() {
		super();
		setClazz(Usuario.class);
	}

	public List<Usuario> listAtivos() throws Exception {
		List<Usuario> usuario = new ArrayList<Usuario>();
		String jpql = "select u from Usuario u where u.status != 'B' order by u.nome";
		Query query = getDao().createQuery(jpql);
		usuario = list(query);
		return usuario;
	}

	public Usuario findUsuarioByLoginOrEmail(String login, String email) {
		try {
			String jpql = "select distinct(u) from Usuario u where upper(u.login)=:login or upper(u.email)=:email";
			Query query = getDao().createQuery(jpql);
			query.setParameter("login", login.toUpperCase());
			query.setParameter("email", email);
			List<Usuario> users = list(query);
			if (users != null && users.size() > 0) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro no getUsuarioByLogin", e);
		}
		return null;
	}

	public String doSenhaPadrao(Usuario usuario, boolean enviaEmail) throws Exception {
		String senhaPadrao = GeradorSenha.gerarSenha().toUpperCase();
		System.out.println(senhaPadrao);
		usuario.setSenha(CryptMD5.encrypt(usuario.getLogin().toUpperCase(), senhaPadrao));
		enviarEmailComSenhaPadrao(usuario, senhaPadrao, enviaEmail);
		return senhaPadrao;
	}

	private void enviarEmailComSenhaPadrao(Usuario usuario, String senhaPadrao, boolean enviaEmail) throws Exception {
		if (enviaEmail) {
			String corpo = "Seu login : " + usuario.getLogin() + "\n";
			corpo += "Sua senha de acesso : " + senhaPadrao + "\n";
			log.debug(corpo);
			Gmail.getInstance(usuario.getEmail(), "Sua senha de acesso.", corpo).enviarEmail();
		}
	}

	public Usuario findByEmail(String email) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("UPPER(o.email) = '" + email.toUpperCase() + "'", BO.FILTRO_GENERICO_QUERY);
		Usuario usuarioBanco = super.findByFields(param);
		return usuarioBanco;
	}

	public List<Usuario> getAllUsuariosByEmpresa(Empresa empresa) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (empresa != null) {
			try {
				String jpql = "select u from Usuario u where u.empresa.id=:id order by u.nome";
				Query query = getDao().createQuery(jpql);
				query.setParameter("id", empresa.getId());
				usuarios = list(query);
			} catch (Exception e) {
				log.error("Erro no getAllUsuariosByEmpresa", e);
			}
		}
		return usuarios;
	}

	public List<Usuario> listByIdEmpresaAndStatus(Integer idEmpresa, String status) {
		try {
			String jpql = "select u from Usuario u where u.empresa.id =:idEmpresa and u.status =:status order by u.nome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("idEmpresa", idEmpresa);
			q.setParameter("status", status);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String resetSenha(String email) throws Exception {
		Usuario usu = findByEmail(email);
		if (usu != null) {
			try {
				String senha = GeradorSenha.gerarSenha();
				usu.setSenha(CryptMD5.encrypt(usu.getLogin().toUpperCase(), senha.toUpperCase()));
				merge(usu);
				String conteudo = Mensagens.getMensagem("recuperacao.conteudo");
				conteudo = conteudo.replace("[senha]", senha);
				Gmail.getInstance(usu.getEmail(), Mensagens.getMensagem("recuperacao.titulo"), conteudo).enviarEmail();
				return senha;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new UsuarioNaoEncontradoException();
		}
		return null;
	}

	public Usuario getUsuarioByLogin(Usuario usuario) {
		if (usuario != null) {
			try {
				String jpql = "select distinct(u) from Usuario u where u.login=:login ";
				Query query = getDao().createQuery(jpql);
				query.setParameter("login", usuario.getLogin());
				List<Usuario> users = list(query);
				if (users != null && users.size() > 0) {
					return users.get(0);
				}
			} catch (Exception e) {
				log.error("Erro no getUsuarioByLogin", e);
			}
		}
		return null;
	}

	public Usuario findByLogin(String login) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("UPPER(o.login) = '" + login.toUpperCase() + "'", BO.FILTRO_GENERICO_QUERY);
		Usuario usuarioBanco = super.findByFields(param);
		return usuarioBanco;
	}

	public Usuario login(String usuStrLogin, String usuStrSenha) throws UsuarioNaoEncontradoException,
			UsuarioBloqueadoException, SenhaInvalidaException, TechnicalException {
		Usuario usu = buscarPorLogin(usuStrLogin);
		if (usu == null)
			throw new UsuarioNaoEncontradoException();
		else if (usu.getStatus().equals(Status.STATUS_BLOQUEADO))
			throw new UsuarioBloqueadoException();
		else if (usuStrSenha == null
				|| !usu.getSenha().equals(CryptMD5.encrypt(usu.getLogin().toUpperCase(), usuStrSenha.toUpperCase())))
			throw new SenhaInvalidaException();
		return usu;
	}

	public Usuario loginCrypt(String usuStrLogin, String usuStrSenha) throws UsuarioNaoEncontradoException,
			UsuarioBloqueadoException, SenhaInvalidaException, TechnicalException {
		Usuario usu = buscarPorLogin(usuStrLogin);
		if (usu == null)
			throw new UsuarioNaoEncontradoException();
		else if (usu.getStatus().equals(Status.STATUS_BLOQUEADO))
			throw new UsuarioBloqueadoException();
		else if (usuStrSenha == null || !usu.getSenha().equals(usuStrSenha))
			throw new SenhaInvalidaException();
		return usu;
	}

	public Usuario buscarPorLogin(String usuStrLogin) throws TechnicalException, UsuarioNaoEncontradoException {
		if (usuStrLogin == null)
			throw new UsuarioNaoEncontradoException();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("login", usuStrLogin.toUpperCase());
		return super.findByFields(param);
	}
}
