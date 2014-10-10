package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Nacionalidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NacionalidadeRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class NacionalidadeBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Profissao
	@EJB
	private NacionalidadeRepositorio pRep;
	private Nacionalidade nacionalidade = new Nacionalidade();
	private EntityLazyModel nacionalidadeLazy;

	public NacionalidadeBean() {

	}

	public EntityLazyModel getAllNacionalidade() {
		if (nacionalidadeLazy == null) {
			nacionalidadeLazy = new EntityLazyModel(nacionalidade);
		}
		return nacionalidadeLazy;
	}

	/**
	 * Insere/Altera o objeto Nacionalidade
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.nacionalidade.getId() == null) {
			this.pRep.insert(this.nacionalidade);
			this.nacionalidade = new Nacionalidade();
			fc.addMessage(null, new FacesMessage(
					"Nacionalidade cadastrada com sucesso!"));
		} else {
			this.pRep.update(this.nacionalidade);
			fc.addMessage(null, new FacesMessage(
					"Nacionalidade Alterada com sucesso!"));
		}
	}

	/**
	 * Busca todas Nacionalidades
	 */
	public List<Nacionalidade> getSelecionarTudo() {
		return pRep.selectAll();
	}

	/**
	 * Exclui um objeto Nacionalidade
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.pRep.remove(this.nacionalidade);
		fc.addMessage(null, new FacesMessage(
				"Nacionalidade exclu�da com sucesso!"));
	}

	/**
	 * Cria uma nova Nacionalidade
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.nacionalidade = new Nacionalidade();
		fc.addMessage(null, new FacesMessage(
				"Uma nova Nacionalidade pode ser adicionada!"));
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

}
