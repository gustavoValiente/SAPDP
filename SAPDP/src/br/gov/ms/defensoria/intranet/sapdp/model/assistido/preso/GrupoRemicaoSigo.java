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
@Table(name = "TB_GRUPO_REMICAO_SIGO")
public class GrupoRemicaoSigo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	private Long idAssistido;
	@Temporal(TemporalType.DATE)
	private Date periodoInicio;
	@Temporal(TemporalType.DATE)
	private Date periodoFim;	
	private Integer usufruiu;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_total_remicao")
	private TotalRemicaoSigo totalRemicao;
	
	
	public GrupoRemicaoSigo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAssistido() {
		return idAssistido;
	}


	public void setIdAssistido(Long idAssistido) {
		this.idAssistido = idAssistido;
	}

	public TotalRemicaoSigo getTotalRemicao() {
		return totalRemicao;
	}

	public void setTotalRemicao(TotalRemicaoSigo totalRemicao) {
		this.totalRemicao = totalRemicao;
	}

	public Date getPeriodoInicio() {
		return periodoInicio;
	}


	public void setPeriodoInicio(Date periodoInicio) {
		this.periodoInicio = periodoInicio;
	}


	public Date getPeriodoFim() {
		return periodoFim;
	}


	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}

	public Integer getUsufruiu() {
		return usufruiu;
	}

	public void setUsufruiu(Integer usufruiu) {
		this.usufruiu = usufruiu;
	}

	
}
