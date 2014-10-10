package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Regional;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.RegionalRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class RegionalBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Regional
	@EJB
	private RegionalRepositorio rRep;
	private Regional regional = new Regional();
	private List<Regional> regionais;

	public RegionalBean() {

	}
	
	/**
	 * Insere/Altera o objeto Regional
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.regional.getId() == null) {
			this.rRep.insert(this.regional);
			this.regional = new Regional();
			fc.addMessage(null, new FacesMessage(
					"Regional cadastrada com sucesso!"));
		} else {
			this.rRep.update(this.regional);
			fc.addMessage(null, new FacesMessage(
					"Regional Alterada com sucesso!"));
		}
		this.regionais = rRep.selectAll();
	}
	/**
	 * Busca todas regionais
	 */
	public List<Regional> getSelecionarTudo() {
		return rRep.selectAll();
	}
	
	/**
	 * Exclui um objeto Regional
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.rRep.remove(this.regional);
		fc.addMessage(null, new FacesMessage("Regional excluída com sucesso!"));
	}
	
	/**
	 * Cria uma nova regional
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.regional = new Regional();
		fc.addMessage(null, new FacesMessage("Uma nova regional pode ser adicionada!"));
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public List<Regional> getRegionais() {
		return regionais;
	}

	public void setRegionais(List<Regional> regionais) {
		this.regionais = regionais;
	}

}
