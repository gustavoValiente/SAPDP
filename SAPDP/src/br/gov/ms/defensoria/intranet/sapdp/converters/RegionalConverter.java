package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Regional;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.RegionalRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Item</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "regionalConverter")
public class RegionalConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return RegionalRepositorio - Instância de um sessionbean
	 */
	private RegionalRepositorio getSession() {
		try {
			return (RegionalRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/RegionalRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		RegionalRepositorio instance = getSession();
		return instance.obterRegionalPorNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}
		
		return ((Regional)value).getNome().toString();		
	}
}