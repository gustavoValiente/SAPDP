package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.gov.ms.defensoria.intranet.sapdp.dao.DesignacaoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

@Stateless
@LocalBean
public class DesignacaoBO {
	
	@EJB
	private DesignacaoDAO dao;
	
	@EJB
	private UsuarioBO usuarioBO;
	
	private Calendar c1;
	private Calendar c2;
	
	public List<Designacoes> listaTodos() {
		return dao.listaTodos();
	}

	public Designacoes inserir(Designacoes entity) {
		return dao.inserir(entity);
	}

	public Designacoes atualizar(Designacoes entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
	
	public Designacoes obterDesignacaoPorId(Long idDesignacao){
		return dao.obterDesignacaoPorId(idDesignacao);
	}
	
	public void alterarStatusDesignacoes(Long id, StatusDesignacao status){
		dao.alterarStatusDesignacoes(id, status);
	}
		
		
	/**
	 * Normaliza as datas atuais
	 */
	public void normalizaDatasAtuais() {
		c1 = Calendar.getInstance();
		// atendimentos entre a 00:00 da data inicial passada como parametro
		c1.setTime(new Date());
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		// e as 23:59:59:99 da data final passada como parametro
		c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.MILLISECOND, 99);
	}
	
	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorData(String defensor, String tipoDesignacao) throws ParseException {

		normalizaDatasAtuais();
		
		return dao.filtrarDesignacoesPorDefensorData(defensor,  c1.getTime(), c2.getTime(), tipoDesignacao);		
		
	}
	
	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorDataPenal(String defensor, String tipoDesignacao) throws ParseException {

		normalizaDatasAtuais();
		
		return dao.filtrarDesignacoesPorDefensorDataPenal(defensor,  c1.getTime(), c2.getTime(), tipoDesignacao);		
		
	}
	
	

	/**
	 * Retorna uma lista de designacoes do dia, implementa��o LazyModel
	 * 
	 * @return <code>List<Designacoes></code>
	 */
	public List<Designacoes> carregarDesignacoesLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order, String assistido,
			String nucleo, String defensor, String atendente,
			String preferencial, String status) {
		
		normalizaDatasAtuais();
		return dao.carregarDesignacoesLazy(startingAt, maxPerPage, fieldOrder, order, assistido, nucleo, defensor, 
				atendente, preferencial, status, c1.getTime(), c2.getTime());
		
	}

	/**
	 * Retorna o total de designacoes do dia, implementa��o LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotallDesignacoesLazy(String assistido, String nucleo,
			String defensor, String atendente, String preferencial,
			String status) {

		normalizaDatasAtuais();
		return dao.carregarTotallDesignacoesLazy(assistido, nucleo, defensor, atendente, preferencial, status, c1.getTime(), c2.getTime());

	}
	
	public Designacoes designarAssistidoSigo(Assistido assistido, Usuario usuario){
		try {
			Designacoes designacao = new Designacoes();
			designacao.setAssistido(assistido);
			designacao.setAtendente(usuario);
			
			if (usuario.getGrupo().get(0) == Grupo.ASSESSOR	|| usuario.getGrupo().get(0) == Grupo.ESTAGIARIO) {
				usuario = this.usuarioBO.obterDefensorDoAssessor(usuario.getLogin());
			}
			designacao.setDefensor(usuario);
			designacao.setDataDesignacao(new Date());
			designacao.setStatus(StatusDesignacao.DESIGNADO);
			designacao.setTipoDesignacao("PENAL");
			return dao.inserir(designacao);
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
	
}
