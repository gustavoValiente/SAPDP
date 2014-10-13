package br.gov.ms.defensoria.intranet.sapdp.model.penal;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public enum Reu {
	PRIMARIO("Primario"), REINCIDENTE("Reincidente"), TECNICAMENTE_PRIMARIO("Tecnicamente Primário");

	private String texto;

	private Reu(String s) {
		this.texto = s;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
