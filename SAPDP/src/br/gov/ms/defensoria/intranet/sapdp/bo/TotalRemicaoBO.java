package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.TotalRemicaoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.TotalRemicao;

@Stateless
@LocalBean
public class TotalRemicaoBO {

	@EJB
	private TotalRemicaoDAO dao;

	public TotalRemicao obterTotalRemicaoPorId(Long id) {
		return dao.obterTotalRemicaoPorId(id);

	}

	public List<TotalRemicao> listaTodos() {
		return dao.listaTodos();
	}

	public TotalRemicao inserir(TotalRemicao entity) {
		return dao.inserir(entity);
	}

	public TotalRemicao atualizar(TotalRemicao entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
