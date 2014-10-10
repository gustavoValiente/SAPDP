package br.gov.ms.defensoria.intranet.sapdp.model.atendimento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_DESIGNACOES")
public class Designacoes implements IGenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name = "id_assistido")
	private Assistido assistido;
	@OneToOne
	@JoinColumn(name = "id_nucleo")
	private Nucleo nucleo;
	@OneToOne
	@JoinColumn(name = "id_defensor")
	private Usuario defensor;
	@OneToOne
	@JoinColumn(name = "id_atendente")
	private Usuario atendente;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDesignacao;
	@Column(length = 50)
	private String senha;
	@Column(length = 5)
	private String preferencial;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private StatusDesignacao status;
	private Long idAtendimentoPai;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataChamado;
	private String loginSubstituicao;
	@Column(length = 30)
	private String sala;

	public Designacoes() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assistido getAssistido() {
		return assistido;
	}

	public void setAssistido(Assistido assistido) {
		this.assistido = assistido;
	}

	public Nucleo getNucleo() {
		return nucleo;
	}

	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getDefensor() {
		return defensor;
	}

	public void setDefensor(Usuario defensor) {
		this.defensor = defensor;
	}

	public Usuario getAtendente() {
		return atendente;
	}

	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}

	public Date getDataDesignacao() {
		return dataDesignacao;
	}

	public void setDataDesignacao(Date dataDesignacao) {
		this.dataDesignacao = dataDesignacao;
	}

	public String getPreferencial() {
		return preferencial;
	}

	public void setPreferencial(String preferencial) {
		this.preferencial = preferencial;
	}

	public StatusDesignacao getStatus() {
		return status;
	}

	public void setStatus(StatusDesignacao status) {
		this.status = status;
	}

	public Long getIdAtendimentoPai() {
		return idAtendimentoPai;
	}

	public void setIdAtendimentoPai(Long idAtendimentoPai) {
		this.idAtendimentoPai = idAtendimentoPai;
	}

	public Date getDataChamado() {
		return dataChamado;
	}

	public void setDataChamado(Date dataChamado) {
		this.dataChamado = dataChamado;
	}

	public String getLoginSubstituicao() {
		return loginSubstituicao;
	}

	public void setLoginSubstituicao(String loginSubstituicao) {
		this.loginSubstituicao = loginSubstituicao;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assistido == null) ? 0 : assistido.hashCode());
		result = prime * result
				+ ((atendente == null) ? 0 : atendente.hashCode());
		result = prime * result
				+ ((dataChamado == null) ? 0 : dataChamado.hashCode());
		result = prime * result
				+ ((dataDesignacao == null) ? 0 : dataDesignacao.hashCode());
		result = prime * result
				+ ((defensor == null) ? 0 : defensor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idAtendimentoPai == null) ? 0 : idAtendimentoPai.hashCode());
		result = prime
				* result
				+ ((loginSubstituicao == null) ? 0 : loginSubstituicao
						.hashCode());
		result = prime * result + ((nucleo == null) ? 0 : nucleo.hashCode());
		result = prime * result
				+ ((preferencial == null) ? 0 : preferencial.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Designacoes other = (Designacoes) obj;
		if (assistido == null) {
			if (other.assistido != null)
				return false;
		} else if (!assistido.equals(other.assistido))
			return false;
		if (atendente == null) {
			if (other.atendente != null)
				return false;
		} else if (!atendente.equals(other.atendente))
			return false;
		if (dataChamado == null) {
			if (other.dataChamado != null)
				return false;
		} else if (!dataChamado.equals(other.dataChamado))
			return false;
		if (dataDesignacao == null) {
			if (other.dataDesignacao != null)
				return false;
		} else if (!dataDesignacao.equals(other.dataDesignacao))
			return false;
		if (defensor == null) {
			if (other.defensor != null)
				return false;
		} else if (!defensor.equals(other.defensor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idAtendimentoPai == null) {
			if (other.idAtendimentoPai != null)
				return false;
		} else if (!idAtendimentoPai.equals(other.idAtendimentoPai))
			return false;
		if (loginSubstituicao == null) {
			if (other.loginSubstituicao != null)
				return false;
		} else if (!loginSubstituicao.equals(other.loginSubstituicao))
			return false;
		if (nucleo == null) {
			if (other.nucleo != null)
				return false;
		} else if (!nucleo.equals(other.nucleo))
			return false;
		if (preferencial == null) {
			if (other.preferencial != null)
				return false;
		} else if (!preferencial.equals(other.preferencial))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
