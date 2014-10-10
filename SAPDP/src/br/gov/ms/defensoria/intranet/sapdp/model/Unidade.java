package br.gov.ms.defensoria.intranet.sapdp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_UNIDADE")
public class Unidade implements IGenericEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(length = 400)
	private String descricao;
	@OneToOne
	@JoinColumn(name = "id_municipio")
	private MunicipioDistrito municipioDistrito;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	private List<Painel> paineis;

	public Unidade() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MunicipioDistrito getMunicipioDistrito() {
		return municipioDistrito;
	}

	public void setMunicipioDistrito(MunicipioDistrito municipioDistrito) {
		this.municipioDistrito = municipioDistrito;
	}

	public List<Painel> getPaineis() {
		return paineis;
	}

	public void setPaineis(List<Painel> paineis) {
		this.paineis = paineis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((municipioDistrito == null) ? 0 : municipioDistrito
						.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Unidade other = (Unidade) obj;
		if (municipioDistrito == null) {
			if (other.municipioDistrito != null)
				return false;
		} else if (!municipioDistrito.equals(other.municipioDistrito))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
