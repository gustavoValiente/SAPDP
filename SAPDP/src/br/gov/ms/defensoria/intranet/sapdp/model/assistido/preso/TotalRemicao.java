package br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_TOTAL_REMICAO")
public class TotalRemicao implements IGenericEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer tempoTrabalho;
	private Integer tempoRemicao;
	
	public TotalRemicao() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTempoTrabalho() {
		return tempoTrabalho;
	}

	public void setTempoTrabalho(Integer tempoTrabalho) {
		this.tempoTrabalho = tempoTrabalho;
	}

	public Integer getTempoRemicao() {
		return tempoRemicao;
	}

	public void setTempoRemicao(Integer tempoRemicao) {
		this.tempoRemicao = tempoRemicao;
	}

	
	
}
