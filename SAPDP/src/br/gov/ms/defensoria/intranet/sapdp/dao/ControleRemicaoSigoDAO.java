package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;

@LocalBean
@Stateless
public class ControleRemicaoSigoDAO {

	@DAO
	@Inject
	private GenericsDAO<ControleRemicaoSigo> dao;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ControleRemicaoSigo obterControleRemicaoSigoPorId(Long id) {
		try {
			String hql = "select c from ControleRemicao c where c.id = :id";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("id", id);
			return (ControleRemicaoSigo) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ControleRemicaoSigo> obterControleRemicaoSigoPorAssistido(Long idAssistido, Long idGrupo){
		try{
			String hql = "select c from GrupoRemicaoSigo g, ControleRemicaoSigo c " +
						 "where g.idAssistido = :idAssistido and c.idGrupo = :idGrupo";			
			Query q = dao.getEntityManager().createQuery(hql);			
			q.setParameter("idAssistido", idAssistido);
			q.setParameter("idGrupo", idGrupo);			
			return q.getResultList();
		
		} catch (NoResultException e ) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ControleRemicaoSigo> listaTodos() {
		return dao.findAll();
	}

	public ControleRemicaoSigo inserir(ControleRemicaoSigo entity) {
		return dao.insert(entity);
	}

	public ControleRemicaoSigo atualizar(ControleRemicaoSigo entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
