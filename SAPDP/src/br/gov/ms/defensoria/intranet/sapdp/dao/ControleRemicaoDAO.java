package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;

@LocalBean
@Stateless
public class ControleRemicaoDAO {

	@DAO
	@Inject
	private GenericsDAO<ControleRemicao> dao;
	
	public ControleRemicao obterControleRemicaoPorId(Long id) {
		try {
			String hql = "select c from ControleRemicao c where c.id = :id";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("id", id);
			return (ControleRemicao) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public ControleRemicao verificaExistControleRemicao(ControleRemicaoSigo controle) {
		try {
			String hql = "SELECT c "
					+ "FROM ControleRemicao c "
					+ "WHERE c.dataInicial = :dataInicial and c.dataFim = :dataFim and c.tipoTrabalho = :tipoTrabalho ";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("dataInicial", controle.getDataInicial());
			q.setParameter("dataFim", controle.getDataFim());
			q.setParameter("tipoTrabalho", controle.getTipoTrabalho());

			return (ControleRemicao) q.getSingleResult();

		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<ControleRemicao> listaTodos() {
		return dao.findAll();
	}

	public ControleRemicao inserir(ControleRemicao entity) {
		return dao.insert(entity);
	}

	public ControleRemicao atualizar(ControleRemicao entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
