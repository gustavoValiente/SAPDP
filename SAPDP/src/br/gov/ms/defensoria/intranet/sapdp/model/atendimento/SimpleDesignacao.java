package br.gov.ms.defensoria.intranet.sapdp.model.atendimento;

import java.util.Date;


/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */

public class SimpleDesignacao {
	
	private Long idDesignacao;
	private String nomeAssistido;
	private String loginDefensor;
	private String preferencial;
	private Date dataDesignacao;
	private String nucleo;
	private StatusDesignacao statusDesignacao;
	private Long idAtendimentoPai;
	private String loginSubstituicao;
	private String tipoDesignacao;
	
	
	public SimpleDesignacao(Long idDesignacao, String nomeAssistido, String loginDefensor, String preferencial, 
			Date dataDesignacao, String nucleo, StatusDesignacao statusDesignacao, Long idAtendimentoPai, 
			String loginSubstituicao, String tipoDesignacao) {
		
		this.idDesignacao = idDesignacao;
		this.nomeAssistido = nomeAssistido;
		this.loginDefensor = loginDefensor;
		this.preferencial = preferencial;
		this.dataDesignacao = dataDesignacao;
		this.nucleo = nucleo;
		this.statusDesignacao = statusDesignacao;
		this.idAtendimentoPai = idAtendimentoPai;
		this.loginSubstituicao = loginSubstituicao;
		this.tipoDesignacao = tipoDesignacao;
	}
	
	public SimpleDesignacao(Long idDesignacao, String nomeAssistido, String loginDefensor,  
			Date dataDesignacao, StatusDesignacao statusDesignacao, String loginSubstituicao, String tipoDesignacao) {
		
		this.idDesignacao = idDesignacao;
		this.nomeAssistido = nomeAssistido;
		this.loginDefensor = loginDefensor;		
		this.dataDesignacao = dataDesignacao;		
		this.statusDesignacao = statusDesignacao;		
		this.loginSubstituicao = loginSubstituicao;
		this.tipoDesignacao = tipoDesignacao;
	}

	public SimpleDesignacao() {
		
	}



	public Long getIdDesignacao() {
		return idDesignacao;
	}


	public void setIdDesignacao(Long idDesignacao) {
		this.idDesignacao = idDesignacao;
	}


	public String getNomeAssistido() {
		return nomeAssistido;
	}


	public void setNomeAssistido(String nomeAssistido) {
		this.nomeAssistido = nomeAssistido;
	}


	public String getLoginDefensor() {
		return loginDefensor;
	}


	public void setLoginDefensor(String loginDefensor) {
		this.loginDefensor = loginDefensor;
	}


	public String getPreferencial() {
		return preferencial;
	}


	public void setPreferencial(String preferencial) {
		this.preferencial = preferencial;
	}


	public Date getDataDesignacao() {
		return dataDesignacao;
	}


	public void setDataDesignacao(Date dataDesignacao) {
		this.dataDesignacao = dataDesignacao;
	}


	public String getNucleo() {
		return nucleo;
	}


	public void setNucleo(String nucleo) {
		this.nucleo = nucleo;
	}


	public StatusDesignacao getStatusDesignacao() {
		return statusDesignacao;
	}


	public void setStatusDesignacao(StatusDesignacao statusDesignacao) {
		this.statusDesignacao = statusDesignacao;
	}


	public Long getIdAtendimentoPai() {
		return idAtendimentoPai;
	}


	public void setIdAtendimentoPai(Long idAtendimentoPai) {
		this.idAtendimentoPai = idAtendimentoPai;
	}

	public String getLoginSubstituicao() {
		return loginSubstituicao;
	}

	public void setLoginSubstituicao(String loginSubstituicao) {
		this.loginSubstituicao = loginSubstituicao;
	}

	public String getTipoDesignacao() {
		return tipoDesignacao;
	}

	public void setTipoDesignacao(String tipoDesignacao) {
		this.tipoDesignacao = tipoDesignacao;
	}
	
	
	
}
