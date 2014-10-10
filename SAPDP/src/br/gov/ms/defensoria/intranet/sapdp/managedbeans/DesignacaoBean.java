package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
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
public class DesignacaoBean {

	private List<Designacoes> designacoes = new ArrayList<Designacoes>();
	private Designacoes designacao = new Designacoes();
	private SimpleDesignacao simpleDesignacao;
	private List<SimpleDesignacao> simpleDesignacoes = new ArrayList<SimpleDesignacao>();
	private Usuario usuario;
	private String casoNovo;

	@EJB
	private SegurancaService service;
	
	@ManagedProperty(value = "#{usuarioServiceBean}")
	private UsuarioServiceBean usuarioServiceBean;

	private EntityLazyModel designacoesLazy;

	// Construtor
	public DesignacaoBean() {

	}

	public EntityLazyModel getAllDesignacoes() {
		if (designacoesLazy == null) {
			designacoesLazy = new EntityLazyModel(designacao);
		}
		return designacoesLazy;
	}

	/**
	 * Insere/Altera o objeto Designacao
	 */
	public void insert() {
		// defensor da tabela USUARIOS selecionado vai para loginSubstituicao na
		// tabela DESIGNACOES e
		// loginSubstituicao da tabela USUARIOS vai para defensor da DESIGNACAO
		if (this.designacao.getDefensor().getLoginSubstituicao() != null) {
			this.designacao.setLoginSubstituicao(this.designacao.getDefensor()
					.getLogin());
			this.designacao.setDefensor(this.usuarioServiceBean
					.obterUsuarioPorLogin(this.designacao.getDefensor()
							.getLoginSubstituicao()));
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		this.designacao.setDataDesignacao(new Date());
		this.designacao.setStatus(StatusDesignacao.DESIGNADO);
		if (this.designacao.getId() == null) {
			this.designacao = this.service.inserir(this.designacao);
			fc.addMessage(null, new FacesMessage(
					"Assistido Designado com sucesso!"));
		} else {
			this.service.atualizar(this.designacao);
			fc.addMessage(null, new FacesMessage(
					"Designacao Alterada com sucesso!"));
		}
	}

	public Designacoes obterDesignacaoPorId() {
		return this.service.obterDesignacaoPorId(this.simpleDesignacao
				.getIdDesignacao());
	}

	/**
	 * Carrega as designa��es do dia para o defensor logado
	 * 
	 * @return <code>List<Desiginacoes></code>
	 */
	public List<SimpleDesignacao> getfiltrarDesignacoesPorDefensorData() {
		try {
			this.usuario = this.usuarioServiceBean.obterUsuarioDaSessao();
			// Se assessor ou estagi�rio logado, ent�o pega o defensor vinculado
			if (this.usuario.getGrupo().get(0) == Grupo.ASSESSOR
					|| this.usuario.getGrupo().get(0) == Grupo.ESTAGIARIO) {
				this.usuario = this.usuarioServiceBean
						.obterDefensorDoAssessor(this.usuario.getLogin());
			}
			this.simpleDesignacoes = this.service
					.filtrarDesignacoesPorDefensorData(this.usuario.getLogin());
			return this.simpleDesignacoes;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void buscaDesignacoesPorDefensorData() {
		try {
			this.usuario = this.usuarioServiceBean.obterUsuarioDaSessao();
			// Se assessor ou estagi�rio logado, ent�o pega o defensor vinculado
			if (this.usuario.getGrupo().get(0) == Grupo.ASSESSOR
					|| this.usuario.getGrupo().get(0) == Grupo.ESTAGIARIO) {
				this.usuario = this.usuarioServiceBean
						.obterDefensorDoAssessor(this.usuario.getLogin());
			}
			this.simpleDesignacoes = this.service
					.filtrarDesignacoesPorDefensorData(this.usuario.getLogin());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alteraStatusDesignacao(StatusDesignacao status) {
		// se status designacao for alterado para CANCELADO, ent�o alterar
		// idAtendimentoPai para NULL
		if (status.equals(StatusDesignacao.CANCELADO)
				&& this.designacao.getIdAtendimentoPai() != null)
			this.designacao.setIdAtendimentoPai(null);
		if (status.equals(StatusDesignacao.CHAMADO)) {
			this.designacao.setDataChamado(new Date());
			this.designacao.setSala(this.usuarioServiceBean
					.obterUsuarioDaSessao().getSala());
		}
		this.designacao.setStatus(status);
		this.service.atualizar(this.designacao);
		this.designacao = new Designacoes();
	}

	public List<Designacoes> getDesignacoes() {
		return designacoes;
	}

	public void setDesignacoes(List<Designacoes> designacoes) {
		this.designacoes = designacoes;
	}

	public Designacoes getDesignacao() {
		return designacao;
	}

	public void setDesignacao(Designacoes designacao) {
		this.designacao = designacao;
	}

	public UsuarioServiceBean getUsuarioServiceBean() {
		return usuarioServiceBean;
	}

	public void setUsuarioServiceBean(UsuarioServiceBean usuarioServiceBean) {
		this.usuarioServiceBean = usuarioServiceBean;
	}

	public SimpleDesignacao getSimpleDesignacao() {
		return simpleDesignacao;
	}

	public void setSimpleDesignacao(SimpleDesignacao simpleDesignacao) {
		this.simpleDesignacao = simpleDesignacao;
	}

	public List<SimpleDesignacao> getSimpleDesignacoes() {
		return simpleDesignacoes;
	}

	public void setSimpleDesignacoes(List<SimpleDesignacao> simpleDesignacoes) {
		this.simpleDesignacoes = simpleDesignacoes;
	}

	public EntityLazyModel getDesignacoesLazy() {
		return designacoesLazy;
	}

	public void setDesignacoesLazy(EntityLazyModel designacoesLazy) {
		this.designacoesLazy = designacoesLazy;
	}

	public String getCasoNovo() {
		return casoNovo;
	}

	public void setCasoNovo(String casoNovo) {
		this.casoNovo = casoNovo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
