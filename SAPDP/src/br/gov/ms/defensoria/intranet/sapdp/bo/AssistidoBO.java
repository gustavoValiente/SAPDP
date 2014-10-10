package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.AssistidoDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso.AssistidoPreso;

@Stateless
@LocalBean
public class AssistidoBO {
	
	@EJB
	private AssistidoDAO dao;

	
	public List<Assistido> listaTodos() {
		return dao.listaTodos();
	}

	public Assistido inserir(Assistido entity) {
		return dao.inserir(entity);
	}

	public Assistido atualizar(Assistido entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
	
	public AssistidoPreso obterAssistidoPreso(Long idAssistido){
		return dao.obterAssistidoPreso(idAssistido);		
	}
	
	public List<Assistido> buscaAssistidoSIGOExisteBase(String nome, String cpf, String rg, String mae, String pai){		
		return dao.buscaAssistidoSIGOExisteBase(nome, cpf, rg, mae, pai);
	}

	public Assistido obterAssistidoPorId(Long id){
		return dao.obterAssistidoPorId(id);
	}
	
}
