package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.TotalRemicao;

@LocalBean
@Stateless
public class TotalRemicaoDAO {

	@DAO
	@Inject
	private GenericsDAO<TotalRemicao> dao;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TotalRemicao obterTotalRemicaoPorId(Long idRemicao) {
		try {
			String hql = "select t from TotalRemicao t where t.id = :idRemicao";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("idRemicao", idRemicao);
			return (TotalRemicao) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<TotalRemicao> listaTodos() {
		return dao.findAll();
	}

	public TotalRemicao inserir(TotalRemicao entity) {
		return dao.insert(entity);
	}

	public TotalRemicao atualizar(TotalRemicao entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
