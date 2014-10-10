package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.lazymodels.ItemLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ItemRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class ItemBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.ItemAtendimento
	@EJB
	private ItemRepositorio iRep;
	private ItemAtendimento item = new ItemAtendimento();
	private List<ItemAtendimento> itens = new ArrayList<ItemAtendimento>();

	@ManagedProperty(value = "#{nucleoBean}")
	private NucleoBean nucleoBean;

	private EntityLazyModel itemLazy;

	public ItemBean() {

	}

	public EntityLazyModel getAllItens() {
		if (itemLazy == null) {
			itemLazy = new EntityLazyModel(item);
		}
		return itemLazy;
	}

	/**
	 * Insere/Altera o objeto ItemAtendimento
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		//this.item.setNucleos(this.nucleoBean.getNucleos());
		
		if (this.item.getId() == null) {
			this.iRep.insert(this.item);
			this.item = new ItemAtendimento();
			fc.addMessage(null, new FacesMessage(
					"Item Atendimento cadastrada com sucesso!"));
		} else {
			this.iRep.update(this.item);			
			fc.addMessage(null, new FacesMessage(
					"Item Atendimento Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas ItemAtendimentos
	 */
	public List<ItemAtendimento> getSelecionarTudo() {
		return iRep.selectAll();
	}

	/**
	 * Exclui um objeto ItemAtendimento
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.iRep.remove(this.item);
		fc.addMessage(null,
				new FacesMessage("Item exclu�do com sucesso!"));
	}

	/**
	 * Cria uma nova ItemAtendimento
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.item = new ItemAtendimento();
		this.item.setNucleos(new ArrayList<Nucleo>());
		fc.addMessage(null, new FacesMessage(
				"Umo novo Item pode ser adicionada!"));
	}

	/**
	 * Adiciona um n�cleo
	 */
	public void adicionarNucleo(){
		if(this.item.getNucleos() == null)
			this.item.setNucleos(new ArrayList<Nucleo>());
		this.item.getNucleos().add(this.nucleoBean.getNucleo());
		this.nucleoBean.setNucleo(new Nucleo());
	}
	
	/**
	 * Remove um n�cleo
	 */
	public void removerNucleo(){
		this.item.getNucleos().remove(this.nucleoBean.getNucleo());
	}
	
	/**
	 * Carrega n�cleos
	 */
	public void carregaNucleos(){
		this.item.setNucleos(this.getObterListaDeNucleosPorItem());
	}
	
	/**
	 * Obtem lista de itens
	 * @return List<ItemAtendimento>
	 */
	public List<ItemAtendimento> getObterListaDeItens(){
		return this.iRep.obterListaDeItens();
	}
	
	/**
	 * Obtem lista de n�cleos por item	
	 * @return List<Nucleo>
	 */
	public List<Nucleo> getObterListaDeNucleosPorItem(){
		return this.iRep.obterListaDeNucleosPorItem(this.item.getId());
	}
	
	/**
	 * Lista os nomes dos itens que contem parte do parametro
	 * informado
	 * @param query - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarItensPorNome(String query) {
		return this.iRep.filtrarItensPorNome(query);
	}
	
	/**
	 * Obtem lista de itens por n�cleo	
	 * @return List<ItemAtendimento>
	 */
	public List<ItemAtendimento> obterListaDeItensPorNucleo(Long idNucleo){
		if(this.itens.isEmpty()){
			this.itens = new ArrayList<ItemAtendimento>();
		}
		this.itens = this.iRep.obterListaDeItensPorNucleo(idNucleo);
		return this.itens;
	}
	
	

	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(event.getObject().toString() + " SELECIONADO!",event.getObject().toString()));
        this.item = this.iRep.buscarItemPorNome(event.getObject().toString());
	}
	
	public NucleoBean getNucleoBean() {
		return nucleoBean;
	}

	public void setNucleoBean(NucleoBean nucleoBean) {
		this.nucleoBean = nucleoBean;
	}

	public ItemAtendimento getItem() {
		return item;
	}

	public void setItem(ItemAtendimento item) {
		this.item = item;
	}

	public List<ItemAtendimento> getItens() {
		return itens;
	}

	public void setItens(List<ItemAtendimento> itens) {
		this.itens = itens;
	}

	public EntityLazyModel getItemLazy() {
		return itemLazy;
	}

	public void setItemLazy(EntityLazyModel itemLazy) {
		this.itemLazy = itemLazy;
	}

}
