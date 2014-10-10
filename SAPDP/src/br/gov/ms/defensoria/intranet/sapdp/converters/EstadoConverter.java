package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Estado;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.EstadoRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Item</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "estadoConverter")
public class EstadoConverter implements Converter {
	/**
	 * Retorna uma inst�ncia com JNDI.
	 * 
	 * @return EstadoRepositorio - Inst�ncia de um sessionbean
	 */
	private EstadoRepositorio getSession() {
		try {
			return (EstadoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/EstadoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		EstadoRepositorio instance = getSession();
		return instance.obterEstadoPorNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}
		
		return ((Estado)value).getNome().toString();		
	}
}