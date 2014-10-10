package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicao;

@Stateless
public class ControleRemicaoRepositorio extends GenericDAOImpl<ControleRemicao> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControleRemicaoRepositorio() {
		super(ControleRemicao.class);
	}
	
	public ControleRemicao obterControleRemicaoPorId(Long id) {
		TypedQuery<ControleRemicao> query = getEm().createQuery(
				"select c from ControleRemicao c where c.id = :id", ControleRemicao.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
