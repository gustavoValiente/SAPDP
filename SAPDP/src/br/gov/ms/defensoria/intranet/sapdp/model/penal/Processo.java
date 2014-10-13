package br.gov.ms.defensoria.intranet.sapdp.model.penal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.Area;
import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_PROCESSO")
public class Processo implements IGenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;		
	private Long idAssistido;
	private Date dataCadastro;
	private String usuarioCadastro;
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Reu reu;
	private String numeroProcesso;
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Regime regime;
	@OneToOne
	@JoinColumn(name = "id_comarca")
	private MunicipioDistrito comarca;
	private Integer vara;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Area tipoVara;
	private Integer anoPena;
	private Integer mesPena;
	private Integer diaPena;
	private String resultado;
	

	public Processo() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdAssistido() {
		return idAssistido;
	}


	public void setIdAssistido(Long idAssistido) {
		this.idAssistido = idAssistido;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public String getUsuarioCadastro() {
		return usuarioCadastro;
	}


	public void setUsuarioCadastro(String usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}


	public Reu getReu() {
		return reu;
	}


	public void setReu(Reu reu) {
		this.reu = reu;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}


	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}


	public Regime getRegime() {
		return regime;
	}


	public void setRegime(Regime regime) {
		this.regime = regime;
	}

	

	public MunicipioDistrito getComarca() {
		return comarca;
	}


	public void setComarca(MunicipioDistrito comarca) {
		this.comarca = comarca;
	}


	public Integer getVara() {
		return vara;
	}


	public void setVara(Integer vara) {
		this.vara = vara;
	}


	public Area getTipoVara() {
		return tipoVara;
	}


	public void setTipoVara(Area tipoVara) {
		this.tipoVara = tipoVara;
	}


	public Integer getAnoPena() {
		return anoPena;
	}


	public void setAnoPena(Integer anoPena) {
		this.anoPena = anoPena;
	}


	public Integer getMesPena() {
		return mesPena;
	}


	public void setMesPena(Integer mesPena) {
		this.mesPena = mesPena;
	}


	public Integer getDiaPena() {
		return diaPena;
	}


	public void setDiaPena(Integer diaPena) {
		this.diaPena = diaPena;
	}


	public String getResultado() {
		return resultado;
	}


	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
