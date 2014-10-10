package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Bairro;
import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.BairroRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class BairroBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Bairro
	@EJB
	private BairroRepositorio mRep;
	private Bairro bairro = new Bairro();
	private EntityLazyModel bairrosLazy;
	private List<Bairro> bairros;
	private MunicipioDistrito testeM;

	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;

	public BairroBean() {

	}

	public EntityLazyModel getAllBairro() {
		Map<String, String> mapa = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		System.out.println("MAPA >: " + mapa.get("codMunicipio"));
		System.out.println("MAPA >: " + mapa.get("nomeMunicipio"));
		if (bairrosLazy == null) {
			bairrosLazy = new EntityLazyModel(bairro, "municipio.id",
					Long.parseLong(mapa.get("codMunicipio")));
		}
		return bairrosLazy;
	}

	/**
	 * Insere/Altera o objeto Bairro
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("nomeMunicipio"));
		this.bairro.setMunicipio(this.municipioDistritoBean
				.buscarMunicipioDistrito(FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap()
						.get("nomeMunicipio")));

		if (this.bairro.getId() == null) {
			this.mRep.insert(this.bairro);
			this.bairro = new Bairro();
			fc.addMessage(null, new FacesMessage(
					"Bairro cadastrado com sucesso!"));
		} else {
			this.mRep.update(this.bairro);
			fc.addMessage(null,
					new FacesMessage("Bairro alterado com sucesso!"));
		}
	}

	public Bairro insert2(String nomeBairro) {
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println(FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("nomeMunicipio"));
		this.bairro.setMunicipio(this.municipioDistritoBean
				.buscarMunicipioDistrito(this.municipioDistritoBean
						.getMunicipioD().getNome()));
		this.bairro.setNome(nomeBairro);
		if (this.bairro.getId() == null) {
			this.mRep.insert(this.bairro);
			fc.addMessage(null, new FacesMessage(
					"Bairro cadastrado com sucesso!"));
			return this.bairro;
		}
		return null;
	}

	/**
	 * Busca todos Bairros
	 */
	public List<Bairro> getSelecionarTudo() {
		return mRep.selectAll();
	}

	/**
	 * Exclui um objeto Bairro
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.mRep.remove(this.bairro);
		fc.addMessage(null, new FacesMessage("Bairro excluído com sucesso!"));
	}

	/**
	 * Cria um novo Bairro
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.bairro = new Bairro();
		fc.addMessage(null, new FacesMessage(
				"Um novo bairro pode ser adicionado!"));
	}

	/**
	 * Lista os nomes dos bairros que contem parte do parametro informado
	 * 
	 * @param query
	 *            - parametro informado pelo usuário
	 * @return List<String>
	 */
	public List<String> filtrarBairros(String query) {
		return this.mRep.filtrarBairrosPorNome(query);
	}

	/**
	 * Lista os nomes dos bairros e seus municípios que contem parte do
	 * parametro informado
	 * 
	 * @param query
	 *            - parametro informado pelo usuário
	 * @return List<String>
	 */
	public List<String> filtrarBairrosMunicipios(String query) {
		return this.mRep.filtrarBairrosMunicipiosPorNome(query);
	}

	public Bairro buscarBairro(String nome) {
		return this.mRep.buscarBairroPorNome(nome);
	}

	public Bairro buscarBairroMunicipio(String bairro, String municipio) {
		return this.mRep.buscarBairroPorNomeEMunicipio(bairro, municipio);
	}

	/**
	 * Método para tratar o evento de seleção SelectEvent
	 * 
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						event.getObject().toString() + " SELECIONADO!", event
								.getObject().toString()));
		this.bairro = this.mRep.buscarBairroPorNome(event.getObject()
				.toString());
	}

	/**
	 * Método para tratar o evento de seleção SelectEvent cujo event retorna
	 * string Bairro - Municipio
	 * 
	 * @param event
	 */
	public void onItemSelectBairroMunicipio(SelectEvent event) {
		String aux[] = event.getObject().toString().split("-");
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						event.getObject().toString() + " SELECIONADO!", event
								.getObject().toString()));
		System.out.println("XXXXXXXXXXXXXXX" + aux[0].trim() + "__"
				+ aux[1].trim());
		this.bairro = this.mRep.buscarBairroPorNomeEMunicipio(aux[0].trim(),
				aux[1].trim());
	}

	public void selecionarNomeDoMunicipio() {
		this.municipioDistritoBean.getMunicipioD().setNome(
				this.bairro.getMunicipio() == null ? "" : this.bairro
						.getMunicipio().getNome());
	}

	public void atribuiMunicipio(Long id) {
		this.testeM.setId(id);
	}

	public EntityLazyModel getBairrosLazy() {
		return bairrosLazy;
	}

	public void setBairrosLazy(EntityLazyModel bairrosLazy) {
		this.bairrosLazy = bairrosLazy;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public List<Bairro> getBairros() {
		return bairros;
	}

	public void setBairros(List<Bairro> bairros) {
		this.bairros = bairros;
	}

	public MunicipioDistritoBean getMunicipioDistritoBean() {
		return municipioDistritoBean;
	}

	public void setMunicipioDistritoBean(
			MunicipioDistritoBean municipioDistritoBean) {
		this.municipioDistritoBean = municipioDistritoBean;
	}

}
