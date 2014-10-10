package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.AssistidoSigoLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Endereco;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.AssistidoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.EstadoCivil;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.OrgaoExpedidor;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Raca;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Sexo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.CondutaCarceraria;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.service.SegurancaService;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class AssistidoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido
	@EJB
	private SegurancaService service;
		
	private Assistido assistido = new Assistido();
	private EntityLazyModel assistidosLazy;
	private List<Assistido> assistidos;

	/* ***************** TESTE ASSISTIDO SIGO ***************** */
	private AssistidoSigo assistidoSigo = new AssistidoSigo();
	private LazyDataModel<AssistidoSigo> assistidosSigoLazy;
	private AssistidoSigo selecionaAssistidoSigo = new AssistidoSigo();

	/* ***************** ASSISTIDO PRESO ********************* */
	private Assistido selecionadoPreso = new Assistido();
	private List<Assistido> assistidosPreso = new ArrayList<Assistido>();
	private AssistidoPreso dadosPreso = new AssistidoPreso();

	@ManagedProperty(value = "#{profissaoBean}")
	private ProfissaoBean profissaoBean;

	@ManagedProperty(value = "#{usuarioServiceBean}")
	private UsuarioServiceBean usuarioServiceBean;

	@ManagedProperty(value = "#{enderecoBean}")
	private EnderecoBean enderecoBean;

	public AssistidoBean() {

	}

	@PostConstruct
	public void init() {
		try {
			Map<String, String> mapa = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			this.assistido = this.service.obterAssistidoPorId(Long.valueOf(mapa
					.get("codAssistido")));
			selecionarNomeDaProfissao();
		} catch (Exception e) {
			System.out.println("Nenhum assistido selecionado!");
		}
	}

	public EntityLazyModel getAllAssistido() {
		if (getAssistidosLazy() == null) {
			setAssistidosLazy(new EntityLazyModel(assistido));
		}
		return getAssistidosLazy();
	}

	/**
	 * Insere/Altera o objeto Assistido
	 */
	public void insert() {
		System.out.println(">>>>>>>> ASSIS" + this.assistido.getNome());
		FacesContext fc = FacesContext.getCurrentInstance();
		this.assistido.setProfissao(this.profissaoBean
				.buscarProfissao(this.profissaoBean.getProfissao().getNome()));
		if (this.assistido.getId() == null) {
			this.assistido.setDataCadastro(new Date());
			this.assistido.setUsuarioCadastro(this.usuarioServiceBean
					.obterUsuarioDaSessao().getLogin());
			this.assistido = this.service.inserir(this.assistido);
			this.assistido = new Assistido();
			fc.addMessage(null, new FacesMessage(
					"Assistido cadastrado com sucesso!"));
		} else {
			this.assistido.setDataAlteracao(new Date());
			this.assistido.setUsuarioAlteracao(this.usuarioServiceBean
					.obterUsuarioDaSessao().getLogin());
			this.service.atualizar(this.assistido);
			fc.addMessage(null, new FacesMessage(
					"Assistido Alterado com sucesso!"));
		}
	}

	public String direcionaLink() {

		return "/admin/assistido/designacoes.jsf?codAssistido="
				+ this.assistido.getId();

	}

	/**
	 * Busca todos assistidos
	 */
	public List<Assistido> getSelecionarTudo() {
		return this.service.listaTodosAssistidos();
	}

	/**
	 * Exclui um objeto Assistido
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.service.removerAssistido(this.assistido.getId());
		fc.addMessage(null, new FacesMessage("Assistido excluído com sucesso!"));
	}

	/**
	 * Cria um novo Assistido
	 */
	public String criarNovo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.profissaoBean.getProfissao().setNome("");
		this.assistido = new Assistido();
		fc.addMessage(null, new FacesMessage(
				"Um novo assistido pode ser adicionado!"));
		if (this.usuarioServiceBean.obterUsuarioDaSessao().getGrupo().get(0) == Grupo.ADMINISTRADOR
				|| this.usuarioServiceBean.obterUsuarioDaSessao().getGrupo()
						.get(0) == Grupo.MASTER)
			return "/admin/assistido/assistido.jsf";
		else
			return "/atendente/assistido.jsf";
	}

	/**
	 * Array List do Estado Civil
	 */
	public List<EstadoCivil> getObterTodosEstadosCivis() {
		return Arrays.asList(EstadoCivil.values());
	}

	/**
	 * Array List do Ra�a
	 */
	public List<Raca> getObterTodasRacas() {
		return Arrays.asList(Raca.values());
	}

	/**
	 * Array List do Sexo
	 */
	public List<Sexo> getObterTodosSexos() {
		return Arrays.asList(Sexo.values());
	}

	/**
	 * Array List do Orgao Expedidor
	 */
	public List<OrgaoExpedidor> getObterTodosOrgaosExpedidores() {
		return Arrays.asList(OrgaoExpedidor.values());
	}

	/**
	 * Adiciona um Endere�o ao assistido
	 */
	public void adicionarEndereco() {
		if (this.assistido.getEnderecos() == null)
			this.assistido.setEnderecos(new ArrayList<Endereco>());
		// this.enderecoBean.getEndereco().setBairro(this.enderecoBean.getBairroBean().getBairro());
		this.assistido.getEnderecos().add(this.enderecoBean.getEndereco());
		this.enderecoBean.setEndereco(new Endereco());
		this.service.atualizar(this.assistido);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Endere�o adicionado!"));
	}

	/**
	 * Remove um Endere�o ao assistido
	 */
	public void removerEndereco() {
		this.assistido.getEnderecos().remove(this.enderecoBean.getEndereco());
		this.service.atualizar(this.assistido);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Endere�o removido!"));
	}

	public void selecionarNomeDaProfissao() {
		this.profissaoBean.getProfissao().setNome(
				this.assistido.getProfissao() == null ? "" : this.assistido
						.getProfissao().getNome());
	}

	public void filtrarWebServiceSIGO() {

		this.assistidosSigoLazy = new AssistidoSigoLazyModel(
				this.assistidoSigo.getNome(), this.assistidoSigo.getCpf(),
				this.assistidoSigo.getRg(), this.assistidoSigo.getMae(),
				this.assistidoSigo.getPai());
	}

	public void verificaExisteAssistidoBase() {

		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
				+ this.selecionaAssistidoSigo.getNome());
		System.out.println("######################"+this.selecionaAssistidoSigo.getNome());
		System.out.println("######################"+this.selecionaAssistidoSigo.getCpf());
		System.out.println("######################"+this.selecionaAssistidoSigo.getRg());
		System.out.println("######################"+this.selecionaAssistidoSigo.getMae());
		System.out.println("######################"+this.selecionaAssistidoSigo.getPai());
		
		this.assistidosPreso = this.service.buscaAssistidoSIGOExisteBase(
				this.selecionaAssistidoSigo.getNome(),
				this.selecionaAssistidoSigo.getCpf(),
				this.selecionaAssistidoSigo.getRg(),
				this.selecionaAssistidoSigo.getMae(),
				this.selecionaAssistidoSigo.getPai());

		if (!this.assistidosPreso.isEmpty()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Clique em SELECIONE para adicion�-lo a lista de espera."));

			RequestContext.getCurrentInstance().execute(
					"PF('selectAssistidoPreso').show()");
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"Assistido não foi encontrado na base da Defensoria!"));
			RequestContext.getCurrentInstance().execute(
					"PF('botaoConfirma').jq.click()");
		}

	}

	/* *****************************  ASSISTIDO Já EXISTE NA BASE DA DPGE ************************************** */
	public void selecionaAssistidoFromDialog() {
		
		//System.out.println("SELECIONADO ASSISTIDO"+this.assistidoPresoBean.getSelecionadoPreso().getId()+
		//		" ---- "+this.assistidoPresoBean.getSelecionadoPreso().getNome());
				
		/* *********************** SALVANDO ESTABELECIMENTO PENAL ****************************/
		System.out.println("XZXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+this.selecionadoPreso);
		this.dadosPreso.setAssistido(this.selecionadoPreso);
		if(this.selecionaAssistidoSigo.getLocalPrisao() != "" && this.selecionaAssistidoSigo.getLocalPrisao() != null){
			EstabelecimentoPenal estabelecimento = this.service.obterEstabelecimentoPorNome(this.selecionaAssistidoSigo.getLocalPrisao());
			if(estabelecimento != null)
				this.dadosPreso.setEstabelecimento(estabelecimento);
			else{
				estabelecimento = new EstabelecimentoPenal();
				estabelecimento.setNome(this.selecionaAssistidoSigo.getLocalPrisao());
				estabelecimento = this.service.inserir(estabelecimento);
				this.dadosPreso.setEstabelecimento(estabelecimento);
			}			
		}
		
		if(this.selecionaAssistidoSigo.getCondutaCarceraria() != "" && this.selecionaAssistidoSigo.getCondutaCarceraria() != null)
			this.dadosPreso.setCondutaCarceraria(obterCondutaCarceraria());
		
		AssistidoPreso aPreso = this.service.obterAssistidoPreso(this.dadosPreso.getAssistido().getId()); 
		if(aPreso != null){
			aPreso.setAssistido(this.dadosPreso.getAssistido());
			aPreso.setCondutaCarceraria(this.dadosPreso.getCondutaCarceraria());
			aPreso.setEstabelecimento(this.dadosPreso.getEstabelecimento());			
			this.dadosPreso = this.service.atualizar(aPreso);
		}else{
			this.dadosPreso = this.service.atualizar(this.dadosPreso);
		}
				
		carregarDadosRemicao(this.dadosPreso.getAssistido().getId());
    	
		designarAssistidoSigo(this.dadosPreso.getAssistido());
		
    	RequestContext.getCurrentInstance().execute("PF('selectAssistidoPreso').hide()");   
    }
	
	public void carregarDadosRemicao(Long idPreso){
		
		/* ################# CARREGAR DADOS DE Remição DO ASSISTIDO DA BASE DO SIGO ############################ */
        if(this.selecionaAssistidoSigo != null){
        	//se retorna null ent�o não cadastrou
        	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX SIGO "+this.selecionaAssistidoSigo.getId());
        	System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY idPreso"+idPreso);
        	List<GrupoRemicaoSigo> listaGrupoRemicaoSigo = this.service.obterGrupoRemicaoSigoPorAssistido(this.selecionaAssistidoSigo.getId());
        	
        	if(listaGrupoRemicaoSigo != null){
        		
        		ControleRemicao controleRemicao;
        		GrupoRemicao grupoRemicao;
        		Boolean mostramsg = false;
        		
        		for (GrupoRemicaoSigo lista : listaGrupoRemicaoSigo) {
        		
        			System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+lista.getIdAssistido());
        			System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+lista.getPeriodoInicio());
        			System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+lista.getPeriodoFim());
        			
        			//atribui 1 se alguma consulta foi inserida
        			grupoRemicao = this.service.obterGrupoRemicao(lista, idPreso);
        			if(grupoRemicao != null){
        				System.out.println("ENTROUUUUUUUUUUUUUUUUUUUUUUUU");
        					
        				List<ControleRemicaoSigo> listRemicao = this.service.obterControleRemicaoSigoPorAssistido(lista.getIdAssistido(), lista.getId());
        				
        				for (ControleRemicaoSigo remicoes : listRemicao) {
        					
        					if(this.service.verificaExistControleRemicao(remicoes) == null){        					
	        					controleRemicao = new ControleRemicao();
	        					controleRemicao.setDataInicial(remicoes.getDataInicial());
	        					controleRemicao.setDataFim(remicoes.getDataFim());
	        					controleRemicao.setTipoTrabalho(remicoes.getTipoTrabalho());
	        					controleRemicao.setIdGrupo(grupoRemicao.getId());
	        					this.service.atualizar(controleRemicao);
        					}
        				}        				
    	    			mostramsg = true;
        			}
        		}   
        		if(mostramsg){
        			FacesContext fc = FacesContext.getCurrentInstance();        	
	    			fc.addMessage(null, new FacesMessage(
	    					"Remição cadastrada com sucesso!"));
        		}else{
        			FacesContext fc = FacesContext.getCurrentInstance();        	
	    			fc.addMessage(null, new FacesMessage(
	    					"Não existe Remição a ser cadastrada!"));
        		}
        	}else{
        			FacesContext fc = FacesContext.getCurrentInstance();        	
	    			fc.addMessage(null, new FacesMessage(
	    					"Remição não cadastrada! Já existe no banco de dados! "));	        	
        	}
        }
	}
	
	/* *********************************************************************************************************** */

	public CondutaCarceraria obterCondutaCarceraria() {

		/*switch (this.selecionaAssistidoSigo.getCondutaCarceraria()) {

			case "BOM" :
				return CondutaCarceraria.BOM;
			case "RUIM" :
				return CondutaCarceraria.RUIM;
			default :
				return null;
		}*/
		return null;
	}
	
	public void cadastraAssistidoSigoNaBase(){
		try {
			AssistidoPreso aPreso = new AssistidoPreso();
			Assistido a = new Assistido();
			
			aPreso.setAssistido(new Assistido());
			aPreso.getAssistido().setNome(this.selecionaAssistidoSigo.getNome());
			aPreso.getAssistido().setMae(this.selecionaAssistidoSigo.getMae());
			aPreso.getAssistido().setPai(this.selecionaAssistidoSigo.getPai());
			aPreso.getAssistido().setRg(this.selecionaAssistidoSigo.getRg());
			aPreso.getAssistido().setCpf(this.selecionaAssistidoSigo.getCpf());
			aPreso.getAssistido().setDataNascimento(this.selecionaAssistidoSigo.getDataNascimento());
			aPreso.getAssistido().setSexo(this.selecionaAssistidoSigo.getSexo());
			if(this.selecionaAssistidoSigo.getCondutaCarceraria() != null && this.selecionaAssistidoSigo.getCondutaCarceraria() != "")
				aPreso.setCondutaCarceraria(obterCondutaCarceraria());
			if(this.selecionaAssistidoSigo.getLocalPrisao() != "" && this.selecionaAssistidoSigo.getLocalPrisao() != null){
				EstabelecimentoPenal estabelecimento = this.service.obterEstabelecimentoPorNome(this.selecionaAssistidoSigo.getLocalPrisao());
				if(estabelecimento != null)
					aPreso.setEstabelecimento(estabelecimento);
				else{
					estabelecimento = new EstabelecimentoPenal();
					estabelecimento.setNome(this.selecionaAssistidoSigo.getLocalPrisao());
					estabelecimento = this.service.inserir(estabelecimento);
					aPreso.setEstabelecimento(estabelecimento);
				}			
			}				
			aPreso.getAssistido().setDataCadastro(new Date());			
			a = this.service.inserir(aPreso.getAssistido());
			this.service.inserir(aPreso);
			
			carregarDadosRemicao(a.getId());
			
			FacesContext fc = FacesContext.getCurrentInstance();        	
			fc.addMessage(null, new FacesMessage(
					"Assistido cadastrado com sucesso!"));
			
			designarAssistidoSigo(a);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void designarAssistidoSigo(Assistido assistido) {
		Designacoes designacao = new Designacoes();
		designacao = this.service.designarAssistidoSigo(assistido, this.usuarioServiceBean.obterUsuarioDaSessao());
		if(designacao == null){
			FacesContext fc = FacesContext.getCurrentInstance();        	
			fc.addMessage(null, new FacesMessage(
					"Designação não realizada!"));
		}else{
			FacesContext fc = FacesContext.getCurrentInstance();        	
			fc.addMessage(null, new FacesMessage(
					"Designação realizada com sucesso!"));
		}
	}
	

	public Assistido getAssistido() {
		return assistido;
	}

	public void setAssistido(Assistido assistido) {
		this.assistido = assistido;
	}

	public List<Assistido> getAssistidos() {
		return assistidos;
	}

	public void setAssistidos(List<Assistido> assistidos) {
		this.assistidos = assistidos;
	}

	public ProfissaoBean getProfissaoBean() {
		return profissaoBean;
	}

	public void setProfissaoBean(ProfissaoBean profissaoBean) {
		this.profissaoBean = profissaoBean;
	}

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
	}

	public UsuarioServiceBean getUsuarioServiceBean() {
		return usuarioServiceBean;
	}

	public void setUsuarioServiceBean(UsuarioServiceBean usuarioServiceBean) {
		this.usuarioServiceBean = usuarioServiceBean;
	}

	public AssistidoSigo getAssistidoSigo() {
		return assistidoSigo;
	}

	public void setAssistidoSigo(AssistidoSigo assistidoSigo) {
		this.assistidoSigo = assistidoSigo;
	}

	public LazyDataModel<AssistidoSigo> getAssistidosSigoLazy() {
		return assistidosSigoLazy;
	}

	public void setAssistidosSigoLazy(
			LazyDataModel<AssistidoSigo> assistidosSigoLazy) {
		this.assistidosSigoLazy = assistidosSigoLazy;
	}

	public AssistidoSigo getSelecionaAssistidoSigo() {
		return selecionaAssistidoSigo;
	}

	public void setSelecionaAssistidoSigo(AssistidoSigo selecionaAssistidoSigo) {
		this.selecionaAssistidoSigo = selecionaAssistidoSigo;
	}

	public List<Assistido> getAssistidosPreso() {
		return assistidosPreso;
	}

	public void setAssistidosPreso(List<Assistido> assistidosPreso) {
		this.assistidosPreso = assistidosPreso;
	}

	public Assistido getSelecionadoPreso() {
		return selecionadoPreso;
	}

	public void setSelecionadoPreso(Assistido selecionadoPreso) {
		this.selecionadoPreso = selecionadoPreso;
	}

	public AssistidoPreso getDadosPreso() {
		return dadosPreso;
	}

	public void setDadosPreso(AssistidoPreso dadosPreso) {
		this.dadosPreso = dadosPreso;
	}

	public EntityLazyModel getAssistidosLazy() {
		return assistidosLazy;
	}

	public void setAssistidosLazy(EntityLazyModel assistidosLazy) {
		this.assistidosLazy = assistidosLazy;
	}

}
