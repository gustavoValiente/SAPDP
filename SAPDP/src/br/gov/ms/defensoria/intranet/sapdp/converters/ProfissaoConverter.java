package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Profissao;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ProfissaoRepositorio;

/**
 * 
 * @author Rafael Gouveia da Silva
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Profissao</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "profissaoConverter")
public class ProfissaoConverter implements Converter {
	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return ProfissaoRepositorio - Instância de um sessionbean
	 */
	private ProfissaoRepositorio getSession() {
		try {
			return (ProfissaoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/ProfissaoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		ProfissaoRepositorio instance = getSession();
		return instance.obterProfissaoPorNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}
		
		return ((Profissao)value).getNome().toString();		
	}
}