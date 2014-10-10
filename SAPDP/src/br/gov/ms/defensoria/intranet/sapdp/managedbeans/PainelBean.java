package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.TipoChamadoSenha;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.PainelRepositorio;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UnidadeRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class PainelBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel
	@EJB
	private PainelRepositorio pRep;
	@EJB
	private UnidadeRepositorio uRep;
	private Painel painel = new Painel();
	private List<Painel> paineis = new ArrayList<Painel>();
	private List<String> selectedNucleos = new ArrayList<String>();
	private Long idUnidade;

	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;

	@ManagedProperty(value = "#{unidadeBean}")
	private UnidadeBean unidadeBean;

	public PainelBean() {
		try {
			Map<String, String> mapa = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			System.out.println("MAPA >: " + mapa.get("codUnidade"));
			this.idUnidade = Long.parseLong(mapa.get("codUnidade"));
		} catch (Exception e) {

		}
	}

	/**
	 * Insere/Altera o objeto Painel
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.painel.setUnidade(this.uRep.obterUnidadePorId(this.idUnidade));
		if (this.painel.getId() == null) {
			this.pRep.insert(this.painel);
			criarNovo();
			fc.addMessage(null, new FacesMessage(
					"Painel cadastrado com sucesso!"));
		} else {
			this.painel = this.pRep.update(this.painel);
			fc.addMessage(null,
					new FacesMessage("Painel alterado com sucesso!"));
		}
	}

	/**
	 * Busca todos Paineis
	 */
	public List<Painel> getSelecionarTudo() {
		return pRep.selectAll();
	}

	/**
	 * ObterPaineisPorUnidade
	 * 
	 * @return List<Painel>
	 */
	public List<Painel> getObterPaineisPorUnidade() {
		return pRep.obterPaineisPorUnidade(idUnidade);
	}

	/**
	 * Exclui um objeto painel
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.pRep.remove(this.painel);
		fc.addMessage(null, new FacesMessage("Painel excluído com sucesso!"));
	}

	/**
	 * Cria um novo painel
	 */
	public void criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.painel = new Painel();
		this.selectedNucleos.clear();
		fc.addMessage(null, new FacesMessage(
				"Um novo painel pode ser adicionado!"));
	}

	/**
	 * 
	 * @return List<String>
	 */
	public List<TipoChamadoSenha> getObterTiposChamadosSenha() {
		return Arrays.asList(TipoChamadoSenha.values());
	}

	/**
	 * Método para tratar o evento de seleção SelectEvent
	 * 
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
		this.municipioDistritoBean.setMunicipioD(this.municipioDistritoBean
				.buscarMunicipioDistrito(event.getObject().toString()));
		this.unidadeBean.setUnidades(this.unidadeBean
				.obterUnidadesPorMunicipio(this.municipioDistritoBean
						.getMunicipioD().getId()));
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						event.getObject().toString() + " SELECIONADO!", event
								.getObject().toString()));
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(event.getObject().toString()
								+ " Unidades carregadas!", event.getObject()
								.toString()));
	}
	
	/**
	 * 
	 */
	public void normalizaInformacoes(){
		this.painel = this.pRep.obterPainelPorId(this.painel.getId());
	}

	/**
	 * Atualiza a lista de painéis
	 * 
	 * @param event
	 */
	public void refreshPaineis(AjaxBehaviorEvent event) {
		this.paineis = this.pRep.obterPaineisPorUnidade(this.unidadeBean
				.getUnidade().getId());
	}

	public String redirecionaPainelSenha() {
		System.out.println("+++++++ " + this.painel.getId());
		return "/admin/unidades/painel/showPainel.jsf?codPainel="
				+ this.painel.getId();
	}

	public Painel getPainel() {
		return painel;
	}

	public void setPainel(Painel painel) {
		this.painel = painel;
	}

	public List<Painel> getPaineis() {
		return paineis;
	}

	public void setPaineis(List<Painel> paineis) {
		this.paineis = paineis;
	}

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}

	public MunicipioDistritoBean getMunicipioDistritoBean() {
		return municipioDistritoBean;
	}

	public void setMunicipioDistritoBean(
			MunicipioDistritoBean municipioDistritoBean) {
		this.municipioDistritoBean = municipioDistritoBean;
	}

	public UnidadeBean getUnidadeBean() {
		return unidadeBean;
	}

	public void setUnidadeBean(UnidadeBean unidadeBean) {
		this.unidadeBean = unidadeBean;
	}

	public List<String> getSelectedNucleos() {
		return selectedNucleos;
	}

	public void setSelectedNucleos(List<String> selectedNucleos) {
		this.selectedNucleos = selectedNucleos;
	}

}