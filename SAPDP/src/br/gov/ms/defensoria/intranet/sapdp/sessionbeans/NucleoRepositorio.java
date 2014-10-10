package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class NucleoRepositorio extends GenericDAOImpl<Nucleo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NucleoRepositorio() {
		super(Nucleo.class);
	}
	
	/**
	 * Retorna uma lista de String com os nomes dos Nucleos
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarNucleosPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select n.nome from Nucleo n where n.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	/**
	 * lista de String com os nomes dos Nucleos
	 * @return String
	 */
	public List<String> filtrarNucleosPorNome() {
		TypedQuery<String> query = getEm().createQuery("select n.nome from Nucleo n",String.class);
		return query.getResultList();
	}
	
	/**
	 * Retorna uma lista de Nucleos
	  * @return List<Nucleo>
	 */
	public List<Nucleo> obterListaDeNucleos() {
		TypedQuery<Nucleo> query = getEm().createQuery("select n from Nucleo n",Nucleo.class);
		return query.getResultList();
	}

	/**
	 * Retorna um objeto Nucleo
	 * @param query - String
	 * @return Nucleo
	 */
	public Nucleo buscarNucleoPorNome(String nome) {
		TypedQuery<Nucleo> query = getEm().createQuery("select n from Nucleo n where n.nome = :nome",Nucleo.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna uma lista de nucleos, implementação LazyModel
	 * @return <code>List<Nucleo></code>
	 */
	public List<Nucleo> carregarNucleoLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select n from Nucleo n where n.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND n.nome LIKE :nome";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Nucleo> query = getEm().createQuery(jpql, Nucleo.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Nucleo>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total nucleos , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalNucleoLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select count(n) from Nucleo n where n.id > 0 ";
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
	 * Retorna nacionalidades por nome
	 * @return <code>Estado</code> 
	 * */
	public Nucleo obterNucleoPorNome(String nome) {
		TypedQuery<Nucleo> query = getEm().createQuery(
				"select n from Nucleo n where n.nome = :nome", Nucleo.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
}
