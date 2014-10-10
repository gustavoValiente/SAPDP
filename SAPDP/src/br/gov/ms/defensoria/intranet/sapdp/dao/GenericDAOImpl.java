package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.gov.ms.defensoria.intranet.sapdp.dao.interfaces.GenericDAO;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class GenericDAOImpl<T> implements Serializable, GenericDAO<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "sapDpPoll_Teste")
	private EntityManager em;

	private final Class<T> persistenceClass;

	public GenericDAOImpl(Class<T> persistenceClass) {
		this.persistenceClass = persistenceClass;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insert(T obj) {
		try {
			em.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public T update(T obj) {
		try {
			obj = em.merge(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean remove(T obj) {
		try {
			obj = em.merge(obj);
			em.remove(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public T find(Object id) {
		return em.find(getPersistenceClass(), id);
	}

	@Override
	public List<T> selectAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getPersistenceClass());
		Root<T> variableRoot = cq.from(getPersistenceClass());
		cq.select(variableRoot);
		return em.createQuery(cq).getResultList();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Class<T> getPersistenceClass() {
		return persistenceClass;
	}

}
