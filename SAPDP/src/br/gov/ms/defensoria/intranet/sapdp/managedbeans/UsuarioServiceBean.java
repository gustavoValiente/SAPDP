package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UsuarioRepositorio;

@ViewScoped
@ManagedBean
public class UsuarioServiceBean {
	@EJB
	private UsuarioRepositorio uRep;
	
	/**
	 * Retorna o usuário por login
	 * @return Usuario
	 */
	public Usuario obterUsuarioLogado(){
		String user = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
		return uRep.obterUsuarioPorLogin(user);
	}
	
	/**
	 * Retorna o usuário por nome
	 * @return Usuario
	 */
	public Usuario obterUsuarioPorNome(String nome){
		return uRep.obterUsuarioPorNome(nome);
	}
	
	/**
	 * Retorna o usuário por login
	 * @return Usuario
	 */
	public Usuario obterUsuarioPorLogin(String login){
		return uRep.obterUsuarioPorLogin(login);
	}
	
	
	/**
	 * Obter usuário da sessão
	 * @return Usuario
	 */
	public Usuario obterUsuarioDaSessao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		Usuario u = new Usuario();
		try {
			u = (Usuario) session.getAttribute("usuario");
		} catch (Exception e) {
			System.out.println("NINGUEM NA SESSAO");
		}
		return u;
	}
	
	
	/**
	 * Obter defensor que o assessor está vinculado
	 * @return
	 */
			
	public Usuario obterDefensorDoAssessor(String assessor){
		return this.uRep.obterDefensorDoAssessor(assessor);
	}
}
