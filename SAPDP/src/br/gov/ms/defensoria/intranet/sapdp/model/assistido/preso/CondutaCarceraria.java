package br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public enum CondutaCarceraria {
	BOM("Bom"), RUIM("Ruim");

	private String texto;

	private CondutaCarceraria(String s) {
		this.texto = s;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
