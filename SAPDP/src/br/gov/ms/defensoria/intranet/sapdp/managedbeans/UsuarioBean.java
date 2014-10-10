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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.Defensoria;
import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.DadosAssessoria;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UsuarioRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class UsuarioBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.Usuario
	@EJB
	private UsuarioRepositorio uRep;
	private Usuario usuario = new Usuario();
	private Substituicao usuarioSubstituicao = new Substituicao();
	private Substituicao usuarioSubstituicao2 = new Substituicao();
	private List<Usuario> usuarios = new ArrayList<Usuario>();	
	private List<Substituicao> usuariosSubstituicao = new ArrayList<Substituicao>();
	private Usuario usuarioAssessoria = new Usuario();
	private DadosAssessoria dadosAssessoria = new DadosAssessoria();
		
	
	@ManagedProperty(value = "#{municipioDistritoBean}")
	private MunicipioDistritoBean municipioDistritoBean;

	@ManagedProperty(value = "#{unidadeBean}")
	private UnidadeBean unidadeBean;

	@ManagedProperty(value = "#{usuarioServiceBean}")
	private UsuarioServiceBean usuarioServiceBean;
	
	@ManagedProperty(value = "#{defensoriaBean}")
	private DefensoriaBean defensoriaBean;

	private Grupo grupo;

	private EntityLazyModel usuarioLazy;


	private String contato;

	public UsuarioBean() {
		
	}
	
	@PostConstruct
	public void init(){
		Map<String, String> mapa = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();		
		if(mapa.get("codUsuario") != null){
			System.out.println("USER _______ "+mapa.get("codUsuario"));
			this.usuario = this.uRep.obterUsuarioPorLogin(mapa.get("codUsuario"));
			this.normalizarInformacoes();
		}
	}

	public EntityLazyModel getAllUsuario() {
		if (usuarioLazy == null) {
			usuarioLazy = new EntityLazyModel(usuario);
		}
		return usuarioLazy;
	}

	/**
	 * Insere/Altera o objeto Usuario
	 */
	public void insert() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (this.usuario.getDataCadastro() == null)
			this.usuario.setDataCadastro(new Date());
		else
			this.usuario.setDataAlteracao(new Date());
		if(this.defensoriaBean.getDefensoria() != null)
			this.usuario.setIdDefensoria(this.defensoriaBean.getDefensoria().getId());
		else
			this.usuario.setIdDefensoria(null);
		this.usuario.setUnidade(this.unidadeBean.getUnidade());		
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos.add(this.grupo);
		this.usuario.setGrupo(grupos);		
		this.usuario = this.uRep.update(this.usuario);
		//verifica se existe substitui��es para esse defensor
		this.usuarioSubstituicao2 = this.uRep.obterUsuarioSubstituicaoPorLogin(this.usuario.getLogin(), "EU_SUBSTITUO");
		if(this.usuarioSubstituicao2 != null){
			this.uRep.registraLoginSubstituicao(this.usuarioSubstituicao2.getLoginSubstituicao(),
												null);
		}
		//seta informa��es defensor substituto		
		if(this.usuarioSubstituicao != null){
			this.uRep.registraLoginSubstituicao(this.usuarioSubstituicao.getLoginSubstituicao(),
												this.usuario.getLogin());			
		}
		

		fc.addMessage(null, new FacesMessage("Informa��es do usu�rio "
				+ this.usuario.getLogin() + " salvas  com sucesso!"));
		
		this.usuarioSubstituicao2 = new Substituicao();
	}
	
	/**
	 * Insere/Altera o objeto dados Assessoria
	 */
	public void insertDadosAssessoria() {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		this.dadosAssessoria.setUnidade(this.unidadeBean.getUnidadeAssessoria());
		this.dadosAssessoria.setUsuario(this.usuarioAssessoria);
		this.usuario.getDadosAssessoria().add(this.dadosAssessoria);
		this.uRep.update(this.usuario);
		fc.addMessage(null, new FacesMessage("Assessoria adicionada com sucesso!"));
		this.dadosAssessoria = new DadosAssessoria();
	}
	
	/**
	 * Exclui um objeto dados Assessoria da lista
	 */
	public void excluirDadosAssessoria() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.usuario.getDadosAssessoria().remove(this.dadosAssessoria);
		this.uRep.update(this.usuario);
		fc.addMessage(null,
				new FacesMessage("Dados assessoria removido com sucesso!"));
	}
	
	/**
	 * Lista os nomes dos usu�rios que contem parte do parametro
	 * informado
	 * @param query - parametro informado pelo usu�rio
	 * @return List<String>
	 */
	public List<String> filtrarUsuariosPorNome(String query) {
		return this.uRep.filtrarUsuariosPorNome(query);
	}

	/**
	 * Busca todas usuarios
	 */
	public List<Usuario> getSelecionarTudo() {
		return uRep.selectAll();
	}

	/**
	 * Exclui um objeto Usuario
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.uRep.remove(this.usuario);
		fc.addMessage(null, new FacesMessage("Usuario exclu�do com sucesso!"));
	}

	/**
	 * Cria uma nova usuario
	 */
	public void criarNova() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.usuario = new Usuario();
		this.dadosAssessoria = new DadosAssessoria();
		this.usuarioAssessoria = new Usuario();
		fc.addMessage(null, new FacesMessage(
				"Uma novo usuario pode ser adicionado!"));
	}

	/**
	 * Normaliza as informa��es para jogar na View
	 */
	public void normalizarInformacoes() {
		this.defensoriaBean.setDefensoria(new Defensoria());
		this.municipioDistritoBean.getMunicipioD().setNome(this.usuario.getUnidade().getMunicipioDistrito().getNome());
		
		this.unidadeBean.setUnidades(this.unidadeBean
				.obterUnidadesPorMunicipio(this.usuario.getUnidade().getMunicipioDistrito().getId()));
		this.unidadeBean.setUnidade(this.usuario == null ? new Unidade()
				: this.usuario.getUnidade());		
		
		this.grupo = usuario.getGrupo().get(0);
		
		this.defensoriaBean.obterListaDefensorias();
		if(this.usuario.getIdDefensoria() != null)
			this.defensoriaBean.setDefensoria(this.defensoriaBean.obterDefensoriaPorId(this.usuario.getIdDefensoria()));
		
		//verifica se existe substitui��es para esse defensor
		this.usuarioSubstituicao = this.uRep.obterUsuarioSubstituicaoPorLogin(this.usuario.getLogin(), "EU_SUBSTITUO");
		if(this.usuarioSubstituicao == null){
			this.usuarioSubstituicao = new Substituicao();
		}
		this.usuarioSubstituicao2 = new Substituicao();
		obterDefensores();
	}

	public List<SelectItem> getSelectItemsGrupo() {
		List<SelectItem> itens = new ArrayList<SelectItem>();
		Usuario u = this.usuarioServiceBean.obterUsuarioLogado();
		for (Grupo grupo : getObterTodosGrupos()) {
			SelectItem item = new SelectItem();
			item.setLabel(grupo.toString());
			item.setValue(grupo.toString());
			if ((grupo == Grupo.MASTER || grupo == Grupo.ADMINISTRADOR)
					&& u.getGrupo().get(0) != Grupo.MASTER)
				item.setDisabled(true);
			itens.add(item);
		}
		return itens;
	}
	
	/**
	 * Retorna uma lista de grupos a partir do enum Grupo
	 * 
	 * @return List<Grupo>
	 */
	public List<Grupo> getObterTodosGrupos() {
		return Arrays.asList(Grupo.values());
	}
	
	/**
	 * Obtem usuario do Grupo DEFENSOR, e de acordo com a unidade do usu�rio logado.
	 * @see UsuarioRepositorio - obterDefensoresParaDesignacao
	 * @return List<Usuario>
	 */
	public List<Usuario> getObterDefensoresPorUnidade() {
		if(this.usuarios.isEmpty())
			this.usuarios = uRep.obterDefensoresParaDesignacao(obterUsuarioDaSessao().getUnidade().getId(),
					Grupo.DEFENSOR);		
		
		for (Usuario u : this.usuarios) {
			//se for != de null ent�o este defensor est� substituindo outro
			if(u.getLoginSubstituicao() != null){
				this.usuarioSubstituicao = this.uRep.obterUsuarioSubstituicaoPorLogin(u.getLoginSubstituicao(),"ME_SUBSTITUI");
				u.setIdDefensoriaSubstituicao(this.usuarioSubstituicao.getIdDefensoriaSubstituicao());
				u.setNomeDefensorSubstituto(this.usuarioSubstituicao.getNomeDefensorSubstituto());
			}
		}	
		return this.usuarios;
		 
	}
	
	/**
	 * Obtem defensores 
	 * @return List<Substituicao>
	 */
	public void obterDefensores() {		
		this.usuariosSubstituicao = uRep.obterDefensores(Grupo.DEFENSOR);
	}

	/**
	 * M�todo para tratar o evento de sele��o SelectEvent
	 * 
	 * @param event
	 */
	public void onItemSelect(SelectEvent event) {
		this.municipioDistritoBean.setMunicipioD(this.municipioDistritoBean
				.buscarMunicipioDistrito(event.getObject().toString()));
		this.unidadeBean.setUnidades(this.unidadeBean
				.obterUnidadesPorMunicipio(this.municipioDistritoBean
						.getMunicipioD().getId()));
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(event.getObject().toString() + " SELECIONADO!", event.getObject().toString()));
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(event.getObject().toString() + " Unidades carregadas!", event.getObject()
								.toString()));
	}
	
	/**
	 * M�todo para tratar o evento de sele��o de usu�rios assessoria
	 * 
	 * @param event
	 */
	public void onItemSelect2(SelectEvent event) {
		this.usuarioAssessoria = this.uRep.obterUsuarioPorNome(this.usuarioAssessoria.getNome());
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(event.getObject().toString() + " SELECIONADO!", event.getObject().toString()));
	}
	
	/**
	 * Obter usu�rio da sess�o
	 * @return Usuario
	 */
	public Usuario obterUsuarioDaSessao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		Usuario u = new Usuario();
		try {
			u = (Usuario) session.getAttribute("usuario");
		} catch (Exception e) {
			System.out.println("NINGUEM NA SESSAO");
		}
		return u;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public UsuarioServiceBean getUsuarioServiceBean() {
		return usuarioServiceBean;
	}

	public void setUsuarioServiceBean(UsuarioServiceBean usuarioServiceBean) {
		this.usuarioServiceBean = usuarioServiceBean;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Usuario getUsuarioAssessoria() {
		return usuarioAssessoria;
	}

	public void setUsuarioAssessoria(Usuario usuarioAssessoria) {
		this.usuarioAssessoria = usuarioAssessoria;
	}

	public DadosAssessoria getDadosAssessoria() {
		return dadosAssessoria;
	}

	public void setDadosAssessoria(DadosAssessoria dadosAssessoria) {
		this.dadosAssessoria = dadosAssessoria;
	}

	public Substituicao getUsuarioSubstituicao() {
		return usuarioSubstituicao;
	}

	public void setUsuarioSubstituicao(Substituicao usuarioSubstituicao) {
		this.usuarioSubstituicao = usuarioSubstituicao;
	}

	public List<Substituicao> getUsuariosSubstituicao() {
		return usuariosSubstituicao;
	}

	public void setUsuariosSubstituicao(List<Substituicao> usuariosSubstituicao) {
		this.usuariosSubstituicao = usuariosSubstituicao;
	}

	public Substituicao getUsuarioSubstituicao2() {
		return usuarioSubstituicao2;
	}

	public void setUsuarioSubstituicao2(Substituicao usuarioSubstituicao2) {
		this.usuarioSubstituicao2 = usuarioSubstituicao2;
	}

	public DefensoriaBean getDefensoriaBean() {
		return defensoriaBean;
	}

	public void setDefensoriaBean(DefensoriaBean defensoriaBean) {
		this.defensoriaBean = defensoriaBean;
	}
			
}
