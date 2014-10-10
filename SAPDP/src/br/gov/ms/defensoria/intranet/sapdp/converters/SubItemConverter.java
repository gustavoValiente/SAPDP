package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.SubItemRepositorio;

/**
 * 
 * @author Alain Gabriel Cáceres Álvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>SubItemAtendimento</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "subItemConverter")
public class SubItemConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return SubItemRepositorio - Instância de um sessionbean
	 */
	private SubItemRepositorio  getSession() {
		try {
			return (SubItemRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/SubItemRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		SubItemRepositorio  instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.obterSubItemPorNome(value) == null ? null : instance.obterSubItemPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((SubItemAtendimento)value).getNome().toString();		
		}catch(Exception e){
			return "";
		}
	}
}