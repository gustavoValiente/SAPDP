package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.EstadoCivil;

/**
 * 
 * @author Alain Gabriel Cáceres Álvarez
 * @since JDK7
 * @version 1.0
 * 
 *          Converter de <code>Estado Civil</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "estadoCivilConverter")
public class EstadoCivilConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return EstadoCivil.valueOf(value.toUpperCase());
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}

		return value.toString();
	}
}