package br.gov.ms.defensoria.intranet.sapdp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;

/**
 * Designa uma divis�o territorial espec�fica, que indica os limites
 * territoriais da compet�ncia de um determinado juiz ou Ju�zo de primeira
 * inst�ncia. Assim, pode haver comarcas que coincidam com os limites de um
 * munic�pio, ou que os ultrapasse.
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_DEFENSORIA")
public class Defensoria implements IGenericEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@OneToOne
	@JoinColumn(name = "id_comarca")
	private MunicipioDistrito municipioDistrito;
	@Enumerated(EnumType.STRING)
	private TipoDefensoria tipo;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TB_NUCLEOS_DEFENSORIAS", joinColumns = @JoinColumn(name = "id_defensoria"), inverseJoinColumns = @JoinColumn(name = "id_nucleo"))
	private List<Nucleo> nucleos;

	public Defensoria() {

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

	public MunicipioDistrito getMunicipioDistrito() {
		return municipioDistrito;
	}

	public void setMunicipioDistrito(MunicipioDistrito municipioDistrito) {
		this.municipioDistrito = municipioDistrito;
	}

	public TipoDefensoria getTipo() {
		return tipo;
	}

	public void setTipo(TipoDefensoria tipo) {
		this.tipo = tipo;
	}

	public List<Nucleo> getNucleos() {
		return nucleos;
	}

	public void setNucleos(List<Nucleo> nucleos) {
		this.nucleos = nucleos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Defensoria other = (Defensoria) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
