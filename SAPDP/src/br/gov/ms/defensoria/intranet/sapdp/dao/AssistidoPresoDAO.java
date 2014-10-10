package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;


@Stateless
@LocalBean
public class AssistidoPresoDAO {
	
	@DAO
	@Inject
	private GenericsDAO<AssistidoPreso> dao;

	public List<AssistidoPreso> listaTodos() {
		return dao.findAll();
	}

	public AssistidoPreso inserir(AssistidoPreso entity) {
		return dao.insert(entity);
	}

	public AssistidoPreso atualizar(AssistidoPreso entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}	
	
}
