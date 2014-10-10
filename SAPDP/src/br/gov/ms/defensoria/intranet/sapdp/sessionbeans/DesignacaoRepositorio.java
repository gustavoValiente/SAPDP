package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class DesignacaoRepositorio extends GenericDAOImpl<Designacoes> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar c1;
	private Calendar c2;

	public DesignacaoRepositorio() {
		super(Designacoes.class);
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

	public List<SimpleDesignacao> filtrarDesignacoesPorDefensorData(
			String defensor) throws ParseException {

		normalizaDatasAtuais();
		// atendimentos entre a 00:00 da data inicial passada como parametro
		// System.out.println(">>>>>>>>>>>"+defensor);
		// System.out.println(">>>>>>>>>>>"+ c1.getTime() + " -- "+
		// c2.getTime());

		TypedQuery<SimpleDesignacao> query = getEm()
				.createQuery(
						"select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleDesignacao(d.id, d.assistido.nome, "
								+ "d.defensor.login, d.preferencial, d.dataDesignacao, d.nucleo.nome, d.status, d.idAtendimentoPai, "
								+ "d.loginSubstituicao) "
								+ "from Designacoes d where (d.defensor.login = :defensor OR d.loginSubstituicao = :defensor)"
								+ "and d.dataDesignacao BETWEEN :dataInicio and :dataFim "
								+ "and (d.status <> :status)",
						SimpleDesignacao.class);
		query.setParameter("defensor", defensor);
		query.setParameter("dataInicio", c1.getTime());
		query.setParameter("dataFim", c2.getTime());
		query.setParameter("status", StatusDesignacao.CONCLUIDO);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<SimpleDesignacao>();
		}
	}

	/**
	 * Retorna uma lista de designacoes do dia, implementação LazyModel
	 * 
	 * @return <code>List<Designacoes></code>
	 */
	public List<Designacoes> carregarDesignacoesLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order, String assistido,
			String nucleo, String defensor, String atendente,
			String preferencial, String status) {
		normalizaDatasAtuais();
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
		// Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<Designacoes> query = getEm().createQuery(jpql,
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

		query.setParameter("dataInicio", c1.getTime());
		query.setParameter("dataFim", c2.getTime());

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Designacoes>();
		return query.getResultList();
	}

	/**
	 * Retorna o total de designacoes do dia, implementação LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotallDesignacoesLazy(String assistido, String nucleo,
			String defensor, String atendente, String preferencial,
			String status) {

		normalizaDatasAtuais();
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

		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);

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

		query.setParameter("dataInicio", c1.getTime());
		query.setParameter("dataFim", c2.getTime());

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}

	/**
	 * Obter Designação pelo Id
	 * @param idDesignacao
	 * @return Designacoes
	 */
	public Designacoes obterDesignacaoPorId(Long idDesignacao){
		TypedQuery<Designacoes> query = getEm().createQuery("select d from Designacoes d where d.id = :idDesignacao", Designacoes.class);
		query.setParameter("idDesignacao", idDesignacao);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return new Designacoes();
		}
	}
	
	public void alterarStatusDesignacoes(Long id, StatusDesignacao status){
		Query query = getEm().createQuery("UPDATE Designacoes d SET d.status = :status WHERE d.id = :id");
		query.setParameter("id", id);
		query.setParameter("status", status);
		query.executeUpdate();
	}
}
