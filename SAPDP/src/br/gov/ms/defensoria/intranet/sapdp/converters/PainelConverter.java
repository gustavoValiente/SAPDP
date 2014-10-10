package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.PainelRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Painel</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "painelConverter")
public class PainelConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return PainelRepositorio - Instância de um sessionbean
	 */
	private PainelRepositorio getSession() {
		try {
			return (PainelRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/PainelRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		PainelRepositorio instance = getSession();
		System.out.println(" +++++++ "+value);
		return (instance.obterPainelPorNome(value) == null ? null : instance.obterPainelPorNome(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println(" ------- "+value);
		try{		
			return ((Painel)value).getNome().toString();		
		}catch(Exception e){
			return "";
		}
	}
}