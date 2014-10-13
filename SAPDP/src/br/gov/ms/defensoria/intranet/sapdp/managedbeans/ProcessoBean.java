package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.model.Area;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Processo;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Regime;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Reu;
import br.gov.ms.defensoria.intranet.sapdp.service.SegurancaService;

@ViewScoped
@ManagedBean
public class ProcessoBean {

	@EJB
	private SegurancaService service;

	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;
	
	@ManagedProperty(value = "#{usuarioServiceBean}")
	private UsuarioServiceBean usuarioServiceBean;
	
	private List<Processo> listProcessos;

	private Processo processo;
	
	private Long idAssistido;

	@PostConstruct
	private void initialize() {
		setProcesso(new Processo());
		listProcessos = null;
		getListProcessos().addAll(service.listaTodosProcessos());		

	}

	public List<Processo> getListProcessos() {
		if (listProcessos == null) {
			listProcessos = new ArrayList<Processo>();
		}
		return listProcessos;
	}

	public void salvar() {
		System.out.println("ENTROU SALVAR PROCESSO"+getIdAssistido());
		FacesContext fc = FacesContext.getCurrentInstance();
		if (getProcesso().getId() == null) {
			getProcesso().setIdAssistido(getIdAssistido());
			System.out.println("ID ASSIS PROCESSO "+getProcesso().getIdAssistido());
			getProcesso().setDataCadastro(new Date());
			getProcesso().setUsuarioCadastro(this.usuarioServiceBean.obterUsuarioDaSessao().getLogin());
			setProcesso(service.inserir(getProcesso()));
			fc.addMessage(null, new FacesMessage(
					"Processo cadastrado com sucesso!"));
		} else {
			processo = service.atualizar(getProcesso());
			fc.addMessage(null, new FacesMessage(
					"Processo Alterado com sucesso!"));
		}
		initialize();
	}

	public void setListProcessos(
			List<Processo> listProcessos) {
		this.listProcessos = listProcessos;
	}

	public void adicionarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		setProcesso(new Processo());
		fc.addMessage(null, new FacesMessage(
				"Um novo Processo pode ser adicionado!"));
	}

	public void remover() {
		FacesContext fc = FacesContext.getCurrentInstance();
		service.remover(getProcesso().getId());
		fc.addMessage(null, new FacesMessage("Processo excluído com sucesso!"));
		initialize();
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	
	public List<Reu> getObterTipoReu() {
		return Arrays.asList(Reu.values());
	}
	
	public List<Regime> getObterTipoRegime() {
		return Arrays.asList(Regime.values());
	}
	
	public List<Area> getObterTipoVara() {
		return Arrays.asList(Area.values());
	}

	public MunicipioDistritoBean getMunicipioDistritoBean() {
		return municipioDistritoBean;
	}

	public void setMunicipioDistritoBean(MunicipioDistritoBean municipioDistritoBean) {
		this.municipioDistritoBean = municipioDistritoBean;
	}

	public Long getIdAssistido() {
		return idAssistido;
	}

	public void setIdAssistido(Long idAssistido) {
		this.idAssistido = idAssistido;
	}

	public UsuarioServiceBean getUsuarioServiceBean() {
		return usuarioServiceBean;
	}

	public void setUsuarioServiceBean(UsuarioServiceBean usuarioServiceBean) {
		this.usuarioServiceBean = usuarioServiceBean;
	}
	
}
