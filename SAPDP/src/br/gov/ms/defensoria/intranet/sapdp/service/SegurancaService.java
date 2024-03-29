package br.gov.ms.defensoria.intranet.sapdp.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.bo.AssistidoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.AssistidoPresoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.ControleRemicaoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.ControleRemicaoSigoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.DesignacaoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.EstabelecimentoPenalBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.GenericsSearchBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.GrupoRemicaoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.GrupoRemicaoSigoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.ProcessoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.TotalRemicaoBO;
import br.gov.ms.defensoria.intranet.sapdp.bo.UsuarioBO;
import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.TotalRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Processo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

@Stateless
@LocalBean
public class SegurancaService {

	@EJB
	private EstabelecimentoPenalBO estabelecimentoPenalBO;

	@EJB
	private GenericsSearchBO searchBO;

	@EJB
	private AssistidoPresoBO assistidoPresoBO;

	@EJB
	private AssistidoBO assistidoBO;

	@EJB
	private GrupoRemicaoSigoBO grupoRemicaoSigoBO;

	@EJB
	private GrupoRemicaoBO grupoRemicaoBO;

	@EJB
	private TotalRemicaoBO totalRemicaoBO;

	@EJB
	private ControleRemicaoSigoBO controleRemicaoSigoBO;

	@EJB
	private ControleRemicaoBO controleRemicaoBO;

	@EJB
	private DesignacaoBO designacaoBO;

	@EJB
	private UsuarioBO usuarioBO;
	
	@EJB
	private ProcessoBO processoBO;

	public EstabelecimentoPenal obterEstabelecimentoPorNome(String nome) {
		return estabelecimentoPenalBO.obterEstabelecimentoPorNome(nome);
	}

	public List<EstabelecimentoPenal> listaTodos() {
		return estabelecimentoPenalBO.listaTodos();
	}

	public EstabelecimentoPenal inserir(EstabelecimentoPenal entity) {
		return estabelecimentoPenalBO.inserir(entity);
	}

	public EstabelecimentoPenal atualizar(EstabelecimentoPenal entity) {
		return estabelecimentoPenalBO.atualizar(entity);
	}

	public void remover(Long id) {
		estabelecimentoPenalBO.remover(id);
	}

