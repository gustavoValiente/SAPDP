package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;


@Stateless
@LocalBean
public class AssistidoDAO {
	
	@DAO
	@Inject
	private GenericsDAO<Assistido> dao;
	
	@EJB
	private TotalRemicaoDAO totalRemicaoDAO;
	
	public List<Assistido> listaTodos() {
		return dao.findAll();
	}

	public Assistido inserir(Assistido entity) {
		return dao.insert(entity);
	}

	public Assistido atualizar(Assistido entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}
	
	
	public AssistidoPreso obterAssistidoPreso(Long idAssistido){
		try {
			String hql = "select p from AssistidoPreso p where p.assistido.id = :idAssistido";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("idAssistido", idAssistido);
			
			return (AssistidoPreso) q.getSingleResult();
			
		} catch (NoResultException e) {
			e.printStackTrace();					
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return null;		
	}
	
	
/* ######################################### SIMULA��O SIGO ################################################### */
	
	/**
	 * filtrar campos (nome, rg, cpf, mae, pai)
	 * @return List<Assistido>
	 */
	@SuppressWarnings("unchecked")
	public List<Assistido> buscaAssistidoSIGOExisteBase(String nome, String cpf, String rg, String mae, String pai){
		String hql  = "select a from Assistido a where a.id > 0 AND (";
		Integer tam = hql.length();
		//Filtros		
		if(cpf != null && cpf != "" && !cpf.isEmpty())
			hql += " a.cpf = :cpf OR ";	
		if(rg != null && rg != "" && !rg.isEmpty())
			hql += " a.rg = :rg OR ";	
		if(mae != null && mae != "" && !mae.isEmpty())
			hql += " a.mae = :mae OR ";		
		if(pai != null && pai != "" && !pai.isEmpty())
			hql += " a.pai = :pai OR ";
		
		if(tam == hql.length())
			hql += " 1=1 ";
		
		hql = hql.substring(0, hql.length()-4);
		hql += ")";
		
		if(nome != null && nome != "" && !nome.isEmpty())
			hql += " AND a.nome LIKE :nome ";			
		System.out.println("hqlLLLLLLLLLLLLLL"+hql);
				
		Query q = dao.getEntityManager().createQuery(hql);
				
		if(nome != null && nome != "" && !nome.isEmpty())
			q.setParameter("nome", "%"+nome+"%");
		if(cpf != null && cpf != "" && !cpf.isEmpty())
			q.setParameter("cpf", cpf);
		if(rg != null && rg != "" && !rg.isEmpty())
			q.setParameter("rg", rg);
		if(mae != null && mae != "" && !mae.isEmpty())
			q.setParameter("mae", mae);		
		if(pai != null && pai != "" && !pai.isEmpty())
			q.setParameter("pai", pai);		
				
		return q.getResultList();
	}
	
	public Assistido obterAssistidoPorId(Long id){
		TypedQuery<Assistido> query = dao.getEntityManager().createQuery("select a from Assistido a where a.id = :id", Assistido.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	

}
