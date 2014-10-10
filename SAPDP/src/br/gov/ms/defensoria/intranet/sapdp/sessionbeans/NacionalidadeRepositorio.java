package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Nacionalidade;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class NacionalidadeRepositorio extends GenericDAOImpl<Nacionalidade> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NacionalidadeRepositorio() {
		super(Nacionalidade.class);
	}
	
	/**
	 * Retorna uma lista de nacionalidades, implementação LazyModel
	 * @return <code>List<Nacionalidade></code>
	 */
	public List<Nacionalidade> carregarNacionalidadesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select n from Nacionalidade n where n.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND n.nome LIKE :nome";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Nacionalidade> query = getEm().createQuery(jpql, Nacionalidade.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Nacionalidade>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total nacionalidades , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalNacionalidadesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select count(n) from Nacionalidade n where n.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND n.nome LIKE :nome";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
	
	
	/*
	 * Retorna nacionalidade por nome
	 * @return <code>Estado</code> 
	 * */
	public Nacionalidade obterNacionalidadePorNome(String nome) {
		TypedQuery<Nacionalidade> query = getEm().createQuery(
				"select n from Nacionalidade n where n.nome = :nome", Nacionalidade.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
}
