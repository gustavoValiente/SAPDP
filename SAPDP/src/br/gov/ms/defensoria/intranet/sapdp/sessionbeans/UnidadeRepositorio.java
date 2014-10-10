package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class UnidadeRepositorio extends GenericDAOImpl<Unidade> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnidadeRepositorio() {
		super(Unidade.class);
	}

	/**
	 * Retorna uma lista de unidades, implementação LazyModel
	 * 
	 * @return <code>List<Unidade></code>
	 */
	public List<Unidade> carregarUnidadesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio) {
		String jpql = "select u from Unidade u where u.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND u.nome LIKE :nome";
		if (!municipio.isEmpty())
			jpql += " AND u.municipioDistrito.nome LIKE :municipio";
		// Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<Unidade> query = getEm().createQuery(jpql, Unidade.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");
		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Unidade>();
		return query.getResultList();
	}

	/**
	 * Retorna total unidades , implementação LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotalUnidadesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio) {
		String jpql = "select count(u) from Unidade u where u.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND u.nome LIKE :nome";
		if (!municipio.isEmpty())
			jpql += " AND u.municipioDistrito.nome LIKE :municipio";

		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);

		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");
		return query.getSingleResult().intValue();
	}

	/**
	 * 
	 * @return List<Unidade> - Lista de unidades do municipio
	 */
	public List<Unidade> carregarUnidadePorMunicipio(Long id) {
		String jpql = "select u from Unidade u where u.municipioDistrito.id = :id ";
		TypedQuery<Unidade> query = getEm().createQuery(jpql, Unidade.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	/**
	 * 
	 * @param nome - String 
	 * @return Unidade
	 */
	public Unidade obterUnidadePorNome(String nome) {
		String jpql = "select u from Unidade u where u.nome = :nome ";
		TypedQuery<Unidade> query = getEm().createQuery(jpql, Unidade.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	/**
	 * 
	 * @param id - Long
	 * @return Unidade
	 */
	public Unidade obterUnidadePorId(Long id) {
		String jpql = "select u from Unidade u where u.id = :id ";
		TypedQuery<Unidade> query = getEm().createQuery(jpql, Unidade.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
}
