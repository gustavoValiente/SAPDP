package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.AssistidoPresoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;

@Stateless
@LocalBean
public class AssistidoPresoBO {
	
	@EJB
	private AssistidoPresoDAO dao;

	
	public List<AssistidoPreso> listaTodos() {
		return dao.listaTodos();
	}

	public AssistidoPreso inserir(AssistidoPreso entity) {
		return dao.inserir(entity);
	}

	public AssistidoPreso atualizar(AssistidoPreso entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}

}
