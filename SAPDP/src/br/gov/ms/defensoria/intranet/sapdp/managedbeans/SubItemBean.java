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
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.SubItemRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class SubItemBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.SubItemAtendimento
	@EJB
	private SubItemRepositorio sRep;
	private SubItemAtendimento subitem = new SubItemAtendimento();
	private List<SubItemAtendimento> subitens = new ArrayList<SubItemAtendimento>();

	@ManagedProperty(value = "#{itemBean}")
	private ItemBean itemBean;

	private EntityLazyModel subItemLazy;

	public SubItemBean() {

	}

	public EntityLazyModel getAllSubItens() {
		if (subItemLazy == null) {
			subItemLazy = new EntityLazyModel(subitem);
		}
		return subItemLazy;
	}

	/**
	 * Insere/Altera o objeto SubItemAtendimento
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		//this.subitem.setNucleos(this.nucleoBean.getNucleos());
		
		if (this.subitem.getId() == null) {
			this.sRep.insert(this.subitem);
			this.subitem = new SubItemAtendimento();
			fc.addMessage(null, new FacesMessage(
					"SubItem cadastrada com sucesso!"));
		} else {
			this.sRep.update(this.subitem);			
			fc.addMessage(null, new FacesMessage(
					"SubItem Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas SubItemAtendimentos
	 */
	public List<SubItemAtendimento> getSelecionarTudo() {
		return sRep.selectAll();
	}

	/**
	 * Exclui um objeto SubItemAtendimento
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.sRep.remove(this.subitem);
		fc.addMessage(null,
				new FacesMessage("SubItem exclu�do com sucesso!"));
	}

	/**
	 * Cria uma nova SubItemAtendimento
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.subitem = new SubItemAtendimento();
		this.subitem.setItens(new ArrayList<ItemAtendimento>());
		fc.addMessage(null, new FacesMessage(
				"Um novo SubItem pode ser adicionada!"));
	}

	/**
	 * Adiciona um item
	 */
	public void adicionarItem(){
		if(this.subitem.getItens() == null)
			this.subitem.setItens(new ArrayList<ItemAtendimento>());
		this.subitem.getItens().add(this.itemBean.getItem());
		this.itemBean.setItem(new ItemAtendimento());
	}
	
	/**
	 * Remove um item
	 */
	public void removerItem(){
		this.subitem.getItens().remove(this.itemBean.getItem());
	}
	
	/**
	 * Carrega itens
	 */
	public void carregaItens(){
		this.subitem.setItens(this.getObterListaDeItensPorSubItem());
	}
	
	/**
	 * Obtem lista de n�cleos por item	
	 * @return List<Nucleo>
	 */
	public List<ItemAtendimento> getObterListaDeItensPorSubItem(){
		return this.sRep.obterListaDeItensPorSubItem(this.subitem.getId());
	}
	
	/**
	 * Obtem lista de subitens por item
	 * @return List<SubItemAtendimento>
	 */
	public List<SubItemAtendimento> obterListaDeSubItensPorItem(Long idItem){
		if(this.subitens.isEmpty()){
			this.subitens = new ArrayList<SubItemAtendimento>();
		}
		this.subitens = this.sRep.obterListaDeSubItensPorItem(idItem);
		return this.subitens;
	}
	

	public SubItemAtendimento getSubitem() {
		return subitem;
	}

	public void setSubitem(SubItemAtendimento subitem) {
		this.subitem = subitem;
	}

	public List<SubItemAtendimento> getSubitens() {
		return subitens;
	}

	public void setSubitens(List<SubItemAtendimento> subitens) {
		this.subitens = subitens;
	}

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

	public EntityLazyModel getSubItemLazy() {
		return subItemLazy;
	}

	public void setSubItemLazy(EntityLazyModel subItemLazy) {
		this.subItemLazy = subItemLazy;
	}
	
	
}
