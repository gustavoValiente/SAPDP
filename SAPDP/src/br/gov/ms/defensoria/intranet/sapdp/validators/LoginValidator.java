package br.gov.ms.defensoria.intranet.sapdp.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UsuarioRepositorio;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return UsuarioRepositorio - Instância de um sessionbean
	 */
	private UsuarioRepositorio uRep;

	private UsuarioRepositorio getSession() {
		try {
			return (UsuarioRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/UsuarioRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		uRep = getSession();
		Usuario u = uRep.obterUsuarioPorLogin(value.toString());
		if(u != null){
			if (uRep.verificaDisponibilidadeDeLogin(u.getLogin())) {
				FacesMessage msg = new FacesMessage("Login inválido/indisponível.",
						"Login inválido/indisponível.");
				msg.setSeverity(FacesMessage.SEVERITY_WARN);
				throw new ValidatorException(msg);
			}
		}
	}
}
