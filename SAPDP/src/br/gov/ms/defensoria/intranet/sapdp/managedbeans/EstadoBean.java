package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Estado;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.EstadoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class EstadoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Estado
	@EJB
	private EstadoRepositorio rRep;
	private Estado estado = new Estado();
	private List<Estado> estados;

	public EstadoBean() {

	}
	
	/**
	 * Insere/Altera o objeto Estado
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.estado.getId() == null) {
			this.rRep.insert(this.estado);
			this.estado = new Estado();
			fc.addMessage(null, new FacesMessage(
					"Estado cadastrado com sucesso!"));
		} else {
			this.rRep.update(this.estado);
			fc.addMessage(null, new FacesMessage(
					"Estado Alterado com sucesso!"));
		}
		this.estados = rRep.selectAll();
	}
	/**
	 * Busca todos estados
	 */
	public List<Estado> getSelecionarTudo() {
		return rRep.selectAll();
	}
	
	/**
	 * Exclui um objeto Estado
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.rRep.remove(this.estado);
		fc.addMessage(null, new FacesMessage("Estado excluído com sucesso!"));
	}
	
	/**
	 * Cria um novo estado
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.estado = new Estado();
		fc.addMessage(null, new FacesMessage("Um novo estado pode ser adicionado!"));
	}


	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}
