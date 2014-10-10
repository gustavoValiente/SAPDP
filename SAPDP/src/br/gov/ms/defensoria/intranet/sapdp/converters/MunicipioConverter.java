package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.MunicipioDistritoRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Item</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "municipioConverter")
public class MunicipioConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return MunicipioDistritoRepositorio - Instância de um sessionbean
	 */
	private MunicipioDistritoRepositorio getSession() {
		try {
			return (MunicipioDistritoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/MunicipioDistritoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		MunicipioDistritoRepositorio instance = getSession();
		return instance.buscarMunicipioDistritoPorNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}
		
		return ((MunicipioDistrito)value).getNome().toString();		
	}
}