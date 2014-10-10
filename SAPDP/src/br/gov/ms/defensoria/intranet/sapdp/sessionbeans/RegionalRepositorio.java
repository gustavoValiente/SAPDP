package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Regional;

@Stateless
public class RegionalRepositorio extends GenericDAOImpl<Regional> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegionalRepositorio() {
		super(Regional.class);
	}
	
	public Regional obterRegionalPorNome(String nome) {
		TypedQuery<Regional> query = getEm().createQuery(
				"select r from Regional r where r.nome = :nome", Regional.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
}
