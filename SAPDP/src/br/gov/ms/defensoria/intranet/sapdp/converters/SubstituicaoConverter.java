package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UsuarioRepositorio;

/**
 * 
 * @author Alain C�ceres
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Substituicao</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "substituicaoConverter", forClass=Substituicao.class)
public class SubstituicaoConverter implements Converter {
	/**
	 * Retorna uma inst�ncia com JNDI.
	 * 
	 * @return UsuarioRepositorio - Inst�ncia de um sessionbean
	 */
	private UsuarioRepositorio getSession() {
		try {
			return (UsuarioRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/UsuarioRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		UsuarioRepositorio instance = getSession();
		System.out.println(" +++++++ "+value);
		System.out.println(" +++++++ "+instance.obterUsuarioSubstituicaoPorNome(value));
		return (instance.obterUsuarioSubstituicaoPorNome(value) == null ? null : instance.obterUsuarioSubstituicaoPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Substituicao)value).getNomeDefensorSubstituto();		
		}catch(Exception e){
			return "";
		}
	}
}