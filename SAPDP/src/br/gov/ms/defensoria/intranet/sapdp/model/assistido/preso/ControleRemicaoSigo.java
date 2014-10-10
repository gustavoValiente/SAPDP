package br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_CONTROLE_REMICAO_SIGO")
public class ControleRemicaoSigo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_trabalho")
	private TipoTrabalho tipoTrabalho;
	@Temporal(TemporalType.DATE)
	private Date dataInicial;
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	private Long idGrupo;

	
	public ControleRemicaoSigo() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public TipoTrabalho getTipoTrabalho() {
		return tipoTrabalho;
	}


	public void setTipoTrabalho(TipoTrabalho tipoTrabalho) {
		this.tipoTrabalho = tipoTrabalho;
	}


	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public Long getIdGrupo() {
		return idGrupo;
	}


	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}	
}
