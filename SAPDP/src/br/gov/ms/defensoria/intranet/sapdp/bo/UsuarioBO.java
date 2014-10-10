package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.UsuarioDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

@Stateless
@LocalBean
public class UsuarioBO {

	@EJB
	private UsuarioDAO dao;

	public Usuario obterUsuarioPorNome(String nome) {
		return dao.obterUsuarioPorNome(nome);
	}

	public Usuario obterDefensorDoAssessor(String assessor) {
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

	public boolean remover(Usuario entity) {
		return dao.remover(entity);
	}

	public boolean verificaDisponibilidadeDeLogin(String login) {
		return dao.verificaDisponibilidadeDeLogin(login);
	}

	public Usuario obterUsuarioPorLogin(String login) {
		return dao.obterUsuarioPorLogin(login);
	}

	public Substituicao obterUsuarioSubstituicaoPorNome(String nome) {
		return dao.obterUsuarioSubstituicaoPorNome(nome);
	}

	public Substituicao obterUsuarioSubstituicaoPorLogin(String login,
			String tipoConsulta) {
		return dao.obterUsuarioSubstituicaoPorLogin(login, tipoConsulta);
	}

	public List<String> filtrarUsuariosPorNome(String nome) {
		return dao.filtrarUsuariosPorNome(nome);
	}

	public List<Usuario> filtrarUsuariosPorUnidade(Long idUnidade) {
		return dao.filtrarUsuariosPorUnidade(idUnidade);
	}

	public List<Usuario> obterDefensoresParaDesignacao(Long idUnidade,
			Grupo grupo) {
		return dao.obterDefensoresParaDesignacao(idUnidade, grupo);
	}

	public List<Substituicao> obterDefensores(Grupo grupo) {
		return dao.obterDefensores(grupo);
	}

	public void registraLoginSubstituicao(String loginDefensor,
			String loginDefensorSubstitui) {
		dao.registraLoginSubstituicao(loginDefensor, loginDefensorSubstitui);

	}

}
