package br.gov.ms.defensoria.intranet.sapdp.model.usuarios;


/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */

public class Substituicao {
	
	private String loginSubstituicao;
	private String nomeDefensorSubstituto;
	private Long idDefensoriaSubstituicao;
	
	public Substituicao() {
		// TODO Auto-generated constructor stub
	}
	
	public Substituicao(String loginSubstituicao,
			String nomeDefensorSubstituto, Long idDefensoriaSubstituicao) {
		super();
		this.loginSubstituicao = loginSubstituicao;
		this.nomeDefensorSubstituto = nomeDefensorSubstituto;
		this.idDefensoriaSubstituicao = idDefensoriaSubstituicao;
	}


	public String getLoginSubstituicao() {
		return loginSubstituicao;
	}


	public void setLoginSubstituicao(String loginSubstituicao) {
		this.loginSubstituicao = loginSubstituicao;
	}


	public String getNomeDefensorSubstituto() {
		return nomeDefensorSubstituto;
	}


	public void setNomeDefensorSubstituto(String nomeDefensorSubstituto) {
		this.nomeDefensorSubstituto = nomeDefensorSubstituto;
	}


	public Long getIdDefensoriaSubstituicao() {
		return idDefensoriaSubstituicao;
	}


	public void setIdDefensoriaSubstituicao(Long idDefensoriaSubstituicao) {
		this.idDefensoriaSubstituicao = idDefensoriaSubstituicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idDefensoriaSubstituicao == null) ? 0
						: idDefensoriaSubstituicao.hashCode());
		result = prime
				* result
				+ ((loginSubstituicao == null) ? 0 : loginSubstituicao
						.hashCode());
		result = prime
				* result
				+ ((nomeDefensorSubstituto == null) ? 0
						: nomeDefensorSubstituto.hashCode());
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
		Substituicao other = (Substituicao) obj;
		if (idDefensoriaSubstituicao == null) {
			if (other.idDefensoriaSubstituicao != null)
				return false;
		} else if (!idDefensoriaSubstituicao
				.equals(other.idDefensoriaSubstituicao))
			return false;
		if (loginSubstituicao == null) {
			if (other.loginSubstituicao != null)
				return false;
		} else if (!loginSubstituicao.equals(other.loginSubstituicao))
			return false;
		if (nomeDefensorSubstituto == null) {
			if (other.nomeDefensorSubstituto != null)
				return false;
		} else if (!nomeDefensorSubstituto.equals(other.nomeDefensorSubstituto))
			return false;
		return true;
	}
	
}
