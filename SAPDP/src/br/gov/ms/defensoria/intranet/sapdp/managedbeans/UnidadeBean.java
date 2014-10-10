package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UnidadeRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class UnidadeBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Unidade
	@EJB
	private UnidadeRepositorio uRep;
	private Unidade unidade = new Unidade();
	private Unidade unidadeAssessoria = new Unidade();
	private List<Unidade> unidades = new ArrayList<Unidade>();
	
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Painel
	

	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;

	private EntityLazyModel unidadeLazy;

	public UnidadeBean() {

	}

	public EntityLazyModel getAllUnidade() {
		if (unidadeLazy == null) {
			unidadeLazy = new EntityLazyModel(unidade);
		}
		return unidadeLazy;
	}

	/**
	 * Insere/Altera o objeto Unidade
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.unidade.setMunicipioDistrito(this.municipioDistritoBean
				.buscarMunicipioDistrito(this.municipioDistritoBean
						.getMunicipioD().getNome()));
		if (this.unidade.getId() == null) {
			this.uRep.insert(this.unidade);
			this.unidade = new Unidade();
			fc.addMessage(null, new FacesMessage(
					"Unidade cadastrada com sucesso!"));
		} else {
			this.uRep.update(this.unidade);
			fc.addMessage(null, new FacesMessage(
					"Unidade Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas unidades
	 */
	public List<Unidade> getSelecionarTudo() {
		return uRep.selectAll();
	}

	/**
	 * Exclui um objeto Unidade
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.uRep.remove(this.unidade);
		fc.addMessage(null, new FacesMessage("Unidade exclu�da com sucesso!"));
	}

	/**
	 * Cria uma nova unidade
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.unidade = new Unidade();
		fc.addMessage(null, new FacesMessage(
				"Uma nova unidade pode ser adicionada!"));
	}

	/**
	 * Carrega unidades do municipio
	 * 
	 * @param id
	 *            - Long, codigo do municipio
	 * @return List<Unidade>
	 */
	public List<Unidade> obterUnidadesPorMunicipio(Long id) {
		return this.uRep.carregarUnidadePorMunicipio(id);
	}

	public void selecionarNomeDoMunicipio() {
		this.municipioDistritoBean.getMunicipioD().setNome(
				this.unidade.getMunicipioDistrito() == null ? "" : this.unidade
						.getMunicipioDistrito().getNome());
	}

	public MunicipioDistritoBean getMunicipioDistritoBean() {
		return municipioDistritoBean;
	}

	public void setMunicipioDistritoBean(
			MunicipioDistritoBean municipioDistritoBean) {
		this.municipioDistritoBean = municipioDistritoBean;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Unidade getUnidadeAssessoria() {
		return unidadeAssessoria;
	}

	public void setUnidadeAssessoria(Unidade unidadeAssessoria) {
		this.unidadeAssessoria = unidadeAssessoria;
	}

}
