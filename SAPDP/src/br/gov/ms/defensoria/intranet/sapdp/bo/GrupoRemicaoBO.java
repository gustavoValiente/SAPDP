package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.gov.ms.defensoria.intranet.sapdp.dao.GrupoRemicaoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.TotalRemicao;

@Stateless
@LocalBean
public class GrupoRemicaoBO {

	@EJB
	private GrupoRemicaoDAO dao;

	@EJB
	private TotalRemicaoBO totalRemicaoBO;

	public List<GrupoRemicao> listaTodos() {
		return dao.listaTodos();
	}

	public GrupoRemicao inserir(GrupoRemicao entity) {
		return dao.inserir(entity);
	}

	public GrupoRemicao atualizar(GrupoRemicao entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}

	public GrupoRemicao obterGrupoRemicao(GrupoRemicaoSigo grupo, Long idPreso) {
		try {

			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
					+ grupo.getIdAssistido());

			GrupoRemicao grupoRemicao = verificaExistGrupoRemicao(grupo, idPreso);

			if (grupoRemicao == null) {

				TotalRemicao totalRemicao = new TotalRemicao();
				totalRemicao.setTempoRemicao(grupo.getTotalRemicao()
						.getTempoRemicao());
				totalRemicao.setTempoTrabalho(grupo.getTotalRemicao()
						.getTempoTrabalho());

				totalRemicao = totalRemicaoBO.inserir(totalRemicao);

				grupoRemicao = new GrupoRemicao();
				grupoRemicao.setIdAssistido(idPreso);
				grupoRemicao.setPeriodoInicio(grupo.getPeriodoInicio());
				grupoRemicao.setPeriodoFim(grupo.getPeriodoFim());
				grupoRemicao.setTotalRemicao(totalRemicao);

				grupoRemicao = this.inserir(grupoRemicao);
				return grupoRemicao;
			}
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public GrupoRemicao verificaExistGrupoRemicao(GrupoRemicaoSigo grupo, Long idPreso) {
		return dao.verificaExistGrupoRemicao(grupo, idPreso);
	}

}
