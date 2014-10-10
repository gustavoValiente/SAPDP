package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ItemRepositorio;

/**
 * 
 * @author Alain Gabriel Cáceres Álvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>ItemAtendimento</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "itemConverter")
public class ItemConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return ItemRepositorio - Instância de um sessionbean
	 */
	private ItemRepositorio  getSession() {
		try {
			return (ItemRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/ItemRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		ItemRepositorio  instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.buscarItemPorNome(value) == null ? null : instance.buscarItemPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((ItemAtendimento)value).getNome().toString();		
		}catch(Exception e){
			return "";
		}
	}
}