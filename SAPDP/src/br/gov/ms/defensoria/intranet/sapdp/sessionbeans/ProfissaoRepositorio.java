package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Profissao;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class ProfissaoRepositorio extends GenericDAOImpl<Profissao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProfissaoRepositorio() {
		super(Profissao.class);
	}
	
	public Profissao obterProfissaoPorNome(String nome) {
		TypedQuery<Profissao> query = getEm().createQuery(
				"select p from Profissao p where p.nome = :nome", Profissao.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna uma lista de String com os nomes das Profissões
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarProfissoesPorNome(String nome) {
		System.out.println("DDDDDDDDDDDDDD "+nome);
		TypedQuery<String> query = getEm().createQuery("select p.nome from Profissao p where p.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	
	/**
	 * Retorna uma lista de profissões, implementação LazyModel
	 * @return <code>List<Profissao></code>
	 */
	public List<Profissao> carregarProfissoesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select p from Profissao p where p.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND p.nome LIKE :nome";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Profissao> query = getEm().createQuery(jpql, Profissao.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Profissao>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total profissoes , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalProfissoesLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome){
		String jpql  = "select count(p) from Profissao p where p.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND p.nome LIKE :nome";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
}
