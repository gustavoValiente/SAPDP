package br.gov.ms.defensoria.intranet.sapdp.model.usuarios;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements IGenericEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, unique = true)
	private String login;
	@Column(nullable = false)
	private String nome;
	@Column(length = 50)
	private String senha;
	@Column(precision = 1)
	private Integer status;
	@ElementCollection(targetClass = Grupo.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "TB_GRUPOS_USUARIOS", joinColumns = @JoinColumn(name = "login", nullable = false), uniqueConstraints = {@UniqueConstraint(columnNames = {
			"login", "groupname"})})
	@Enumerated(EnumType.STRING)
	@Column(name = "groupname", length = 64, nullable = false)
	private List<Grupo> grupo;
	@Column(length = 20)
	private String celular;
	@Column(length = 100)
	private String email;
	@Column(length = 20)
	private String telefone1;
	@Column(length = 20)
	private String telefone2;
	@Column(length = 20)
	private String telefone3;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	private Unidade unidade;
	@Column(length = 20)
	private String sala;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoAcesso;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "id_usuario")
	private List<DadosAssessoria> dadosAssessoria;
	private Long idDefensoria;
	private String loginSubstituicao;
	@Transient
	private String nomeDefensorSubstituto;
	@Transient
	private Long idDefensoriaSubstituicao;

	public Usuario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Grupo> getGrupo() {
		return grupo;
	}

	public void setGrupo(List<Grupo> grupo) {
		this.grupo = grupo;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public List<DadosAssessoria> getDadosAssessoria() {
		return dadosAssessoria;
	}

	public void setDadosAssessoria(List<DadosAssessoria> dadosAssessoria) {
		this.dadosAssessoria = dadosAssessoria;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public Long getId() {
		return null;
	}

}
