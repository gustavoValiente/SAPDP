package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;

@LocalBean
@Stateless
public class GrupoRemicaoDAO {

	@DAO
	@Inject
	private GenericsDAO<GrupoRemicao> dao;

	@EJB
	private TotalRemicaoDAO totalRemicaoDAO;
	
	public GrupoRemicao verificaExistGrupoRemicao(GrupoRemicaoSigo grupo, Long idPreso) {
		try {
			String hql = "SELECT g "
					+ "FROM GrupoRemicao g "
					+ "WHERE g.idAssistido = :idAssistido and g.periodoInicio = :periodoInicio and g.periodoFim = :periodoFim ";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("idAssistido", idPreso);
			q.setParameter("periodoInicio", grupo.getPeriodoInicio());
			q.setParameter("periodoFim", grupo.getPeriodoFim());

			return (GrupoRemicao) q.getSingleResult();

		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<GrupoRemicao> listaTodos() {
		return dao.findAll();
	}

	public GrupoRemicao inserir(GrupoRemicao entity) {
		return dao.insert(entity);
	}

	public GrupoRemicao atualizar(GrupoRemicao entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
