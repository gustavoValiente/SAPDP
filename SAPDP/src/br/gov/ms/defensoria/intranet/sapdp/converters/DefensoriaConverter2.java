package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Defensoria;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.DefensoriaRepositorio;

/**
 * 
 * @author Alain Gabriel C�ceres �lvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Defensoria</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "defensoriaConverter2")
public class DefensoriaConverter2 implements Converter {
	/**
	 * Retorna uma inst�ncia com JNDI.
	 * 
	 * @return DefensoriaRepositorio - Inst�ncia de um sessionbean
	 */
	private DefensoriaRepositorio getSession() {
		try {
			return (DefensoriaRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/DefensoriaRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		DefensoriaRepositorio instance = getSession();
		System.out.println("<>>>>>>>>>>>>>>>>>>>>>>"+value);
		if(value == null)
			return "";
		return (instance.find(Long.parseLong(value)) == null ? null : instance.find(Long.parseLong(value)));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {		
		try{		
			if(value == null)
				return "";
			return ((Defensoria)value).getId().toString();	
		}catch(Exception e){
			return "";
		}
	}
}