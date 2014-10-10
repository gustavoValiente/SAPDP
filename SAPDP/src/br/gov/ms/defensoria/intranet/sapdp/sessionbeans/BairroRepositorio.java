package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Bairro;

@Stateless
public class BairroRepositorio extends
		GenericDAOImpl<Bairro> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BairroRepositorio() {
		super(Bairro.class);
	}
	/**
	 * Retorna uma lista de String com os nomes dos Bairros
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarBairrosPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select n.nome from Bairro n where n.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	/**
	 * Retorna uma lista de String com os nomes dos Bairros e seus Municípios
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarBairrosMunicipiosPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select n.nome +' - '+ n.municipio.nome from Bairro n where n.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	/**
	 * Retorna um objeto Bairro
	 * @param query - String
	 * @return Bairro
	 */
	public Bairro buscarBairroPorNome(String nome) {
		TypedQuery<Bairro> query = getEm().createQuery("select n from Bairro n where n.nome = :nome",Bairro.class);
		query.setParameter("nome", nome);		
		try{			
			return query.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}			
	}
	
	/**
	 * Retorna um objeto Bairro
	 * @param query - String Bairro e String Municipio
	 * @return Bairro
	 */
	public Bairro buscarBairroPorNomeEMunicipio(String nomeBairro, String nomeMunicipio) {
		TypedQuery<Bairro> query = getEm().createQuery("select n from Bairro n where n.nome = :nome and n.municipio.nome = :municipio",Bairro.class);
		query.setParameter("nome", nomeBairro);		
		query.setParameter("municipio", nomeMunicipio);
		try{			
			return query.getSingleResult();
		}catch (NoResultException nre){
			return null;
		}			
	}
	
	/**
	 * Retorna uma lista de profissões, implementação LazyModel
	 * @return <code>List<Profissao></code>
	 */
	public List<Bairro> carregarBairrosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio, Long idMunicipio){
		String jpql  = "select m from Bairro m where m.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND m.nome LIKE :nome";
		if(!municipio.isEmpty())
			jpql += " AND m.municipio.nome LIKE :municipio";		
		if(idMunicipio != null)
			jpql += " AND m.municipio.id = :idMunicipio";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Bairro> query = getEm().createQuery(jpql, Bairro.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!municipio.isEmpty())
			query.setParameter("municipio", "%"+municipio+"%");		
		if(idMunicipio != null)
			query.setParameter("idMunicipio", idMunicipio);
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Bairro>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total profissoes , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalBairrosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String municipio, Long idMunicipio){
		String jpql  = "select count(m) from Bairro m where m.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND m.nome LIKE :nome";
		if(!municipio.isEmpty())
			jpql += " AND m.municipio.nome LIKE :municipio";		
		if(idMunicipio != null)
			jpql += " AND m.municipio.id = :idMunicipio";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!municipio.isEmpty())
			query.setParameter("municipio", "%"+municipio+"%");		
		if(idMunicipio != null)
			query.setParameter("idMunicipio", idMunicipio);
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
}
