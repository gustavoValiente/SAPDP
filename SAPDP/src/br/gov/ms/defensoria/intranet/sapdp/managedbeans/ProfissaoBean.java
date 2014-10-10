package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Profissao;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ProfissaoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class ProfissaoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Profissao
	@EJB
	private ProfissaoRepositorio pRep;
	private Profissao profissao = new Profissao();
	private EntityLazyModel profissoesLazy;

	public ProfissaoBean() {

	}

	public EntityLazyModel getAllProfissao() {
		if (getProfissoesLazy() == null) {
			setProfissoesLazy(new EntityLazyModel(profissao));
		}
		return getProfissoesLazy();
	}

	/**
	 * Insere/Altera o objeto Profissao
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.profissao.getId() == null) {
			this.pRep.insert(this.profissao);
			this.profissao = new Profissao();
			fc.addMessage(null, new FacesMessage(
					"Profiss�o cadastrada com sucesso!"));
		} else {
			this.pRep.update(this.profissao);
			fc.addMessage(null, new FacesMessage(
					"Profiss�o Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas Profissoes
	 */
	public List<Profissao> getSelecionarTudo() {
		return pRep.selectAll();
	}

	/**
	 * Exclui um objeto Profissao
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.pRep.remove(this.profissao);
		fc.addMessage(null, new FacesMessage("Profiss�o exclu�da com sucesso!"));
	}

	/**
	 * Cria uma nova Profissao
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.profissao = new Profissao();
		fc.addMessage(null, new FacesMessage(
				"Uma nova profiss�o pode ser adicionada!"));
	}
	
	/**
	 * Lista os nomes das profiss�es que contem parte do parametro
	 * informado
	 * @param query - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarProfissoes(String query) {
		return this.pRep.filtrarProfissoesPorNome(query);
	}
	
	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(event.getObject().toString() + " SELECIONADO!",event.getObject().toString()));
        this.profissao = this.pRep.obterProfissaoPorNome(event.getObject().toString());
	}
	
	
	public Profissao buscarProfissao(String nome) {
		return this.pRep.obterProfissaoPorNome(nome);
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public EntityLazyModel getProfissoesLazy() {
		return profissoesLazy;
	}

	public void setProfissoesLazy(EntityLazyModel profissoesLazy) {
		this.profissoesLazy = profissoesLazy;
	}
}
