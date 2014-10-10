package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Defensoria;

@Stateless
public class DefensoriaRepositorio extends GenericDAOImpl<Defensoria> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefensoriaRepositorio() {
		super(Defensoria.class);
	}
	
	public Defensoria obterDefensoriaPorNome(String nome) {		
		TypedQuery<Defensoria> query = getEm().createQuery(
				"select d from Defensoria d where d.nome = :nome", Defensoria.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	public Defensoria obterDefensoriaPorId(Long id) {		
		TypedQuery<Defensoria> query = getEm().createQuery(
				"select d from Defensoria d where d.id = :id", Defensoria.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	

	/**
	 * Retorna uma lista de defensorias, implementação LazyModel
	 * 
	 * @return <code>List<Defensoria></code>
	 */
	public List<Defensoria> carregarDefensoriasLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio) {
		String jpql = "select d from Defensoria d where d.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND d.nome LIKE :nome";
		if (!municipio.isEmpty())
			jpql += " AND d.municipioDistrito.nome LIKE :municipio";
		
		// Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<Defensoria> query = getEm().createQuery(jpql, Defensoria.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");
		
		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Defensoria>();
		return query.getResultList();
	}

	/**
	 * Retorna total unidades , implementação LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotalUnidadesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio) {
		String jpql = "select count(d) from Defensoria d where d.id > 0 ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND d.nome LIKE :nome";
		if (!municipio.isEmpty())
			jpql += " AND d.municipioDistrito.nome LIKE :municipio";

		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);

		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");

		return query.getSingleResult().intValue();
	}
}
