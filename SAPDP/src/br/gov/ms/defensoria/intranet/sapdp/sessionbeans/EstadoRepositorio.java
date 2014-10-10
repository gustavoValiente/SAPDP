package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Estado;

@Stateless
public class EstadoRepositorio extends GenericDAOImpl<Estado> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoRepositorio() {
		super(Estado.class);
	}

	public Estado obterEstadoPorNome(String nome) {
		TypedQuery<Estado> query = getEm().createQuery(
				"select e from Estado e where e.nome = :nome", Estado.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
}
