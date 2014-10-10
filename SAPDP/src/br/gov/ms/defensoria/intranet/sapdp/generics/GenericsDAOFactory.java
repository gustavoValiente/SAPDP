package br.gov.ms.defensoria.intranet.sapdp.generics;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Fabrica de DAOs genericos responsavel pela injecao destes nos beans do CDI.
 */
@Named
public class GenericsDAOFactory {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Produces
	@DAO
	public GenericsDAO createGenericDAO(InjectionPoint injectionPoint) {
		Member mb = injectionPoint.getMember();
		GenericsDAO dao = new GenericsDAO();

		// Pega a classe do primeiro parametro (ENTITY) do atributo injetado
		// para usa-lo no construtor.
		Field field = (Field) mb;
		Type genericFieldType = field.getGenericType();
		ParameterizedType aType = (ParameterizedType) genericFieldType;
		Type[] fieldArgTypes = aType.getActualTypeArguments();
		Class fieldArgClass = (Class) fieldArgTypes[0];
		dao.setEntityManager(em);
		dao.setClazzBiz(fieldArgClass);
		return dao;
	}

}
