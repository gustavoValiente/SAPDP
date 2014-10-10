package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;

@LocalBean
@Stateless
public class GrupoRemicaoSigoDAO {

	@DAO
	@Inject
	private GenericsDAO<GrupoRemicaoSigo> dao;
	
	@SuppressWarnings("unchecked")	
	public List<GrupoRemicaoSigo> obterGrupoRemicaoSigoPorAssistido(Long idAssistido){
		try {			
			
			String hql = "select g from GrupoRemicaoSigo g " +						 
						 "where g.idAssistido = :idAssistido";						
			Query q = dao.getEntityManager().createQuery(hql);			
			q.setParameter("idAssistido", idAssistido);			
			return q.getResultList();
			
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	

	public List<GrupoRemicaoSigo> listaTodos() {
		return dao.findAll();
	}

	public GrupoRemicaoSigo inserir(GrupoRemicaoSigo entity) {
		return dao.insert(entity);
	}

	public GrupoRemicaoSigo atualizar(GrupoRemicaoSigo entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
