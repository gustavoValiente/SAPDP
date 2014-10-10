package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Endereco;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class EnderecoRepositorio extends GenericDAOImpl<Endereco> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnderecoRepositorio() {
		super(Endereco.class);
	}
	
	public Endereco obterEnderecoPorNome(String rua) {
		TypedQuery<Endereco> query = getEm().createQuery(
				"select e from Endereco e where e.rua = :rua", Endereco.class);
		query.setParameter("nome", rua);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna uma lista de String com os nomes das Profissões
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarEnderecosPorNome(String rua) {
		System.out.println("DDDDDDDDDDDDDD "+rua);
		TypedQuery<String> query = getEm().createQuery("select e.rua from Endereco e where e.rua LIKE :rua",String.class);
		query.setParameter("rua", "%"+rua+"%");
		return query.getResultList();
	}
	
	
	/**
	 * Retorna uma lista de profissões, implementação LazyModel
	 * @return <code>List<Endereco></code>
	 */
	public List<Endereco> carregarEnderecosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String rua, String bairro, String cep){
		String jpql  = "select e from Endereco e where e.id > 0 ";
		//Filtros
		if(!rua.isEmpty())
			jpql += " AND e.rua LIKE :rua";
		if(!bairro.isEmpty())
			jpql += " AND e.bairro.nome LIKE :bairro";
		if(!cep.isEmpty())
			jpql += " AND e.cep LIKE :cep";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Endereco> query = getEm().createQuery(jpql, Endereco.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!rua.isEmpty())
			query.setParameter("rua", "%"+rua+"%");
		if(!bairro.isEmpty())
			query.setParameter("bairro", "%"+bairro+"%");
		if(!cep.isEmpty())
			query.setParameter("cep", "%"+cep+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Endereco>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total enderecos , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalEnderecosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String rua, String bairro, String cep){
		String jpql  = "select count(e) from Endereco e where e.id > 0 ";
		//Filtros
		if(!rua.isEmpty())
			jpql += " AND e.rua LIKE :rua";
		if(!bairro.isEmpty())
			jpql += " AND e.bairro.nome LIKE :bairro";
		if(!cep.isEmpty())
			jpql += " AND e.cep LIKE :cep";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!rua.isEmpty())
			query.setParameter("rua", "%"+rua+"%");
		if(!bairro.isEmpty())
			query.setParameter("bairro", "%"+bairro+"%");
		if(!cep.isEmpty())
			query.setParameter("cep", "%"+cep+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
	
}
