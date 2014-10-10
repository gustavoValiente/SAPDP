package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;


@Stateless
@LocalBean
public class DesignacaoDAO {
	
	@DAO
	@Inject
	private GenericsDAO<Designacoes> dao;
		
	
	public List<Designacoes> listaTodos() {
		return dao.findAll();
	}

	public Designacoes inserir(Designacoes entity) {
		return dao.insert(entity);
	}

	public Designacoes atualizar(Designacoes entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}
	
	/**
	 * Obter Designação pelo Id
	 * @param idDesignacao
	 * @return Designacoes
	 */
	public Designacoes obterDesignacaoPorId(Long idDesignacao){
		TypedQuery<Designacoes> query = dao.getEntityManager().createQuery("select d from Designacoes d where d.id = :idDesignacao", 
				Designacoes.class);
		query.setParameter("idDesignacao", idDesignacao);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return new Designacoes();
		}
	}
	
	public void alterarStatusDesignacoes(Long id, StatusDesignacao status){
		Query query = dao.getEntityManager().createQuery("UPDATE Designacoes d SET d.status = :status WHERE d.id = :id");
		query.setParameter("id", id);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorData(
			String defensor, Date dataInicio, Date dataFim) throws ParseException {

		TypedQuery<SimpleDesignacao> query = dao.getEntityManager()
				.createQuery(
						"select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao(d.id, d.assistido.nome, "
								+ "d.defensor.login, d.preferencial, d.dataDesignacao, d.nucleo.nome, d.status, d.idAtendimentoPai, "
								+ "d.loginSubstituicao) "
								+ "from Designacoes d where (d.defensor.login = :defensor OR d.loginSubstituicao = :defensor)"
								+ "and d.dataDesignacao BETWEEN :dataInicio and :dataFim "
								+ "and (d.status <> :status)",
						SimpleDesignacao.class);
		query.setParameter("defensor", defensor);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		query.setParameter("status", StatusDesignacao.CONCLUIDO);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<SimpleDesignacao>();
		}
	}
		
	public List<Designacoes> carregarDesignacoesLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order, String assistido,
			String nucleo, String defensor, String atendente,
			String preferencial, String status, Date dataInicio, Date dataFim) {
		
		String jpql = "select d from Designacoes d where d.dataDesignacao BETWEEN :dataInicio and :dataFim ";
		// Filtros
		if (!assistido.isEmpty())
			jpql += " AND d.assistido.nome LIKE :assistido";
		if (!nucleo.isEmpty())
			jpql += " AND d.nucleo.nome LIKE :nucleo";
		if (!defensor.isEmpty())
			jpql += " AND d.defensor.nome LIKE :defensor";
		if (!atendente.isEmpty())
			jpql += " AND d.atendente.nome LIKE :atendente";
		if (!preferencial.isEmpty())
			jpql += " AND d.preferencial = :preferencial";
		if (!status.isEmpty())
			jpql += " AND d.status = :status";
		// Ordena��o
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<Designacoes> query = dao.getEntityManager().createQuery(jpql,
				Designacoes.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!assistido.isEmpty())
			query.setParameter("assistido", "%" + assistido + "%");
		if (!nucleo.isEmpty())
			query.setParameter("nucleo", "%" + nucleo + "%");
		if (!defensor.isEmpty())
			query.setParameter("defensor", "%" + defensor + "%");
		if (!atendente.isEmpty())
			query.setParameter("atendente", "%" + atendente + "%");
		if (!preferencial.isEmpty())
			query.setParameter("preferencial", preferencial);
		if (!status.isEmpty())
			query.setParameter("status", StatusDesignacao.valueOf(status));

		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Designacoes>();
		return query.getResultList();
	}
	
	public int carregarTotallDesignacoesLazy(String assistido, String nucleo,
			String defensor, String atendente, String preferencial,
			String status, Date dataInicio, Date dataFim) {

	
		String jpql = "select count(d) from Designacoes d where d.dataDesignacao BETWEEN :dataInicio and :dataFim ";
		// Filtros
		if (!assistido.isEmpty())
			jpql += " AND d.assistido.nome LIKE :assistido";
		if (!nucleo.isEmpty())
			jpql += " AND d.nucleo.nome LIKE :nucleo";
		if (!defensor.isEmpty())
			jpql += " AND d.defensor.nome LIKE :defensor";
		if (!atendente.isEmpty())
			jpql += " AND d.atendente.nome LIKE :atendente";
		if (!preferencial.isEmpty())
			jpql += " AND d.preferencial = :preferencial";
		if (!status.isEmpty())
			jpql += " AND d.status = :status";

		TypedQuery<Long> query = dao.getEntityManager().createQuery(jpql, Long.class);

		if (!assistido.isEmpty())
			query.setParameter("assistido", "%" + assistido + "%");
		if (!nucleo.isEmpty())
			query.setParameter("nucleo", "%" + nucleo + "%");
		if (!defensor.isEmpty())
			query.setParameter("defensor", "%" + defensor + "%");
		if (!atendente.isEmpty())
			query.setParameter("atendente", "%" + atendente + "%");
		if (!preferencial.isEmpty())
			query.setParameter("preferencial", preferencial);
		if (!status.isEmpty())
			query.setParameter("status", StatusDesignacao.valueOf(status));

		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}

}
