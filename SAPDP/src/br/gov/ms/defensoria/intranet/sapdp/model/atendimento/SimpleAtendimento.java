package br.gov.ms.defensoria.intranet.sapdp.model.atendimento;

import java.util.Date;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */

public class SimpleAtendimento {

	private Long idAtendimento;
	private Long idDesignacao;
	private String nomeAssistido;
	private String nomeAtividade;
	private String nomeProvidencia;
	private String nomeUnidade;
	private String nomeDefensor;
	private Date dataAtendimento;
	private String observacao;
	private String fatoNarrado;
	private String loginSubstituicao;
	private Long idDefensoria;
	private Integer juizado;

	public SimpleAtendimento() {
		// TODO Auto-generated constructor stub
	}

	public SimpleAtendimento(Long idAtendimento, Long idDesignacao,
			String nomeAssistido, String nomeAtividade, String nomeProvidencia,
			String nomeUnidade, String nomeDefensor, Date dataAtendimento,
			String observacao, String fatoNarrado, String loginSubstituicao,
			Long idDefensoria, Integer juizado) {
		super();
		this.idAtendimento = idAtendimento;
		this.idDesignacao = idDesignacao;
		this.nomeAssistido = nomeAssistido;
		this.nomeAtividade = nomeAtividade;
		this.nomeProvidencia = nomeProvidencia;
		this.nomeUnidade = nomeUnidade;
		this.dataAtendimento = dataAtendimento;
		this.observacao = observacao;
		this.fatoNarrado = fatoNarrado;
		this.nomeDefensor = nomeDefensor;
		this.loginSubstituicao = loginSubstituicao;
		this.idDefensoria = idDefensoria;
		this.juizado = juizado;
	}

	public Long getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
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

	public String getNomeAtividade() {
		return nomeAtividade;
	}

	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}

	public String getNomeProvidencia() {
		return nomeProvidencia;
	}

	public void setNomeProvidencia(String nomeProvidencia) {
		this.nomeProvidencia = nomeProvidencia;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getFatoNarrado() {
		return fatoNarrado;
	}

	public void setFatoNarrado(String fatoNarrado) {
		this.fatoNarrado = fatoNarrado;
	}

	public String getNomeDefensor() {
		return nomeDefensor;
	}

	public void setNomeDefensor(String nomeDefensor) {
		this.nomeDefensor = nomeDefensor;
	}

	public String getLoginSubstituicao() {
		return loginSubstituicao;
	}

	public void setLoginSubstituicao(String loginSubstituicao) {
		this.loginSubstituicao = loginSubstituicao;
	}

	public Long getIdDefensoria() {
		return idDefensoria;
	}

	public void setIdDefensoria(Long idDefensoria) {
		this.idDefensoria = idDefensoria;
	}

	public Integer getJuizado() {
		return juizado;
	}

	public void setJuizado(Integer juizado) {
		this.juizado = juizado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataAtendimento == null) ? 0 : dataAtendimento.hashCode());
		result = prime * result
				+ ((fatoNarrado == null) ? 0 : fatoNarrado.hashCode());
		result = prime * result
				+ ((idAtendimento == null) ? 0 : idAtendimento.hashCode());
		result = prime * result
				+ ((idDesignacao == null) ? 0 : idDesignacao.hashCode());
		result = prime
				* result
				+ ((loginSubstituicao == null) ? 0 : loginSubstituicao
						.hashCode());
		result = prime * result
				+ ((nomeAssistido == null) ? 0 : nomeAssistido.hashCode());
		result = prime * result
				+ ((nomeAtividade == null) ? 0 : nomeAtividade.hashCode());
		result = prime * result
				+ ((nomeDefensor == null) ? 0 : nomeDefensor.hashCode());
		result = prime * result
				+ ((nomeProvidencia == null) ? 0 : nomeProvidencia.hashCode());
		result = prime * result
				+ ((nomeUnidade == null) ? 0 : nomeUnidade.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleAtendimento other = (SimpleAtendimento) obj;
		if (dataAtendimento == null) {
			if (other.dataAtendimento != null)
				return false;
		} else if (!dataAtendimento.equals(other.dataAtendimento))
			return false;
		if (fatoNarrado == null) {
			if (other.fatoNarrado != null)
				return false;
		} else if (!fatoNarrado.equals(other.fatoNarrado))
			return false;
		if (idAtendimento == null) {
			if (other.idAtendimento != null)
				return false;
		} else if (!idAtendimento.equals(other.idAtendimento))
			return false;
		if (idDesignacao == null) {
			if (other.idDesignacao != null)
				return false;
		} else if (!idDesignacao.equals(other.idDesignacao))
			return false;
		if (loginSubstituicao == null) {
			if (other.loginSubstituicao != null)
				return false;
		} else if (!loginSubstituicao.equals(other.loginSubstituicao))
			return false;
		if (nomeAssistido == null) {
			if (other.nomeAssistido != null)
				return false;
		} else if (!nomeAssistido.equals(other.nomeAssistido))
			return false;
		if (nomeAtividade == null) {
			if (other.nomeAtividade != null)
				return false;
		} else if (!nomeAtividade.equals(other.nomeAtividade))
			return false;
		if (nomeDefensor == null) {
			if (other.nomeDefensor != null)
				return false;
		} else if (!nomeDefensor.equals(other.nomeDefensor))
			return false;
		if (nomeProvidencia == null) {
			if (other.nomeProvidencia != null)
				return false;
		} else if (!nomeProvidencia.equals(other.nomeProvidencia))
			return false;
		if (nomeUnidade == null) {
			if (other.nomeUnidade != null)
				return false;
		} else if (!nomeUnidade.equals(other.nomeUnidade))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		return true;
	}

}
