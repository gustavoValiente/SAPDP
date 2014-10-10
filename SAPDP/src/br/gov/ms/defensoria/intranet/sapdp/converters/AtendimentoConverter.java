package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AtendimentoRepositorio;

/**
 * 
 * @author Alain Gabriel Cáceres Álvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Atendimento</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "atendimentoConverter")
public class AtendimentoConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return AtendimentoRepositorio - Instância de um sessionbean
	 */
	private AtendimentoRepositorio  getSession() {
		try {
			return (AtendimentoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/AtendimentoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		AtendimentoRepositorio  instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.find(Long.parseLong(value)) == null ? null : instance.find(Long.parseLong(value)));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Atendimento)value).getId().toString();		
		}catch(Exception e){
			return "";
		}
	}
}