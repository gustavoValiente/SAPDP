package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;

@LocalBean
@Stateless
public class EstabelecimentoPenalDAO {

	@DAO
	@Inject
	private GenericsDAO<EstabelecimentoPenal> dao;

	public EstabelecimentoPenal obterEstabelecimentoPorNome(String nome) {
		try {
			String hql = "select e from EstabelecimentoPenal e where e.nome = :pnome";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("pnome", nome);
			return (EstabelecimentoPenal) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<EstabelecimentoPenal> listaTodos() {
		return dao.findAll();
	}

	public EstabelecimentoPenal inserir(EstabelecimentoPenal entity) {
		return dao.insert(entity);
	}

	public EstabelecimentoPenal atualizar(EstabelecimentoPenal entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
