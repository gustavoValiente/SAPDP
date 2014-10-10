package br.gov.ms.defensoria.intranet.sapdp.model.paineis;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_PAINEL_UNIDADE")
public class Painel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Unidade unidade;
	@Column(length = 50)
	private String nome;
	@Column(length = 4)
	private String senha;
	@Enumerated(EnumType.STRING)
	private TipoChamadoSenha tipoChamadoSenha;
	@Column(length = 10)
	private String salaGuiche;
	@Column(length = 20)
	private String statusSenha;
	@Transient
	private List<String> ultimosChamados;
	@CollectionTable(name = "TB_PAINEL_NUCLEOS")
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> nucleos;

	public Painel() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoChamadoSenha getTipoChamadoSenha() {
		return tipoChamadoSenha;
	}

	public void setTipoChamadoSenha(TipoChamadoSenha tipoChamadoSenha) {
		this.tipoChamadoSenha = tipoChamadoSenha;
	}

	public String getSalaGuiche() {
		return salaGuiche;
	}

	public void setSalaGuiche(String salaGuiche) {
		this.salaGuiche = salaGuiche;
	}

	public String getStatusSenha() {
		return statusSenha;
	}

	public void setStatusSenha(String statusSenha) {
		this.statusSenha = statusSenha;
	}

	public List<String> getUltimosChamados() {
		return ultimosChamados;
	}

	public void setUltimosChamados(List<String> ultimosChamados) {
		this.ultimosChamados = ultimosChamados;
	}

	public List<String> getNucleos() {
		return nucleos;
	}

	public void setNucleos(List<String> nucleos) {
		this.nucleos = nucleos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Painel)) {
			return false;
		}
		Painel other = (Painel) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (unidade == null) {
			if (other.unidade != null) {
				return false;
			}
		} else if (!unidade.equals(other.unidade)) {
			return false;
		}
		return true;
	}

}