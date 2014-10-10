package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.datacontract.schemas._2004._07.SGEN_ModelData_Logic_SGENContext_Model.MEndereco;
import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Endereco;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ConsultaCEPRepositorio;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.EnderecoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class EnderecoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Endereco
	@EJB
	private EnderecoRepositorio eRep;
	private Endereco endereco = new Endereco();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private EntityLazyModel enderecosLazy;
	private String nomeBairroTemp = null;

	@EJB
	private ConsultaCEPRepositorio cepRep;

	@ManagedProperty(value = "#{bairroBean}")
	private BairroBean bairroBean;

	public EnderecoBean() {

	}

	public EntityLazyModel getAllEndereco() {
		if (enderecosLazy == null) {
			enderecosLazy = new EntityLazyModel(endereco);
		}
		return enderecosLazy;
	}

	/**
	 * Insere/Altera o objeto Endereco
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();

		this.endereco.setBairro(this.bairroBean.buscarBairro(this.bairroBean
				.getBairro().getNome()));

		if (this.endereco.getId() == null) {
			this.eRep.update(this.endereco);
			this.endereco = new Endereco();
			fc.addMessage(null, new FacesMessage(
					"Endereço cadastrado com sucesso!"));
		} else {
			this.eRep.update(this.endereco);
			fc.addMessage(null, new FacesMessage(
					"Endereço alterado com sucesso!"));
		}
	}

	/**
	 * Busca todas Enderecos
	 */
	public List<Endereco> getSelecionarTudo() {
		return eRep.selectAll();
	}

	/**
	 * Exclui um objeto Endereco
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.eRep.remove(this.endereco);
		fc.addMessage(null, new FacesMessage("Endereço exclu�do com sucesso!"));
	}

	/**
	 * Cria uma nova Endereco
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.bairroBean.getBairro().setNome("");
		this.endereco = new Endereco();
		fc.addMessage(null, new FacesMessage(
				"Um novo Endereço pode ser adicionado!"));
	}

	/**
	 * Lista os nomes das profiss�es que contem parte do parametro informado
	 * 
	 * @param query
	 *            - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarEnderecos(String query) {
		return this.eRep.filtrarEnderecosPorNome(query);
	}

	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * 
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
						event.getObject().toString() + " SELECIONADO!", event
								.getObject().toString()));
		this.endereco = this.eRep.obterEnderecoPorNome(event.getObject()
				.toString());
	}

	public void selecionarNomeDoBairro() {
		System.out.println("XXXXXXXXXXXXXX" + this.endereco.getId());
		this.bairroBean.getBairro().setNome(
				this.endereco.getBairro() == null ? "" : this.endereco
						.getBairro().getNome());
		System.out.println("YYYYYYYYYYYYYYYYYYYYYY"
				+ this.endereco.getBairro().getNome());
		System.out.println("PPPPPPPPPPPPPPPPPPPPP" + this.nomeBairroTemp);
	}

	public void buscaCep() throws RemoteException {
		this.nomeBairroTemp = null;
		String cep = this.endereco.getCep().replace("-", "");

		this.cepRep.consultarCEP(cep);

		try {
			if (this.cepRep.retornaTamanho() > 0) {
				java.util.List<MEndereco> listreturnp14 = java.util.Arrays
						.asList(this.cepRep.getServiceCep());
				System.out.println("CEPPPPPPPPPPPPPPPPPP"
						+ this.endereco.getCep());
				System.out.println("XXXXXXXXXXXXXXXXXXXX"
						+ listreturnp14.get(0).getBairro());

				if (this.bairroBean.buscarBairroMunicipio(listreturnp14.get(0)
						.getBairro(), listreturnp14.get(0).getLocalidade()) != null) {
					this.endereco.setBairro(this.bairroBean
							.buscarBairroMunicipio(listreturnp14.get(0)
									.getBairro(), listreturnp14.get(0)
									.getLocalidade()));
					selecionarNomeDoBairro();
				} else {
					this.bairroBean.getBairro().setNome("");
					this.nomeBairroTemp = listreturnp14.get(0).getBairro();
					this.endereco.setBairro(null);
				}
				this.endereco.setRua(listreturnp14.get(0).getTipoLogradouro()
						+ " " + listreturnp14.get(0).getLogradouro());
			} else {
				criarNova();
				this.bairroBean.getBairro().setNome("");
				this.endereco.setCep(cep);
				this.nomeBairroTemp = "00000000";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("PPPPPPPPPPPPPPPPPPPP" + this.nomeBairroTemp);

	}

	public void insert2() {
		String nBairro = (this.nomeBairroTemp == null || this.nomeBairroTemp == "00000000")
				? this.bairroBean.getBairro().getNome()
				: this.nomeBairroTemp;
		this.bairroBean.setBairro(this.bairroBean.insert2(nBairro));
		if (this.bairroBean.getBairro() != null) {
			this.endereco.setBairro(this.bairroBean.getBairro());
			System.out.println("ENTROUUUUUUUUUUUUUUUUUUUUUUUUU"
					+ this.bairroBean.getBairro().getNome());
			selecionarNomeDoBairro();
		}
	}

	public Endereco buscarEndereco(String nome) {
		return this.eRep.obterEnderecoPorNome(nome);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public BairroBean getBairroBean() {
		return bairroBean;
	}

	public void setBairroBean(BairroBean bairroBean) {
		this.bairroBean = bairroBean;
	}

	public EntityLazyModel getEnderecosLazy() {
		return enderecosLazy;
	}

	public void setEnderecosLazy(EntityLazyModel enderecosLazy) {
		this.enderecosLazy = enderecosLazy;
	}

	public String getNomeBairroTemp() {
		return nomeBairroTemp;
	}

	public void setNomeBairroTemp(String nomeBairroTemp) {
		this.nomeBairroTemp = nomeBairroTemp;
	}

	/*
	 * public static void main(String[] args) throws RemoteException {
	 * 
	 * org.datacontract.schemas._2004._07.SGEN_ModelData_Logic_SGENContext_Model.
	 * MEndereco[] buscarEnderecoPorFiltrosDiversos13mtemp = new
	 * IEnderecoServiceProxy() .buscarEnderecoPorFiltrosDiversos("79043050");
	 * java.util.List<MEndereco> listreturnp14 = java.util.Arrays
	 * .asList(buscarEnderecoPorFiltrosDiversos13mtemp);
	 * System.out.println(listreturnp14.get(0).getBairro());
	 * 
	 * }
	 */
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

}
