package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.OrgaoExpedidor;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 *          Converter de <code>Orgao Expedidor</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "orgaoExpedidorConverter")
public class OrgaoExpedidorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return OrgaoExpedidor.valueOf(value.toUpperCase());
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