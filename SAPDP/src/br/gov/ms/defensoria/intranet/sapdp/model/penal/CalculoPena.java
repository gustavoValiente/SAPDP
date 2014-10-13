package br.gov.ms.defensoria.intranet.sapdp.model.penal;

import java.util.Date;

import javax.persistence.Entity;
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
@Table(name = "TB_CALCULO_PENA")
public class CalculoPena {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;		
	@OneToOne
	@JoinColumn(name = "id_processo")
	private Processo processo;
	
	@Temporal(TemporalType.DATE)
	private Date dataBasePena;
	@Temporal(TemporalType.DATE)
	private Date dataFechado;
	@Temporal(TemporalType.DATE)
	private Date dataAberto;
	@Temporal(TemporalType.DATE)
	private Date dataTermino;
	@Temporal(TemporalType.DATE)
	private Date dataSemiAberto;
	@Temporal(TemporalType.DATE)
	private Date dataLivramentoCondicional;
	@Temporal(TemporalType.DATE)
	private Date dataExtincao;

	public CalculoPena() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Processo getProcesso() {
		return processo;
	}


	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	

	public Date getDataBasePena() {
		return dataBasePena;
	}


	public void setDataBasePena(Date dataBasePena) {
		this.dataBasePena = dataBasePena;
	}


	public Date getDataFechado() {
		return dataFechado;
	}


	public void setDataFechado(Date dataFechado) {
		this.dataFechado = dataFechado;
	}


	public Date getDataAberto() {
		return dataAberto;
	}


	public void setDataAberto(Date dataAberto) {
		this.dataAberto = dataAberto;
	}


	public Date getDataTermino() {
		return dataTermino;
	}


	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}


	public Date getDataSemiAberto() {
		return dataSemiAberto;
	}


	public void setDataSemiAberto(Date dataSemiAberto) {
		this.dataSemiAberto = dataSemiAberto;
	}


	public Date getDataLivramentoCondicional() {
		return dataLivramentoCondicional;
	}


	public void setDataLivramentoCondicional(Date dataLivramentoCondicional) {
		this.dataLivramentoCondicional = dataLivramentoCondicional;
	}


	public Date getDataExtincao() {
		return dataExtincao;
	}


	public void setDataExtincao(Date dataExtincao) {
		this.dataExtincao = dataExtincao;
	}

	
}
