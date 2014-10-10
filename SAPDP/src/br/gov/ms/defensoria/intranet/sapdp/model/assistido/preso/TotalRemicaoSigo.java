package br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_TOTAL_REMICAO_SIGO")
public class TotalRemicaoSigo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer tempoTrabalho;
	private Integer tempoRemicao;
	
	public TotalRemicaoSigo() {

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
