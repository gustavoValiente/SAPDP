package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.EstabelecimentoPenalDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.EstabelecimentoPenal;

@Stateless
@LocalBean
public class EstabelecimentoPenalBO {

	@EJB
	private EstabelecimentoPenalDAO dao;

	public EstabelecimentoPenal obterEstabelecimentoPorNome(String nome) {
		return dao.obterEstabelecimentoPorNome(nome);

	}

	public List<EstabelecimentoPenal> listaTodos() {
		return dao.listaTodos();
	}

	public EstabelecimentoPenal inserir(EstabelecimentoPenal entity) {
		return dao.inserir(entity);
	}

	public EstabelecimentoPenal atualizar(EstabelecimentoPenal entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
