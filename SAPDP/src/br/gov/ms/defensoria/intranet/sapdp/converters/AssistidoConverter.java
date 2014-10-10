package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AssistidoRepositorio;

/**
 * 
 * @author Alain Gabriel Cáceres Álvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Assistido</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "assistidoConverter", forClass=Assistido.class)
public class AssistidoConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return AssistidoRepositorio - Instância de um sessionbean
	 */
	private AssistidoRepositorio getSession() {
		try {
			return (AssistidoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/AssistidoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		AssistidoRepositorio instance = getSession();
		System.out.println(" +++++++ "+value);
		System.out.println(" +++++++ "+instance.obterAssistidoPorNome(value));
		return (instance.obterAssistidoPorNome(value) == null ? null : instance.obterAssistidoPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Assistido)value).getNome();		
		}catch(Exception e){
			return "";
		}
	}
}