package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.GrupoRemicaoSigoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.GrupoRemicaoSigo;

@Stateless
@LocalBean
public class GrupoRemicaoSigoBO {

	@EJB
	private GrupoRemicaoSigoDAO dao;

	public List<GrupoRemicaoSigo> listaTodos() {
		return dao.listaTodos();
	}

	public GrupoRemicaoSigo inserir(GrupoRemicaoSigo entity) {
		return dao.inserir(entity);
	}

	public GrupoRemicaoSigo atualizar(GrupoRemicaoSigo entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
	
	public List<GrupoRemicaoSigo> obterGrupoRemicaoSigoPorAssistido(Long idAssistido){
		return dao.obterGrupoRemicaoSigoPorAssistido(idAssistido);
	}	
	
	
}
