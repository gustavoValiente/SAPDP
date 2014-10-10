package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;

@Stateless
public class AssistidoPresoRepositorio extends GenericDAOImpl<AssistidoPreso> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssistidoPresoRepositorio() {
		super(AssistidoPreso.class);
	}
	
	public AssistidoPreso obterRegionalPorId(Long id) {
		TypedQuery<AssistidoPreso> query = getEm().createQuery(
				"select t from AssistidoPreso t where t.id = :id", AssistidoPreso.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
