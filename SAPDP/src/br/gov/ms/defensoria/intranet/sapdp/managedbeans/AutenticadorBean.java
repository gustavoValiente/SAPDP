package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.io.IOException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UsuarioRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ManagedBean(name = "autenticadorBean")
public class AutenticadorBean {

	private String username = "", password = "";

	HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
	HttpServletResponse response = (HttpServletResponse) FacesContext
			.getCurrentInstance().getExternalContext().getResponse();
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);

	private Usuario usuario;
	@EJB
	private UsuarioRepositorio uRep;

	public AutenticadorBean() {

	}

	public String login() {
		String message = "", retorno = "", path = FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequestContextPath();

		try {

			request.login(username, password);
			Principal principal = request.getUserPrincipal();

			if (request.isUserInRole("MASTER-ROLE")
					|| request.isUserInRole("ADMIN-ROLE"))
				retorno = path + "/admin/index.jsf";
			else if (request.isUserInRole("DEFENSOR-ROLE")
					|| request.isUserInRole("ASSESSOR-ROLE") || request.isUserInRole("ASSISTENTE-SOCIAL-ROLE"))
				retorno = path + "/defensor/index.jsf";
			else if (request.isUserInRole("ATENDENTE-ROLE") || request.isUserInRole("ESTAGIARIO-ROLE"))
				retorno = path + "/atendente/index.jsf";
			
			//Colocar usuário na sessão
			this.usuario = this.uRep.obterUsuarioPorLogin(this.username);
			session = (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			session.setAttribute("usuario", this.usuario);

			message = "Usuário : " + principal.getName() + " , bem-vindo!";
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									message, null));

			try {
				response.sendRedirect(retorno);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";

		} catch (ServletException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Falha na autenticação!", null));
			e.printStackTrace();
		}
		return "failure";
	}

	public String logout() {
		if (session != null) {
			session.invalidate();
		}
		return "/login.jsf";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
