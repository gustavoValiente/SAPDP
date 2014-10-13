package br.gov.ms.defensoria.intranet.sapdp.model.penal;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public enum Regime {
	REGIME_FECHADO("Regime Fechado"), 
	SEMI_ABERTO("Semi Aberto"), 
	ABERTO("Aberto"), 
	LIVRAMENTO_CONDICIONAL("Livramento Condicional"), 
	EXTINCAO("Extinção");

	private String texto;

	private Regime(String s) {
		this.texto = s;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
