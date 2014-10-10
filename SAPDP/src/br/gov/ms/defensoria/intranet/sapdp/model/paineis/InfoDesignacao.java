package br.gov.ms.defensoria.intranet.sapdp.model.paineis;

import java.util.Date;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class InfoDesignacao {
	private Long idDesignacao;
	private Date dataChamado;
	private String senha;
	private String sala;
	private String preferencial;

	public InfoDesignacao() {

	}

	public InfoDesignacao(Long idDesignacao, Date dataChamado, String senha,
			String sala, String preferencial) {
		super();
		this.idDesignacao = idDesignacao;
		this.dataChamado = dataChamado;
		this.senha = senha;
		this.sala = sala;
		this.preferencial = preferencial;
	}

	public Long getIdDesignacao() {
		return idDesignacao;
	}

	public void setIdDesignacao(Long idDesignacao) {
		this.idDesignacao = idDesignacao;
	}

	public Date getDataChamado() {
		return dataChamado;
	}

	public void setDataChamado(Date dataChamado) {
		this.dataChamado = dataChamado;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getPreferencial() {
		return preferencial;
	}

	public void setPreferencial(String preferencial) {
		this.preferencial = preferencial;
	}

}