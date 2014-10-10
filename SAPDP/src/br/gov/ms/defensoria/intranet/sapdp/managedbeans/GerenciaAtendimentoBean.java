package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;

@ViewScoped
@ManagedBean
public class GerenciaAtendimentoBean {

	@ManagedProperty(value = "#{assistidoBean}")
	private AssistidoBean assistidoBean;
	
	@ManagedProperty(value = "#{designacaoBean}")
	private DesignacaoBean designacaoBean;

	@ManagedProperty(value = "#{nucleoBean}")
	private NucleoBean nucleoBean;

	@ManagedProperty(value = "#{usuarioBean}")
	private UsuarioBean usuarioBean;
	
	@ManagedProperty(value = "#{atendimentoBean}")
	private AtendimentoBean atendimentoBean;
	
	@ManagedProperty(value = "#{itemBean}")
	private ItemBean itemBean;
	
	@ManagedProperty(value = "#{subItemBean}")
	private SubItemBean subItemBean;		

	private Boolean designar = false;
	private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");	

	private String dataAtualizacaoPainel;
	private String dataAtualizacaoPainelPenal;
	
	private LazyDataModel<SimpleAtendimento> simpleAtendimentosLazy = null;
	

	// Construtor
	public GerenciaAtendimentoBean() {
		// Parametro designação
		try {
			Map<String, String> mapa = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			this.designar = Boolean.valueOf(mapa.get("designar"));			
		} catch (Exception e) {
			System.out.println("Não designar!");
		}
	}

	
	/**
	 * Insere o objeto Atendimento
	 */
	public void insertAtend() {		
		this.atendimentoBean.getAtendimento().setItem(this.itemBean.getItem());
		this.atendimentoBean.getAtendimento().setSubItem(this.subItemBean.getSubitem());	
		this.atendimentoBean.insert(this.atendimentoBean.getAtendimento());
		this.itemBean.setItem(new ItemAtendimento());
		this.subItemBean.setSubitem(new SubItemAtendimento());
	}
	
	public void atualizarPainelAssistidosEmEspera() {
		this.designacaoBean.buscaDesignacoesPorDefensorData("PADRAO");
		
		this.dataAtualizacaoPainel = fmt.format(new Date());
	}
	
	
	public void atualizarPainelAssistidosEmEsperaPenal() {
		this.designacaoBean.buscaDesignacoesPorDefensorData("PENAL");
		
		this.dataAtualizacaoPainelPenal = fmt.format(new Date());
	}
	
	/**
	 * Configura a designação do assistido
	 */
	public void designarAssistido() {
		this.designacaoBean.getDesignacao().setAssistido(
				this.assistidoBean.getAssistido());
		this.designacaoBean.getDesignacao()
				.setAtendente(
						this.usuarioBean.getUsuarioServiceBean()
								.obterUsuarioDaSessao());
		//trata atendimento retorno
		if(this.designacaoBean.getCasoNovo().equalsIgnoreCase("NAO") && this.atendimentoBean.getAtendimento() != null){
			this.designacaoBean.getDesignacao().setIdAtendimentoPai(this.atendimentoBean.getSimpleAtendimento().getIdAtendimento());
			this.atendimentoBean.alteraUtimoAtendimento(this.atendimentoBean.getSimpleAtendimento().getIdAtendimento(), "NAO");
		}
		this.designacaoBean.insert();
	}

	/**
	 * Direciona o link de acordo com o n�vel de usu�rio
	 * 
	 * @return String - URL de retorno
	 */
	public String direcionaLink() {
		if (this.usuarioBean.getUsuarioServiceBean().obterUsuarioDaSessao().getGrupo().get(0) == Grupo.ADMINISTRADOR ||
			this.usuarioBean.getUsuarioServiceBean().obterUsuarioDaSessao().getGrupo().get(0) == Grupo.MASTER){
			
			return "/admin/assistido/assistido.jsf?codAssistido="
					+ this.assistidoBean.getAssistido().getId();
		}else{
			return "/atendente/assistido.jsf?codAssistido="
					+ this.assistidoBean.getAssistido().getId();
		}
	}

