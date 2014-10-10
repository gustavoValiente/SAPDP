package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.ControleRemicaoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicao;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;

@Stateless
@LocalBean
public class ControleRemicaoBO {

	@EJB
	private ControleRemicaoDAO dao;

	public ControleRemicao obterControleRemicaoPorId(Long id) {
		return dao.obterControleRemicaoPorId(id);

	}

	public List<ControleRemicao> listaTodos() {
		return dao.listaTodos();
	}

	public ControleRemicao inserir(ControleRemicao entity) {
		return dao.inserir(entity);
	}

	public ControleRemicao atualizar(ControleRemicao entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
	
	public ControleRemicao verificaExistControleRemicao(ControleRemicaoSigo controle) {
		return dao.verificaExistControleRemicao(controle);
	}
}
