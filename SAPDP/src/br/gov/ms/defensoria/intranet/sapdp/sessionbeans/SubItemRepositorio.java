package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class SubItemRepositorio extends GenericDAOImpl<SubItemAtendimento> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubItemRepositorio() {
		super(SubItemAtendimento.class);
	}

	/**
	 * Retorna uma lista de subitens, implementação LazyModel
	 * 
	 * @return <code>List<SubItemAtendimento></code>
	 */
	public List<SubItemAtendimento> carregarSubItensLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order, String nome) {
		String jpql = "select i from SubItemAtendimento i where i.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND i.nome LIKE :nome";

		// Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<SubItemAtendimento> query = getEm().createQuery(jpql,
				SubItemAtendimento.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<SubItemAtendimento>();
		return query.getResultList();
	}

	/**
	 * Retorna total subitens , implementação LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotalSubItensLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome) {
		String jpql = "select count(i) from SubItemAtendimento i where i.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND i.nome LIKE :nome";

		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);

		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");

		return query.getSingleResult().intValue();
	}

	/**
	 * 
	 * @param nome
	 * @return SubItemAtendimento
	 */
	public SubItemAtendimento obterSubItemPorNome(String nome) {
		TypedQuery<SubItemAtendimento> query = getEm().createQuery(
				"select i from SubItemAtendimento i where i.nome = :nome",
				SubItemAtendimento.class);
		query.setParameter("nome", nome);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Retorna um SubItem com os itens carregados
	  * @return SubItem
	 */
	public List<ItemAtendimento> obterListaDeItensPorSubItem(Long idSubItem) {
		TypedQuery<ItemAtendimento> query = getEm().createQuery("select i from SubItemAtendimento s inner join s.itens i where s.id = :idSubItem ",ItemAtendimento.class);
		query.setParameter("idSubItem", idSubItem);
		return query.getResultList();
	}
	
	/**
	 * Retorna um SubItem com os itens carregados
	  * @return SubItem
	 */
	public List<SubItemAtendimento> obterListaDeSubItensPorItem(Long idItem) {
		TypedQuery<SubItemAtendimento> query = getEm().createQuery("select s from SubItemAtendimento s inner join s.itens i where i.id = :idItem ",
				SubItemAtendimento.class);
		query.setParameter("idItem", idItem);
		return query.getResultList();
	}
	

}
