package br.gov.ms.defensoria.intranet.sapdp.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014.
 * 
 */
@Local
public interface GenericDAO<T> {
	public void insert(T obj);

	public T update(T obj);

	public boolean remove(T obj);

	public T find(Object id);

	public List<T> selectAll();
}
