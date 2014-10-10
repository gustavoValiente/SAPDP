package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.AssistidoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class AssistidoRepositorio extends GenericDAOImpl<Assistido> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AssistidoRepositorio() {
		super(Assistido.class);
	}
	
	public Assistido obterAssistidoPorNome(String nome) {
		TypedQuery<Assistido> query = getEm().createQuery(
				"select a from Assistido a where a.nome = :nome", Assistido.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	
	/**
	 * Retorna uma lista de String com os nomes dos Assistidos
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarAssistidosPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select a.nome from Assistido a where a.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	
	/**
	 * Retorna uma lista de assistidos, implementaï¿½ï¿½o LazyModel
	 * @return <code>List<Assistido></code>
	 */
	public List<Assistido> carregarAssistidosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String mae, String pai, String cpf, String rg){
		String jpql  = "select a from Assistido a where a.id > 0 ";
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
		//Ordenaï¿½ï¿½o
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Assistido> query = getEm().createQuery(jpql, Assistido.class);
		
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
			return new ArrayList<Assistido>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total profissoes , implementaï¿½ï¿½o LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalAssistidosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String mae, String pai, String cpf, String rg){
		String jpql  = "select count(a) from Assistido a where a.id > 0 ";
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
	
	public Assistido obterAssistidoPorId(Long id){
		TypedQuery<Assistido> query = getEm().createQuery("select a from Assistido a where a.id = :id", Assistido.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
		
	/**
	 * filtrar campos (nome, rg, cpf, mae, pai)
	 * @return List<AssistidoSigo>
	 */
	public List<AssistidoSigo> buscaAssistidoSigo(String nome, String cpf, String rg, String mae, String pai){
		String jpql  = "select a from AssistidoSigo a where a.id > 0 AND (";
		Integer tam = jpql.length();
		//Filtros		
		if(cpf != null && cpf != "" && !cpf.isEmpty())
			jpql += " a.cpf = :cpf OR ";	
		if(rg != null && rg != "" && !rg.isEmpty())
			jpql += " a.rg = :rg OR ";	
		if(mae != null && mae != "" && !mae.isEmpty())
			jpql += " a.mae = :mae OR ";		
		if(pai != null && pai != "" && !pai.isEmpty())
			jpql += " a.pai = :pai OR ";
		
		if(tam == jpql.length())
			jpql += " 1=1 ";
		
		jpql = jpql.substring(0, jpql.length()-4);
		jpql += ")";
		
		if(nome != null && nome != "" && !nome.isEmpty())
			jpql += " AND a.nome LIKE :nome ";			
		
		TypedQuery<AssistidoSigo> query = getEm().createQuery(jpql, AssistidoSigo.class);
				
		if(nome != null && nome != "" && !nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(cpf != null && cpf != "" && !cpf.isEmpty())
			query.setParameter("cpf", cpf);
		if(rg != null && rg != "" && !rg.isEmpty())
			query.setParameter("rg", rg);
		if(mae != null && mae != "" && !mae.isEmpty())
			query.setParameter("mae", mae);		
		if(pai != null && pai != "" && !pai.isEmpty())
			query.setParameter("pai", pai);		
				
		return query.getResultList();
	}

	
	/* ######################################### SIMULAÇÃO SIGO ################################################### */
	
	public Long obterIdPorNomeEstabelecimentoPenal(String nome){
		TypedQuery<Long> query = getEm().createQuery("select e.id from EstabelecimentoPenal e where e.nome = :nome", Long.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	public EstabelecimentoPenal obterEstabelecimentoPorNome(String nome){
		try {
			TypedQuery<EstabelecimentoPenal> query = getEm().createQuery("select e "
					+ "from EstabelecimentoPenal e "
					+ "where e.nome = :nome", EstabelecimentoPenal.class);
			query.setParameter("nome", nome);
			return query.getSingleResult();	
		} catch (NoResultException e) {
			e.printStackTrace();					
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return null;		
	}

	public AssistidoPreso obterAssistidoPreso(Long idAssistido){
		try {
			
			TypedQuery<AssistidoPreso> query = getEm().createQuery("select p from Assistido p where p.id = :idAssistido", AssistidoPreso.class);
			query.setParameter("idAssistido", idAssistido);
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			e.printStackTrace();					
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return new AssistidoPreso();		
	}
	
	
}
