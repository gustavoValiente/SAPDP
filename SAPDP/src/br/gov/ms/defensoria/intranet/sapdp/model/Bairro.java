package br.gov.ms.defensoria.intranet.sapdp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_BAIRRO")
public class Bairro implements IGenericEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_municipio")
	private MunicipioDistrito municipio;

	public Bairro() {

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

	public MunicipioDistrito getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioDistrito municipio) {
		this.municipio = municipio;
	}

}
