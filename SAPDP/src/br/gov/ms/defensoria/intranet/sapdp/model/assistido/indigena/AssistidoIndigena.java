package br.gov.ms.defensoria.intranet.sapdp.model.assistido.indigena;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_ASSISTIDO_INDIGENA")
public class AssistidoIndigena extends Assistido {
	@OneToOne
	@JoinColumn(name = "id_etnia")
	private Etnia etnia;
	@OneToOne
	@JoinColumn(name = "id_aldeia")
	private Aldeia aldeia;

	public AssistidoIndigena() {

	}

	public Etnia getEtnia() {
		return etnia;
	}

	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	public Aldeia getAldeia() {
		return aldeia;
	}

	public void setAldeia(Aldeia aldeia) {
		this.aldeia = aldeia;
	}

}
