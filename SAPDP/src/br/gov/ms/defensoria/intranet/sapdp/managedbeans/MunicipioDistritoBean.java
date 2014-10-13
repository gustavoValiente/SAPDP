package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.MunicipioDistritoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class MunicipioDistritoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito
	@EJB
	private MunicipioDistritoRepositorio mRep;
	private MunicipioDistrito municipioD = new MunicipioDistrito();
	private EntityLazyModel municipiosDistritosLazy;
	private List<MunicipioDistrito> municipioDs;
	private String nome;
	
	public MunicipioDistritoBean() {

	}
	
	
	public EntityLazyModel getAllMunicipioDistrito(){
		if(municipiosDistritosLazy == null){
			municipiosDistritosLazy = new EntityLazyModel(municipioD);
		}
		return municipiosDistritosLazy;
	}
	

	/**
	 * Insere/Altera o objeto MunicipioDistrito
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.municipioD.getId() == null) {
			this.mRep.insert(this.municipioD);
			this.municipioD = new MunicipioDistrito();
			fc.addMessage(null, new FacesMessage(
					"Municipio / Distrito cadastrado com sucesso!"));
		} else {
			this.mRep.update(this.municipioD);
			fc.addMessage(null, new FacesMessage(
					"Municipio / Distrito alterado com sucesso!"));
		}
	}

	/**
	 * Busca todos MunicipioDistritos
	 */
	public List<MunicipioDistrito> getSelecionarTudo() {
		return mRep.selectAll();
	}

	/**
	 * Exclui um objeto MunicipioDistrito
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.mRep.remove(this.municipioD);
		fc.addMessage(null, new FacesMessage(
				"Munic�pio / Distrito exclu�do com sucesso!"));
	}

	/**
	 * Cria um novo MunicipioDistrito
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.municipioD = new MunicipioDistrito();
		fc.addMessage(null, new FacesMessage(
				"Um novo municipio/distrito pode ser adicionado!"));
	}
	
	public MunicipioDistrito obterMunicipioPorId(Long id){
		return this.mRep.obterMunicipioPorId(id);
	}

	/**
	 * Lista os nomes dos municipiosDistritos que contem parte do parametro
	 * informado
	 * @param query - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarMunicipiosDistritos(String query) {
		return this.mRep.filtrarMunicipiosDistritosPorNome(query);
	}
	
	public List<String> filtrarMunicipiosDistritosPorEstado(String query){
		return this.mRep.filtrarMunicipiosDistritosPorEstado(query);
	}
	
	public MunicipioDistrito buscarMunicipioDistrito(String nome) {
		return this.mRep.buscarMunicipioDistritoPorNome(nome);
	}
	
	
	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(event.getObject().toString() + " SELECIONADO!",event.getObject().toString()));
        this.municipioD = this.mRep.buscarMunicipioDistritoPorNome(event.getObject().toString());
	}
	
	public MunicipioDistrito getMunicipioD() {
		return municipioD;
	}

	public void setMunicipioD(MunicipioDistrito municipioD) {
		this.municipioD = municipioD;
	}

	public List<MunicipioDistrito> getMunicipioDs() {
		return municipioDs;
	}

	public void setMunicipioDs(List<MunicipioDistrito> municipioDs) {
		this.municipioDs = municipioDs;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
