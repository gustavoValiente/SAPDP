package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.ControleRemicaoSigoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.ControleRemicaoSigo;

@Stateless
@LocalBean
public class ControleRemicaoSigoBO {

	@EJB
	private ControleRemicaoSigoDAO dao;

	public ControleRemicaoSigo obterControleRemicaoPorId(Long id) {
		return dao.obterControleRemicaoSigoPorId(id);

	}
	
	public List<ControleRemicaoSigo> obterControleRemicaoSigoPorAssistido(Long idAssistido, Long idGrupo){
		return dao.obterControleRemicaoSigoPorAssistido(idAssistido, idGrupo);
	}


	public List<ControleRemicaoSigo> listaTodos() {
		return dao.listaTodos();
	}

	public ControleRemicaoSigo inserir(ControleRemicaoSigo entity) {
		return dao.inserir(entity);
	}

	public ControleRemicaoSigo atualizar(ControleRemicaoSigo entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
