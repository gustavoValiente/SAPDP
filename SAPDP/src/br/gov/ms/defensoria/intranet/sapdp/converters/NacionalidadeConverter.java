package br.gov.ms.defensoria.intranet.sapdp.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Nacionalidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NacionalidadeRepositorio;

/**
 * 
 * @author Alain Gabriel C�ceres �lvarez
 * @since JDK7
 * @version 1.0
 * 
 * Converter de <code>Nacionalidade</code> injetando um EJB.
 * 
 */
@FacesConverter(value = "nacionalidadeConverter")
public class NacionalidadeConverter implements Converter {
	/**
	 * Retorna uma inst�ncia com JNDI.
	 * 
	 * @return EstadoRepositorio - Inst�ncia de um sessionbean
	 */
	private NacionalidadeRepositorio getSession() {
		try {
			return (NacionalidadeRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/NacionalidadeRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		NacionalidadeRepositorio instance = getSession();
		return instance.obterNacionalidadePorNome(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return null;
		}
		
		return ((Nacionalidade)value).getNome().toString();		
	}
}