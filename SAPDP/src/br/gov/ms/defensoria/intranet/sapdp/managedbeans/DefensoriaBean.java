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
import br.gov.ms.defensoria.intranet.sapdp.model.Defensoria;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.DefensoriaRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class DefensoriaBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Defensoria
	@EJB
	private DefensoriaRepositorio uRep;
	private Defensoria defensoria = new Defensoria();
	private List<Defensoria> defensorias;

	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;

	@ManagedProperty(value = "#{nucleoBean}")
	private NucleoBean nucleoBean;

	private EntityLazyModel defensoriaLazy;

	public DefensoriaBean() {

	}

	public EntityLazyModel getAllDefensoria() {
		if (defensoriaLazy == null) {
			defensoriaLazy = new EntityLazyModel(defensoria);
		}
		return defensoriaLazy;
	}

	/**
	 * Insere/Altera o objeto Defensoria
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.defensoria.setMunicipioDistrito(this.municipioDistritoBean
				.buscarMunicipioDistrito(this.municipioDistritoBean
						.getMunicipioD().getNome()));
		if (this.defensoria.getId() == null) {
			this.uRep.insert(this.defensoria);
			this.defensoria = new Defensoria();
			fc.addMessage(null, new FacesMessage(
					"Defensoria cadastrada com sucesso!"));
		} else {
			this.uRep.update(this.defensoria);
			fc.addMessage(null, new FacesMessage(
					"Defensoria Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas Defensorias
	 */
	public List<Defensoria> getSelecionarTudo() {
		return uRep.selectAll();
	}
	
	public void obterListaDefensorias(){
		this.defensorias = this.uRep.selectAll();
	}

	/**
	 * Exclui um objeto Defensoria
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.uRep.remove(this.defensoria);
		fc.addMessage(null,
				new FacesMessage("Defensoria exclu�da com sucesso!"));
	}

	/**
	 * Cria uma nova Defensoria
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.defensoria = new Defensoria();
		this.defensoria.setNucleos(new ArrayList<Nucleo>());
		fc.addMessage(null, new FacesMessage(
				"Uma nova Defensoria pode ser adicionada!"));
	}

	/**
	 * Prepara informa��es para a view
	 */
	public void normalizarInformacoes() {
		this.municipioDistritoBean.getMunicipioD().setNome(
				this.defensoria.getMunicipioDistrito() == null ? ""
						: this.defensoria.getMunicipioDistrito().getNome());
	}
	
	/**
	 * Adiciona um n�cleo
	 */
	public void adicionarNucleo(){
		if(this.defensoria.getNucleos() == null)
			this.defensoria.setNucleos(new ArrayList<Nucleo>());
		this.defensoria.getNucleos().add(this.nucleoBean.getNucleo());
	}
	
	public Defensoria obterDefensoriaPorId(Long id){
		return this.uRep.obterDefensoriaPorId(id);
	}
	
	/**
	 * Remove um n�cleo
	 */
	public void removerNucleo(){
		this.defensoria.getNucleos().remove(this.nucleoBean.getNucleo());
	}

	public MunicipioDistritoBean getMunicipioDistritoBean() {
		return municipioDistritoBean;
	}

	public void setMunicipioDistritoBean(
			MunicipioDistritoBean municipioDistritoBean) {
		this.municipioDistritoBean = municipioDistritoBean;
	}

	public Defensoria getDefensoria() {
		return defensoria;
	}

	public void setDefensoria(Defensoria defensoria) {
		this.defensoria = defensoria;
	}

	public List<Defensoria> getDefensorias() {
		return defensorias;
	}

	public void setDefensorias(List<Defensoria> defensorias) {
		this.defensorias = defensorias;
	}

	public NucleoBean getNucleoBean() {
		return nucleoBean;
	}

	public void setNucleoBean(NucleoBean nucleoBean) {
		this.nucleoBean = nucleoBean;
	}

}
