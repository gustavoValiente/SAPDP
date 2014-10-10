package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UnidadeRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Unidade</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "unidadeConverter")
public class UnidadeConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return UnidadeRepositorio - Instância de um sessionbean
	 */
	private UnidadeRepositorio getSession() {
		try {
			return (UnidadeRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/UnidadeRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		UnidadeRepositorio instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.obterUnidadePorNome(value) == null ? null : instance.obterUnidadePorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Unidade)value).getNome().toString();		
		}catch(Exception e){
			return "";
		}
	}
}