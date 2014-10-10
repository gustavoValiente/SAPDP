package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.AssistidoSigo;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class AssistidoSigoRepositorio extends GenericDAOImpl<AssistidoSigo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssistidoSigoRepositorio() {
		super(AssistidoSigo.class);
	}

	public AssistidoSigo obterAssistidoSigoPorNome(String nome) {
		TypedQuery<AssistidoSigo> query = getEm().createQuery(
				"select a from AssistidoSigo a where a.nome = :nome", AssistidoSigo.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	
	/**
	 * Retorna uma lista de String com os nomes dos AssistidoSigos
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarAssistidosSigoPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select a.nome from AssistidoSigo a where a.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	
	/**
	 * Retorna uma lista de assistidos, implementação LazyModel
	 * @return <code>List<AssistidoSigo></code>
	 */
	public List<AssistidoSigo> carregarAssistidosSigoLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String mae, String pai, String cpf, String rg){
		String jpql  = "select a from AssistidoSigo a where a.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND a.nome LIKE :nome";
		if(!mae.isEmpty())
			jpql += " AND a.mae LIKE :mae";		
		if(!pai.isEmpty())
			jpql += " AND a.pai LIKE :pai";		
		if(!cpf.isEmpty())
			jpql += " AND a.cpf LIKE :cpf";
		if(!rg.isEmpty())
			jpql += " AND a.rg LIKE :rg";	
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<AssistidoSigo> query = getEm().createQuery(jpql, AssistidoSigo.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!mae.isEmpty())
			query.setParameter("mae", "%"+mae+"%");		
		if(!pai.isEmpty())
			query.setParameter("pai", "%"+pai+"%");		
		if(!cpf.isEmpty())
			query.setParameter("cpf", "%"+cpf+"%");
		if(!rg.isEmpty())
			query.setParameter("rg", "%"+rg+"%");
		
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<AssistidoSigo>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total profissoes , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalAssistidosSigoLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String mae, String pai, String cpf, String rg){
		String jpql  = "select count(a) from AssistidoSigo a where a.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND a.nome LIKE :nome";
		if(!mae.isEmpty())
			jpql += " AND a.mae LIKE :mae";		
		if(!pai.isEmpty())
			jpql += " AND a.pai LIKE :pai";		
		if(!cpf.isEmpty())
			jpql += " AND a.cpf LIKE :cpf";		
		if(!rg.isEmpty())
			jpql += " AND a.rg LIKE :rg";	
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!mae.isEmpty())
			query.setParameter("mae", "%"+mae+"%");		
		if(!pai.isEmpty())
			query.setParameter("pai", "%"+pai+"%");		
		if(!cpf.isEmpty())
			query.setParameter("cpf", "%"+cpf+"%");
		if(!rg.isEmpty())
			query.setParameter("rg", "%"+rg+"%");
		
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
	
	public AssistidoSigo obterAssistidoSigoPorId(Long id){
		TypedQuery<AssistidoSigo> query = getEm().createQuery("select a from AssistidoSigo a where a.id = :id", AssistidoSigo.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
}
