package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.ProcessoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Processo;


@Stateless
@LocalBean
public class ProcessoBO {

	@EJB
	private ProcessoDAO dao;

	public List<Processo> listaTodos() {
		return dao.listaTodos();
	}

	public Processo inserir(Processo entity) {
		return dao.inserir(entity);
	}

	public Processo atualizar(Processo entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
