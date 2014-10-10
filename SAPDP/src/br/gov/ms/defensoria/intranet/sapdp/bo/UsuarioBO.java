package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.UsuarioDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

@Stateless
@LocalBean
public class UsuarioBO {

	@EJB
	private UsuarioDAO dao;

	public Usuario obterUsuarioPorNome(String nome) {
		return dao.obterUsuarioPorNome(nome);
	}
	
	public Usuario obterDefensorDoAssessor(String assessor){
		return dao.obterDefensorDoAssessor(assessor);		
	}
	

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario inserir(Usuario entity) {
		return dao.inserir(entity);
	}

	public Usuario atualizar(Usuario entity) {
		return dao.atualizar(entity);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}

