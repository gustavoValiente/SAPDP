package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.TotalRemicao;

@Stateless
public class TotalRemicaoRepositorio extends GenericDAOImpl<TotalRemicao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TotalRemicaoRepositorio() {
		super(TotalRemicao.class);
	}
	
	public TotalRemicao obterRegionalPorId(Long id) {
		TypedQuery<TotalRemicao> query = getEm().createQuery(
				"select t from TotalRemicao t where t.id = :id", TotalRemicao.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
