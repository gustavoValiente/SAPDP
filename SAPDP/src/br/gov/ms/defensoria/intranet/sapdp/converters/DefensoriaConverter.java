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
@FacesConverter(value = "defensoriaConverter", forClass=Defensoria.class)
public class DefensoriaConverter implements Converter {
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
		System.out.println("<>>>>>>>>>>>>>>>>>>>>>>XXXXXXXXXXXX"+value);
		if(value == "" || value == null)
			return "";	
		return (instance.obterDefensoriaPorNome(value) == null ? null : instance.obterDefensoriaPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {		
		try{		
			return ((Defensoria)value).getNome().toString();	
		}catch(Exception e){
			return "";
		}
	}
}