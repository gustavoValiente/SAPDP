package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NucleoRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Nucleo</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "nucleoConverter")
public class NucleoConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return NucleoRepositorio - Instância de um sessionbean
	 */
	private NucleoRepositorio  getSession() {
		try {
			return (NucleoRepositorio ) new InitialContext()
					.lookup("java:global/SAPDP/NucleoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		NucleoRepositorio  instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.buscarNucleoPorNome(value) == null ? null : instance.buscarNucleoPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Nucleo)value).getNome().toString();		
		}catch(Exception e){
			return "";
		}
	}
}