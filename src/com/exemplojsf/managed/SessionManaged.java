package com.exemplojsf.managed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.exemplojsf.bo.EmpresaBO;
import com.exemplojsf.bo.MenuBO;
import com.exemplojsf.bo.ObjetoBO;
import com.exemplojsf.bo.PerfilBO;
import com.exemplojsf.bo.PerfilObjetoBO;
import com.exemplojsf.entity.Empresa;
import com.exemplojsf.entity.Menu;
import com.exemplojsf.entity.Objeto;
import com.exemplojsf.entity.PerfilObjeto;
import com.exemplojsf.entity.Usuario;
import com.exemplojsf.exception.BancoDadosException;
import com.exemplojsf.util.JSFHelper;
import com.exemplojsf.util.Status;

@ManagedBean
@SessionScoped
public class SessionManaged extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado, usuarioSelecionado;

	private Empresa empresaLogada, empresaSelecionada;

	private static Logger log = Logger.getLogger(SessionManaged.class);

	private MenuModel menu = new DefaultMenuModel();

	private List<Usuario> usuarios;

	private List<Empresa> empresas;

	private List<Objeto> objetos;

	private EmpresaBO empresaBO;

	private PerfilBO perfilBO;

	private ObjetoBO objetoBO;

	private PerfilObjetoBO perfilObjetoBO;

	private MenuBO menuBO;

	public SessionManaged() {
		setClazz(Usuario.class);
		empresaBO = new EmpresaBO();
		perfilBO = new PerfilBO();
		objetoBO = new ObjetoBO();
		perfilObjetoBO = new PerfilObjetoBO();
		menuBO = new MenuBO();
		carregarEmpresas();
	}

	public void carregarMenu() {
		try {
			if (usuarioLogado != null) {
				objetos = new ArrayList<>();
				List<PerfilObjeto> listPerfilObjetos = perfilObjetoBO.listByIdPerfil(usuarioLogado.getPerfil().getId());
				if (listPerfilObjetos != null && !listPerfilObjetos.isEmpty()) {
					menu = new DefaultMenuModel();
					Set<Menu> menusSet = new HashSet<>();
					listPerfilObjetos.forEach(po -> {
						if (po.getObjeto() != null && po.getObjeto().getMenu() != null) {
							menusSet.add(po.getObjeto().getMenu());
						}
					});
					listPerfilObjetos
							.sort((o1, o2) -> o1.getObjeto().getObjeto().compareTo(o2.getObjeto().getObjeto()));
					List<Menu> menus = new ArrayList<>(menusSet);
					menus.sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));
					menus.forEach(m -> {
						DefaultSubMenu menuMod = new DefaultSubMenu(m.getNome());
						if (m.getIcone() != null && !m.getIcone().isEmpty()) {
							menuMod.setIcon(m.getIcone());
						}
						listPerfilObjetos.forEach(obj -> {
							if (obj != null && obj.getObjeto() != null && obj.getObjeto().getMenu().getId() == m.getId()
									&& obj.getObjeto().getDescricao() != null
									&& !obj.getObjeto().getDescricao().isEmpty() && obj.getObjeto().getObjeto() != null
									&& !obj.getObjeto().getObjeto().isEmpty()) {
								objetos.add(obj.getObjeto());
								addMenuItem(menuMod, obj.getObjeto());
							}
						});
						if (menuMod.getElementsCount() > 0)
							menu.addElement(menuMod);
					});
				} else {
					JSFHelper.redirect("login.jsf");
				}
				DefaultSubMenu miSenha = new DefaultSubMenu("Configurações");
				miSenha.setIcon("fa fa-cog");
				DefaultMenuItem itemSenha = new DefaultMenuItem("Trocar Senha");
				itemSenha.setIcon("fa fa-key");
				itemSenha.setUrl("troca.jsf");
				itemSenha.setId("troca");
				miSenha.addElement(itemSenha);
				DefaultMenuItem miCac = new DefaultMenuItem();
				miCac.setValue("Atendimento");
				miCac.setHref("index.jsp");
				miCac.setTarget("_blank");
				miCac.setIcon("fa fa-user");
				DefaultMenuItem miAtualizacoes = new DefaultMenuItem();
				miAtualizacoes.setUrl("sobre.jsf");
				miAtualizacoes.setValue("Atualizações");
				miAtualizacoes.setIcon("fa fa-info-circle");
				DefaultMenuItem miLogoff = new DefaultMenuItem();
				miLogoff.setUrl("login.jsf");
				miLogoff.setValue("Sair");
				miLogoff.setIcon("fa fa-power-off");
				menu.addElement(miSenha);
				menu.addElement(miAtualizacoes);
				menu.addElement(miCac);
				menu.addElement(miLogoff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isUsuarioAdministrador() {
		if (getUsuarioLogado().getPerfil().getTipo().equals("A")) {
			return true;
		} else {
			return false;
		}
	}

	private void addMenuItem(DefaultSubMenu menuMod, Objeto obj) {
		DefaultMenuItem itemMod = new DefaultMenuItem(obj.getDescricao());
		itemMod.setUrl(obj.getObjeto());
		itemMod.setEscape(false);
		itemMod.setId(obj.getObjeto());
		menuMod.addElement(itemMod);
		if (obj.getIcone() != null && !obj.getIcone().isEmpty()) {
			itemMod.setIcon(obj.getIcone());
		}
	}

	public void carregarEmpresas() {
		try {
			empresas = empresaBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		setUsuarioSelecionado(usuarioLogado);
		if (usuarioLogado != null) {
			setEmpresaLogada(usuarioLogado.getEmpresa());
		}
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Empresa> getEmpresas() {
		if (empresas == null) {
			carregarEmpresas();
		}
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresaLogada() {
		return empresaLogada;
	}

	public void setEmpresaLogada(Empresa empresaLogada) {
		this.empresaLogada = empresaLogada;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}
}
