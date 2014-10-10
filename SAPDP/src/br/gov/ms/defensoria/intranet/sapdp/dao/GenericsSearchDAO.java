package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.util.SimpleValidate;

@Stateless
public class GenericsSearchDAO {

	@DAO
	@Inject
	private GenericsDAO<IGenericEntity> dao;

	@SuppressWarnings("unchecked")
	public List<IGenericEntity> carregarPesquisaLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros, IGenericEntity entity) {

		String jpql = "SELECT entity FROM " + entity.getClass().getName()
				+ " entity where entity.id > 0";

		// Filtros
		Set<String> listParametros = parametros.keySet();

		int indice = 0;
		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			String args = "args_" + indice;
			Object valueArgs = parametros.get(campo);
			if(valueArgs.getClass().equals(String.class)){
				jpql += " AND entity." + campo + " LIKE :" + args;
			} else {
				jpql += " AND entity." + campo + " = :" + args;
			}
			
			indice++;
		}

		// Ordenacao
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		Query q = dao.getEntityManager().createQuery(jpql);

		q.setFirstResult(startingAt);
		q.setMaxResults(maxPerPage);

		indice = 0;
		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			String args = "args_" + indice;
			Object valueArgs = parametros.get(campo);
			if(valueArgs.getClass().equals(String.class)){
				q.setParameter(args, "%" + valueArgs.toString() + "%");
			} else{
				q.setParameter(args, valueArgs);
			}
			
		}

		List<IGenericEntity> entities = q.getResultList();
		if (SimpleValidate.isNullOrEmpty(entities)) {
			return new ArrayList<IGenericEntity>();
		}
		return entities;

	}

	@SuppressWarnings("unchecked")
	public List<IGenericEntity> carregarPesquisaUsuarioLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros) {
		

		String jpql = "SELECT entity FROM Usuario entity where entity.login <> 'master' ";

		// Filtros
		Set<String> listParametros = parametros.keySet();
		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			jpql += " AND entity." + campo + " LIKE :" + campo;
		}

		// Ordenacao
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		Query q = dao.getEntityManager().createQuery(jpql);

		q.setFirstResult(startingAt);
		q.setMaxResults(maxPerPage);

		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			q.setParameter(campo, "%" + parametros.get(campo) + "%");
		}

		List<IGenericEntity> entities = q.getResultList();
		if (SimpleValidate.isNullOrEmpty(entities)) {
			return new ArrayList<IGenericEntity>();
		}
		return entities;

	}

}