	public void atenderAssistido(){
		
		if(this.designacaoBean.getSimpleDesignacao().getStatusDesignacao() == StatusDesignacao.DESIGNADO){
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(
					"É necessário CHAMAR o assistido primeiro!"));
		}else{
		
			this.atendimentoBean.setSimpleAtendimento(new SimpleAtendimento());
			System.out.println("ATENDENDO "+this.designacaoBean.getSimpleDesignacao().getIdDesignacao());
			this.designacaoBean.setDesignacao(this.designacaoBean.obterDesignacaoPorId());			
			this.itemBean.setItens(this.getObterListaDeItensPorNucleo());
			this.subItemBean.setSubitens(this.getObterListaDeSubItensPorItem());
			this.atendimentoBean.criarNovo();
			this.itemBean.setItem(new ItemAtendimento());
			this.subItemBean.setSubitem(new SubItemAtendimento());
			
			if(this.designacaoBean.getDesignacao().getIdAtendimentoPai() != null){
				getObterHistoricoPorAtendimento(this.designacaoBean.getDesignacao().getIdAtendimentoPai());
			}else{
				this.atendimentoBean.setAtendimentos2(new ArrayList<Atendimento>());
			}
		}
	}
	
	public void carregaAtendimento(SelectEvent event){
		
		
		this.atendimentoBean.setAtendimento(this.atendimentoBean.obterAtendimentoPorId(this.atendimentoBean.getSimpleAtendimento().getIdAtendimento()));
		this.designacaoBean.setDesignacao(this.atendimentoBean.getAtendimento().getDesignacao());
		this.getItemBean().setItem(this.atendimentoBean.getAtendimento().getItem());
		this.getSubItemBean().setSubitem(this.atendimentoBean.getAtendimento().getSubItem());
		this.getItemBean().obterListaDeItensPorNucleo(this.atendimentoBean.getAtendimento().getDesignacao().getNucleo().getId());
		this.getSubItemBean().obterListaDeSubItensPorItem(this.getItemBean().getItem().getId());
		if(this.atendimentoBean.getAtendimento().getDesignacao().getIdAtendimentoPai() != null){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>XXXXXXXXXXXXXX"+this.atendimentoBean.getAtendimento().getDesignacao().getIdAtendimentoPai());
			getObterHistoricoPorAtendimento(this.atendimentoBean.getAtendimento().getDesignacao().getIdAtendimentoPai());
		}else{			
			this.atendimentoBean.setAtendimentos2(new ArrayList<Atendimento>());
		}		
	}
	
	public void carregaAtendimento2(SelectEvent event){
		
		this.atendimentoBean.setAtendimento(this.atendimentoBean.obterAtendimentoPorId(this.atendimentoBean.getAtendimento().getId()));
		this.designacaoBean.setDesignacao(this.atendimentoBean.getAtendimento().getDesignacao());
		this.getItemBean().setItem(this.atendimentoBean.getAtendimento().getItem());
		this.getSubItemBean().setSubitem(this.atendimentoBean.getAtendimento().getSubItem());
		this.getItemBean().obterListaDeItensPorNucleo(this.atendimentoBean.getAtendimento().getDesignacao().getNucleo().getId());
		this.getSubItemBean().obterListaDeSubItensPorItem(this.getItemBean().getItem().getId());		
	}
	
	public void cancelarDesignacao(){
		this.designacaoBean.setDesignacao(this.designacaoBean.obterDesignacaoPorId());
		//se designação for retorno, logo  é necessário tratar 
		if(this.designacaoBean.getDesignacao().getIdAtendimentoPai() != null)
			this.atendimentoBean.alteraUtimoAtendimento(this.designacaoBean.getDesignacao().getIdAtendimentoPai(), "SIM");
		this.designacaoBean.alteraStatusDesignacao(StatusDesignacao.CANCELADO);
	}
	
	public void chamarAssistido(){
		this.designacaoBean.setDesignacao(this.designacaoBean.obterDesignacaoPorId());
		this.designacaoBean.alteraStatusDesignacao(StatusDesignacao.CHAMADO);
	}
	
	public void obterInformacoesDoAssistido(){
		System.out.println("INFO ASSIS");
		this.assistidoBean.setAssistido(this.designacaoBean.getDesignacao().getAssistido());
		this.assistidoBean.selecionarNomeDaProfissao();
	}
	
	@PostConstruct
	public void getObterAtendimentosPorDefensorData(){
		
		try {
			this.usuarioBean.setUsuario(this.usuarioBean.getUsuarioServiceBean().obterUsuarioDaSessao());
			
			//Se assessor ou estagi�rio logado, então  pega o defensor vinculado
			if(this.usuarioBean.getUsuario().getGrupo().get(0) == Grupo.ASSESSOR || this.usuarioBean.getUsuario().getGrupo().get(0) == Grupo.ESTAGIARIO){
				this.usuarioBean.setUsuario(this.usuarioBean.getUsuarioServiceBean().obterDefensorDoAssessor(this.usuarioBean.getUsuario().getLogin()));				
			}
			System.out.println("PASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			if(this.simpleAtendimentosLazy == null && this.usuarioBean.getUsuario().getLogin() != null)
				this.simpleAtendimentosLazy = this.atendimentoBean.obterAtendimentosPorDefensorData(this.usuarioBean.getUsuario().getLogin());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		
	}
	
	/**
	 * Obter atendimentos por Assistido
	 * @return List<Atendimento>
	 */
	public List<SimpleAtendimento> getObterAtendimentosPorAssistido(){
		try {
			this.usuarioBean.setUsuario(this.usuarioBean.getUsuarioServiceBean().obterUsuarioDaSessao());
			//Se assessor ou estagi�rio logado, então  pega o defensor vinculado
			if(this.usuarioBean.getUsuario().getGrupo().get(0) == Grupo.ASSESSOR || this.usuarioBean.getUsuario().getGrupo().get(0) == Grupo.ESTAGIARIO){
				this.usuarioBean.setUsuario(this.usuarioBean.getUsuarioServiceBean().obterDefensorDoAssessor(this.usuarioBean.getUsuario().getLogin()));
			}
			return this.atendimentoBean.obterAtendimentosPorAssistido(this.assistidoBean.getAssistido().getId());	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ArrayList<SimpleAtendimento>();
		}
		
	}
	

	
	/**
	 * Obter historico por atendimento
	 * @return List<Atendimento>
	 */
	public List<Atendimento> getObterHistoricoPorAtendimento(Long idAtendimentoPai){
		try {		
			System.out.println("XXXXXX>>>>>>>>>>>>>>>>>"+idAtendimentoPai);
			return this.atendimentoBean.montaListaAtendimentos(idAtendimentoPai);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ArrayList<Atendimento>();
		}
	}
	
	/**
	 * Obtem lista de itens por n�cleo	
	 * @return List<ItemAtendimento>
	 */
	public List<ItemAtendimento> getObterListaDeItensPorNucleo(){
		if(this.designacaoBean.getDesignacao().getNucleo() != null){
			System.out.println("ID NUCLEOOOOOOOOOOOOOOOOOOOOOO >>>>>>>"+this.designacaoBean.getDesignacao().getNucleo().getId());
			return this.itemBean.obterListaDeItensPorNucleo(this.designacaoBean.getDesignacao().getNucleo().getId());
		}else{
			return this.itemBean.getItens();
		}
	}
	
	/**
	 *  M�todo que da refresh na lista de Itens por n�cleo
	 * @param event
	 */
	public void refreshItens(AjaxBehaviorEvent event) {
		System.out.println("PASSOU refreshItens >>>>>>>>>>>>"+this.itemBean.getItem().getId());
		this.itemBean.setItens(this.getObterListaDeItensPorNucleo());
		
	}
	
	/**
	 * Obtem lista de subItens por item	
	 * @return List<SubItemAtendimento>
	 */
	public List<SubItemAtendimento> getObterListaDeSubItensPorItem(){
		if(this.itemBean.getItem() != null){
			System.out.println("ID ITEM >>>>>>>"+this.itemBean.getItem().getId());
			return this.subItemBean.obterListaDeSubItensPorItem(this.itemBean.getItem().getId());
		}else{
			return this.subItemBean.getSubitens();
		}
	}
	
	
	/**
	 *  M�todo que da refresh na lista de SubItens por item
	 * @param event
	 */
	public void refreshSubItens(AjaxBehaviorEvent event) {
		System.out.println("PASSOU >>>>>>>>>>>>"+this.itemBean.getItem().getId());
		this.subItemBean.setSubitens(this.getObterListaDeSubItensPorItem());
		
	}
	
	public void selecionaAssistidoFromDialog(){
		//se realizou designação então carrega designações
		this.assistidoBean.selecionaAssistidoFromDialog();
		//if(this.assistidoBean.selecionaAssistidoFromDialog() == 1){
		//	this.designacaoBean.buscaDesignacoesPorDefensorData("PENAL");
		//}
	}
	
	public void cadastraAssistidoSigoNaBase(){
		this.assistidoBean.cadastraAssistidoSigoNaBase();
		//if (this.assistidoBean.cadastraAssistidoSigoNaBase() == 1) {
		//	this.designacaoBean.buscaDesignacoesPorDefensorData("PENAL");
		//}
	}
	
	
	
	public AssistidoBean getAssistidoBean() {
		return assistidoBean;
	}

	public void setAssistidoBean(AssistidoBean assistidoBean) {
		this.assistidoBean = assistidoBean;
	}

	public NucleoBean getNucleoBean() {
		return nucleoBean;
	}

	public void setNucleoBean(NucleoBean nucleoBean) {
		this.nucleoBean = nucleoBean;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public Boolean getDesignar() {
		return designar;
	}

	public void setDesignar(Boolean designar) {
		this.designar = designar;
	}

	public DesignacaoBean getDesignacaoBean() {
		return designacaoBean;
	}

	public void setDesignacaoBean(DesignacaoBean designacaoBean) {
		this.designacaoBean = designacaoBean;
	}

	public String getDataAtualizacaoPainel() {
		return dataAtualizacaoPainel;
	}

	public void setDataAtualizacaoPainel(String dataAtualizacaoPainel) {
		this.dataAtualizacaoPainel = dataAtualizacaoPainel;
	}

	public AtendimentoBean getAtendimentoBean() {
		return atendimentoBean;
	}

	public void setAtendimentoBean(AtendimentoBean atendimentoBean) {
		this.atendimentoBean = atendimentoBean;
	}

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

	public SubItemBean getSubItemBean() {
		return subItemBean;
	}

	public void setSubItemBean(SubItemBean subItemBean) {
		this.subItemBean = subItemBean;
	}

	public LazyDataModel<SimpleAtendimento> getSimpleAtendimentosLazy() {
		return simpleAtendimentosLazy;
	}

	public void setSimpleAtendimentosLazy(
			LazyDataModel<SimpleAtendimento> simpleAtendimentosLazy) {
		this.simpleAtendimentosLazy = simpleAtendimentosLazy;
	}


	public String getDataAtualizacaoPainelPenal() {
		return dataAtualizacaoPainelPenal;
	}


	public void setDataAtualizacaoPainelPenal(String dataAtualizacaoPainelPenal) {
		this.dataAtualizacaoPainelPenal = dataAtualizacaoPainelPenal;
	}

	
}
