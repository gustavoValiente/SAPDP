package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class ItemRepositorio extends GenericDAOImpl<ItemAtendimento> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemRepositorio() {
		super(ItemAtendimento.class);
	}

	/**
	 * Retorna uma lista de itens, implementação LazyModel
	 * 
	 * @return <code>List<ItemAtendimento></code>
	 */
	public List<ItemAtendimento> carregarItensLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order, String nome) {
		String jpql = "select i from ItemAtendimento i where i.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND i.nome LIKE :nome";
		
		// Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<ItemAtendimento> query = getEm().createQuery(jpql,
				ItemAtendimento.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");

		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<ItemAtendimento>();
		return query.getResultList();
	}

	/**
	 * Retorna total itens , implementação LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotalItensLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome) {
		String jpql = "select count(i) from ItemAtendimento i where i.id > 0 ";
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
	 * @return ItemAtendimento
	 */
	public ItemAtendimento obterItemPorNome(String nome) {
		TypedQuery<ItemAtendimento> query = getEm().createQuery(
				"select i from ItemAtendimento i where i.nome = :nome",
				ItemAtendimento.class);
		query.setParameter("nome", nome);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * obter lista de itens
	 * @return List<ItemAtendimento>
	 */
	public List<ItemAtendimento> obterListaDeItens(){
		TypedQuery<ItemAtendimento> query = getEm().createQuery("select i from ItemAtendimento i ",
				ItemAtendimento.class);		
		return query.getResultList();
	}
		
	
	/**
	 * Retorna uma Item com os nucleos carregados
	  * @return Item
	 */
	public List<Nucleo> obterListaDeNucleosPorItem(Long idItem) {
		TypedQuery<Nucleo> query = getEm().createQuery("select n from ItemAtendimento i inner join i.nucleos n where i.id = :idItem ",Nucleo.class);
		query.setParameter("idItem", idItem);
		return query.getResultList();
	}
	
	
	/**
	 * Retorna lista de Itens por Núcleo
	  * @return List<ItemAtendimento>
	 */
	public List<ItemAtendimento> obterListaDeItensPorNucleo(Long idNucleo) {
		TypedQuery<ItemAtendimento> query = getEm().createQuery("select i from ItemAtendimento i inner join i.nucleos n where n.id = :idNucleo ",
				ItemAtendimento.class);
		query.setParameter("idNucleo", idNucleo);
		return query.getResultList();
	}
	
	/**
	 * Retorna uma lista de String com os nomes dos Itens
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarItensPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select i.nome from ItemAtendimento i where i.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	/**
	 * Retorna um objeto Item
	 * @param query - String
	 * @return Item
	 */
	public ItemAtendimento buscarItemPorNome(String nome) {
		TypedQuery<ItemAtendimento> query = getEm().createQuery("select i from ItemAtendimento i where i.nome = :nome",ItemAtendimento.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	

}
