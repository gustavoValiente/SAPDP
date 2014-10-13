package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Processo;


@LocalBean
@Stateless
public class ProcessoDAO {

	@DAO
	@Inject
	private GenericsDAO<Processo> dao;



	public List<Processo> listaTodos() {
		return dao.findAll();
	}

	public Processo inserir(Processo entity) {
		return dao.insert(entity);
	}

	public Processo atualizar(Processo entity) {
		return dao.update(entity);
	}

	public void remover(Long id) {
		dao.remove(id);
	}

}
