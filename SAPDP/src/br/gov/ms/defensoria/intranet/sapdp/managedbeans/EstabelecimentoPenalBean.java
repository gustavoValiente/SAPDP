package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;
import br.gov.ms.defensoria.intranet.sapdp.service.SegurancaService;

@ViewScoped
@ManagedBean
public class EstabelecimentoPenalBean {

	@EJB
	private SegurancaService service;

	private List<EstabelecimentoPenal> listEstabelecimentoPenal;

	private EstabelecimentoPenal entity;

	@PostConstruct
	private void initialize() {
		setEntity(new EstabelecimentoPenal());
		listEstabelecimentoPenal = null;
		getListEstabelecimentoPenal().addAll(service.listaTodos());		

	}

	public List<EstabelecimentoPenal> getListEstabelecimentoPenal() {
		if (listEstabelecimentoPenal == null) {
			listEstabelecimentoPenal = new ArrayList<EstabelecimentoPenal>();
		}
		return listEstabelecimentoPenal;
	}

	public void salvar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (getEntity().getId() == null) {
			setEntity(service.inserir(getEntity()));
			fc.addMessage(null, new FacesMessage(
					"Estabelecimento Penal cadastrada com sucesso!"));
		} else {
			entity = service.atualizar(getEntity());
			fc.addMessage(null, new FacesMessage(
					"Estabelecimento Penal Alterada com sucesso!"));
		}
		initialize();
	}

	public void setListEstabelecimentoPenal(
			List<EstabelecimentoPenal> listEstabelecimentoPenal) {
		this.listEstabelecimentoPenal = listEstabelecimentoPenal;
	}

	public void adicionarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		setEntity(new EstabelecimentoPenal());
		fc.addMessage(null, new FacesMessage(
				"Um novo Estabelecimento Penal pode ser adicionada!"));
	}

	public void remover() {
		FacesContext fc = FacesContext.getCurrentInstance();
		service.remover(getEntity().getId());
		fc.addMessage(null, new FacesMessage("Estabelecimento Penal excluída com sucesso!"));
		initialize();
	}

	public EstabelecimentoPenal getEntity() {
		return entity;
	}

	public void setEntity(EstabelecimentoPenal entity) {
		this.entity = entity;
	}

}
