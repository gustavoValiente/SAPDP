package br.gov.ms.defensoria.intranet.sapdp.model.atendimento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;

@Entity
@Table(name = "TB_ATENDIMENTO")
public class Atendimento implements IGenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtendimento;
	@OneToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_unidade")
	private Unidade unidade;
	@OneToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_item")
	private ItemAtendimento item;
	@OneToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_subitem")
	private SubItemAtendimento subItem;
	@OneToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_designacao")
	private Designacoes designacao;
	@Column(length = 1500)
	private String observacao;
	@Column(length = 2000)
	private String fatoNarrado;
	private String ultimoAtendimento;
	private String usuarioCadastro;
	private String usuarioAlteracao;
	private Date dataAlteracao;
	private Long idDefensoriaDefensor;
	private Long idDefensoriaSubstituicao;
	private Integer juizado;

	public Atendimento() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public ItemAtendimento getItem() {
		return item;
	}

	public void setItem(ItemAtendimento item) {
		this.item = item;
	}

	public SubItemAtendimento getSubItem() {
		return subItem;
	}

	public void setSubItem(SubItemAtendimento subItem) {
		this.subItem = subItem;
	}

	public Designacoes getDesignacao() {
		return designacao;
	}

	public void setDesignacao(Designacoes designacao) {
		this.designacao = designacao;
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

	public String getUltimoAtendimento() {
		return ultimoAtendimento;
	}

	public void setUltimoAtendimento(String ultimoAtendimento) {
		this.ultimoAtendimento = ultimoAtendimento;
	}

	public String getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(String usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getIdDefensoriaDefensor() {
		return idDefensoriaDefensor;
	}

	public void setIdDefensoriaDefensor(Long idDefensoriaDefensor) {
		this.idDefensoriaDefensor = idDefensoriaDefensor;
	}

	public Long getIdDefensoriaSubstituicao() {
		return idDefensoriaSubstituicao;
	}

	public void setIdDefensoriaSubstituicao(Long idDefensoriaSubstituicao) {
		this.idDefensoriaSubstituicao = idDefensoriaSubstituicao;
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
				+ ((dataAlteracao == null) ? 0 : dataAlteracao.hashCode());
		result = prime * result
				+ ((dataAtendimento == null) ? 0 : dataAtendimento.hashCode());
		result = prime * result
				+ ((designacao == null) ? 0 : designacao.hashCode());
		result = prime * result
				+ ((fatoNarrado == null) ? 0 : fatoNarrado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idDefensoriaDefensor == null) ? 0 : idDefensoriaDefensor
						.hashCode());
		result = prime
				* result
				+ ((idDefensoriaSubstituicao == null)
						? 0
						: idDefensoriaSubstituicao.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((subItem == null) ? 0 : subItem.hashCode());
		result = prime
				* result
				+ ((ultimoAtendimento == null) ? 0 : ultimoAtendimento
						.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		result = prime
				* result
				+ ((usuarioAlteracao == null) ? 0 : usuarioAlteracao.hashCode());
		result = prime * result
				+ ((usuarioCadastro == null) ? 0 : usuarioCadastro.hashCode());
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
		Atendimento other = (Atendimento) obj;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null)
				return false;
		} else if (!dataAlteracao.equals(other.dataAlteracao))
			return false;
		if (dataAtendimento == null) {
			if (other.dataAtendimento != null)
				return false;
		} else if (!dataAtendimento.equals(other.dataAtendimento))
			return false;
		if (designacao == null) {
			if (other.designacao != null)
				return false;
		} else if (!designacao.equals(other.designacao))
			return false;
		if (fatoNarrado == null) {
			if (other.fatoNarrado != null)
				return false;
		} else if (!fatoNarrado.equals(other.fatoNarrado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idDefensoriaDefensor == null) {
			if (other.idDefensoriaDefensor != null)
				return false;
		} else if (!idDefensoriaDefensor.equals(other.idDefensoriaDefensor))
			return false;
		if (idDefensoriaSubstituicao == null) {
			if (other.idDefensoriaSubstituicao != null)
				return false;
		} else if (!idDefensoriaSubstituicao
				.equals(other.idDefensoriaSubstituicao))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (subItem == null) {
			if (other.subItem != null)
				return false;
		} else if (!subItem.equals(other.subItem))
			return false;
		if (ultimoAtendimento == null) {
			if (other.ultimoAtendimento != null)
				return false;
		} else if (!ultimoAtendimento.equals(other.ultimoAtendimento))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		if (usuarioAlteracao == null) {
			if (other.usuarioAlteracao != null)
				return false;
		} else if (!usuarioAlteracao.equals(other.usuarioAlteracao))
			return false;
		if (usuarioCadastro == null) {
			if (other.usuarioCadastro != null)
				return false;
		} else if (!usuarioCadastro.equals(other.usuarioCadastro))
			return false;
		return true;
	}

}
