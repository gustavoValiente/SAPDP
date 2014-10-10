package br.gov.ms.defensoria.intranet.sapdp.model.assistido;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public enum Sexo {
	F("Feminino"), M("Masculino");

	private String texto;

	private Sexo(String s) {
		this.texto = s;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