	public List<IGenericEntity> carregarPesquisaLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros, IGenericEntity entity) {
		return searchBO.carregarPesquisaLazy(startingAt, maxPerPage,
				fieldOrder, order, parametros, entity);
	}

	public List<IGenericEntity> carregarPesquisaUsuarioLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros) {
		return searchBO.carregarPesquisaUsuarioLazy(startingAt, maxPerPage,
				fieldOrder, order, parametros);
	}

	public AssistidoPreso atualizar(AssistidoPreso assistidoPreso) {
		return assistidoPresoBO.atualizar(assistidoPreso);
	}

	public AssistidoPreso inserir(AssistidoPreso assistidoPreso) {
		return assistidoPresoBO.inserir(assistidoPreso);
	}

	public Assistido inserir(Assistido assistido) {
		return assistidoBO.inserir(assistido);
	}

	public Assistido atualizar(Assistido assistido) {
		return assistidoBO.atualizar(assistido);
	}

	public AssistidoPreso obterAssistidoPreso(Long idAssistido) {
		return assistidoBO.obterAssistidoPreso(idAssistido);
	}

	public List<Assistido> buscaAssistidoSIGOExisteBase(String nome,
			String cpf, String rg, String mae, String pai) {
		return assistidoBO
				.buscaAssistidoSIGOExisteBase(nome, cpf, rg, mae, pai);
	}

	public Assistido obterAssistidoPorId(Long id) {
		return assistidoBO.obterAssistidoPorId(id);
	}

	public List<Assistido> listaTodosAssistidos() {
		return assistidoBO.listaTodos();
	}

	public void removerAssistido(Long id) {
		assistidoBO.remover(id);
	}

	public List<GrupoRemicaoSigo> obterGrupoRemicaoSigoPorAssistido(
			Long idAssistido) {
		return grupoRemicaoSigoBO
				.obterGrupoRemicaoSigoPorAssistido(idAssistido);
	}

	public GrupoRemicao obterGrupoRemicao(GrupoRemicaoSigo grupo, Long idPreso) {
		return grupoRemicaoBO.obterGrupoRemicao(grupo, idPreso);
	}

	public TotalRemicao atualizar(TotalRemicao totalRemicao) {
		return totalRemicaoBO.atualizar(totalRemicao);
	}

	public ControleRemicaoSigo obterControleRemicaoPorId(Long id) {
		return controleRemicaoSigoBO.obterControleRemicaoPorId(id);

	}

	public List<ControleRemicaoSigo> obterControleRemicaoSigoPorAssistido(
			Long idAssistido, Long idGrupo) {
		return controleRemicaoSigoBO.obterControleRemicaoSigoPorAssistido(
				idAssistido, idGrupo);
	}

	public ControleRemicao atualizar(ControleRemicao entity) {
		return controleRemicaoBO.atualizar(entity);
	}

	public ControleRemicao verificaExistControleRemicao(
			ControleRemicaoSigo controle) {
		return controleRemicaoBO.verificaExistControleRemicao(controle);
	}

	public List<Designacoes> listaTodasDesignacoes() {
		return designacaoBO.listaTodos();
	}

	public Designacoes inserir(Designacoes entity) {
		return designacaoBO.inserir(entity);
	}

	public Designacoes atualizar(Designacoes entity) {
		return designacaoBO.atualizar(entity);
	}

	public void removerDesignacao(Long id) {
		designacaoBO.remover(id);
	}

	public Designacoes obterDesignacaoPorId(Long idDesignacao) {
		return designacaoBO.obterDesignacaoPorId(idDesignacao);
	}

	public void alterarStatusDesignacoes(Long id, StatusDesignacao status) {
		designacaoBO.alterarStatusDesignacoes(id, status);
	}

	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorData(
			String defensor, String tipoDesignacao) throws ParseException {
		return designacaoBO.filtrarDesignacoesPorDefensorData(defensor,
				tipoDesignacao);
	}

	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorDataPenal(
			String defensor, String tipoDesignacao) throws ParseException {
		return designacaoBO.filtrarDesignacoesPorDefensorDataPenal(defensor,
				tipoDesignacao);
	}

	public Designacoes designarAssistidoSigo(Assistido assistido,
			Usuario usuario) {
		return designacaoBO.designarAssistidoSigo(assistido, usuario);
	}

	public Usuario obterDefensorDoAssessor(String assessor) {
		return usuarioBO.obterDefensorDoAssessor(assessor);
	}

	public Usuario obterUsuarioPorNome(String nome) {
		return usuarioBO.obterUsuarioPorNome(nome);
	}

	public Usuario inserir(Usuario entity) {
		return usuarioBO.inserir(entity);
	}

	public Usuario atualizar(Usuario entity) {
		return usuarioBO.atualizar(entity);
	}

	public boolean remover(Usuario entity) {
		return usuarioBO.remover(entity);
	}

	public boolean verificaDisponibilidadeDeLogin(String login) {
		return usuarioBO.verificaDisponibilidadeDeLogin(login);
	}

	public Usuario obterUsuarioPorLogin(String login) {
		return usuarioBO.obterUsuarioPorLogin(login);
	}

	public Substituicao obterUsuarioSubstituicaoPorNome(String nome) {
		return usuarioBO.obterUsuarioSubstituicaoPorNome(nome);
	}

	public Substituicao obterUsuarioSubstituicaoPorLogin(String login,
			String tipoConsulta) {
		return usuarioBO.obterUsuarioSubstituicaoPorLogin(login, tipoConsulta);
	}

	public List<String> filtrarUsuariosPorNome(String nome) {
		return usuarioBO.filtrarUsuariosPorNome(nome);
	}

	public List<Usuario> filtrarUsuariosPorUnidade(Long idUnidade) {
		return usuarioBO.filtrarUsuariosPorUnidade(idUnidade);
	}

	public List<Usuario> obterDefensoresParaDesignacao(Long idUnidade,
			Grupo grupo) {
		return usuarioBO.obterDefensoresParaDesignacao(idUnidade, grupo);
	}

	public List<Substituicao> obterDefensores(Grupo grupo) {
		return usuarioBO.obterDefensores(grupo);
	}

	public void registraLoginSubstituicao(String loginDefensor,
			String loginDefensorSubstitui) {
		usuarioBO.registraLoginSubstituicao(loginDefensor,
				loginDefensorSubstitui);

	}

	public List<Usuario> listaTodosUsuarios() {
		return usuarioBO.listaTodos();
	}
	
	public List<Processo> listaTodosProcessos() {
		return processoBO.listaTodos();
	}

	public Processo inserir(Processo entity) {
		return processoBO.inserir(entity);
	}

	public Processo atualizar(Processo entity) {
		return processoBO.atualizar(entity);
	}

	public void removerProcesso(Long id) {
		processoBO.remover(id);
	}

}
