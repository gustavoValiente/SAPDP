package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NucleoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class NucleoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Nucleo
	@EJB
	private NucleoRepositorio pRep;
	private Nucleo nucleo = new Nucleo();
	private List<Nucleo> nucleos = new ArrayList<Nucleo>();
	private EntityLazyModel nucleoLazy;

	public NucleoBean() {

	}

	public EntityLazyModel getAllNucleo() {
		if (getNucleoLazy() == null) {
			setNucleoLazy(new EntityLazyModel(nucleo));
		}
		return getNucleoLazy();
	}

	/**
	 * Insere/Altera o objeto Nucleo
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.nucleo.getId() == null) {
			this.pRep.insert(this.nucleo);
			this.nucleo = new Nucleo();
			fc.addMessage(null, new FacesMessage(
					"Nucleo cadastrado com sucesso!"));
		} else {
			this.pRep.update(this.nucleo);
			fc.addMessage(null,
					new FacesMessage("Nucleo Alterado com sucesso!"));
		}
	}

	/**
	 * Busca todas nucleos
	 */
	public List<Nucleo> getSelecionarTudo() {
		return pRep.selectAll();
	}

	/**
	 * Busca todos nomes dos nucleos
	 */
	public List<String> getSelecionarTodosNomes() {
		return pRep.filtrarNucleosPorNome();
	}

	/**
	 * Exclui um objeto nucleo
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.pRep.remove(this.nucleo);
		fc.addMessage(null, new FacesMessage("Nucleo exclu�do com sucesso!"));
	}

	/**
	 * Cria um novo Nucleo
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.nucleo = new Nucleo();
		fc.addMessage(null, new FacesMessage(
				"Um novo Nucleo pode ser adicionado!"));
	}

	/**
	 * Lista os nomes dos nucleos que contem parte do parametro informado
	 * 
	 * @param query
	 *            - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarNucleosPorNome(String query) {
		return this.pRep.filtrarNucleosPorNome(query);
	}

	/**
	 * Obtem a lista de Nucleos disponiveis
	 * 
	 * @return List<Nucleo>
	 */
	public List<Nucleo> getObterListaDeNucleos() {
		return this.pRep.obterListaDeNucleos();
	}

	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * 
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						event.getObject().toString() + " SELECIONADO!", event
								.getObject().toString()));
		this.nucleo = this.pRep.buscarNucleoPorNome(event.getObject()
				.toString());
	}

	/**
	 * 
	 * @param nome
	 * @return Nucleo
	 */
	public Nucleo buscarNucleo(String nome) {
		return this.pRep.buscarNucleoPorNome(nome);
	}

	public Nucleo getNucleo() {
		return nucleo;
	}

	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}

	public List<Nucleo> getNucleos() {
		return nucleos;
	}

	public void setNucleos(List<Nucleo> nucleos) {
		this.nucleos = nucleos;
	}

	public EntityLazyModel getNucleoLazy() {
		return nucleoLazy;
	}

	public void setNucleoLazy(EntityLazyModel nucleoLazy) {
		this.nucleoLazy = nucleoLazy;
	}

}
