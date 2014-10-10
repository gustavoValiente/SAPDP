package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

@LocalBean
@Stateless
public class UsuarioDAO {

	@DAO
	@Inject
	private GenericsDAO<Usuario> dao;

	public Usuario obterUsuarioPorNome(String nome) {
		try {
			String hql = "select u from Usuario u where u.nome = :pnome";
			Query q = dao.getEntityManager().createQuery(hql);
			q.setParameter("pnome", nome);
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public Usuario obterDefensorDoAssessor(String assessor) {
		TypedQuery<Usuario> query = dao.getEntityManager()
				.createQuery(
						"" + "select u from Usuario u where exists(select ass "
								+ "from u.dadosAssessoria ass "
								+ "where ass.usuario.login = :assessor)",
						Usuario.class);
		query.setParameter("assessor", assessor);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return new Usuario();
		}

	}

	public List<Usuario> listaTodos() {
		return dao.findAll();
	}

	public Usuario inserir(Usuario entity) {
		return dao.insert(entity);
	}

	public Usuario atualizar(Usuario entity) {
		return dao.update(entity);
	}

	public boolean remover(Usuario entity) {
		return dao.remove(entity);
	}

	/**
	 * Verifica disponibilidade do Login
	 * 
	 * @param login
	 * @return boolean
	 */
	public boolean verificaDisponibilidadeDeLogin(String login) {
		try {
			TypedQuery<String> query = dao.getEntityManager().createQuery(
					"select u.login from Usuario u where u.login = :login",
					String.class);
			query.setParameter("login", login);

			if (query.getResultList().size() > 0)
				return true;
			return false;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * Obter usuario por login
	 * 
	 * @param login
	 * @return Usuario
	 */
	public Usuario obterUsuarioPorLogin(String login) {
		try {
			TypedQuery<Usuario> query = dao.getEntityManager().createQuery(
					"select u from Usuario u where u.login = :login",
					Usuario.class);
			query.setParameter("login", login);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Obter usuario por nome
	 * 
	 * @param nome
	 * @return Usuario
	 */

	/**
	 * Obter usuario substituição por nome
	 * 
	 * @param nome
	 * @return Substituicao
	 */
	public Substituicao obterUsuarioSubstituicaoPorNome(String nome) {
		try {
			TypedQuery<Substituicao> query = dao
					.getEntityManager()
					.createQuery(
							"select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) from Usuario u where u.nome = :nome",
							Substituicao.class);
			query.setParameter("nome", nome);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Obter usuario substituiï¿½ï¿½o por nome
	 * 
	 * @param nome
	 * @return Substituicao
	 */
	public Substituicao obterUsuarioSubstituicaoPorLogin(String login,
			String tipoConsulta) {
		String sqlCondicao = "";
		if (tipoConsulta.equalsIgnoreCase("EU_SUBSTITUO")) {
			sqlCondicao = sqlCondicao + " u.loginSubstituicao ";
		} else {
			sqlCondicao = sqlCondicao + " u.login ";
		}
		TypedQuery<Substituicao> query = dao
				.getEntityManager()
				.createQuery(
						"select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) "
								+ "from Usuario u where "
								+ sqlCondicao
								+ " = :login", Substituicao.class);
		query.setParameter("login", login);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Filtrar usuï¿½rios
	 * 
	 * @param nome
	 * @return List<String>
	 */
	public List<String> filtrarUsuariosPorNome(String nome) {
		TypedQuery<String> query = dao
				.getEntityManager()
				.createQuery(
						"select u.nome from Usuario u where u.nome LIKE :nome and not exists( select tag from u.grupo tag where tag in :master)",
						String.class);
		query.setParameter("nome", "%" + nome + "%");
		query.setParameter("master", Grupo.MASTER);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Filtrar usuï¿½rios por unidade
	 * 
	 * @param idUnidade
	 * @return List<Usuario>
	 */
	public List<Usuario> filtrarUsuariosPorUnidade(Long idUnidade) {
		TypedQuery<Usuario> query = dao.getEntityManager().createQuery(
				"select u from Usuario u where u.unidade.id = :idUnidade",
				Usuario.class);
		query.setParameter("idUnidade", idUnidade);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Usuario>();
		}
	}

	/**
	 * Filtrar usuï¿½rios por grupo/unidade
	 * 
	 * @param nome
	 * @return List<Usuario>
	 */
	public List<Usuario> obterDefensoresParaDesignacao(Long idUnidade,
			Grupo grupo) {
		try {
			TypedQuery<Usuario> query = dao
					.getEntityManager()
					.createQuery(
							"select u from Usuario u where (u.unidade.id = :idUnidade or exists(select ass from u.dadosAssessoria ass where ass.unidade.id = :idUnidade)) "
									+ "and exists(select tag from u.grupo tag where tag in :grupo and tag <> :master)",
							Usuario.class);
			query.setParameter("idUnidade", idUnidade);
			query.setParameter("grupo", grupo);
			query.setParameter("master", Grupo.MASTER);

			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Usuario>();
		}
	}

	/**
	 * Filtrar usuï¿½rios defensor
	 * 
	 * @param nome
	 * @return List<Usuario>
	 */
	public List<Substituicao> obterDefensores(Grupo grupo) {
		try {
			TypedQuery<Substituicao> query = dao
					.getEntityManager()
					.createQuery(
							"select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) from Usuario u where exists(select tag from u.grupo tag where tag in :grupo and tag <> :master)",
							Substituicao.class);
			query.setParameter("grupo", grupo);
			query.setParameter("master", Grupo.MASTER);

			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Substituicao>();
		}
	}

	public void registraLoginSubstituicao(String loginDefensor,
			String loginDefensorSubstitui) {
		try {
			Query query = dao.getEntityManager().createQuery(
					"UPDATE Usuario u SET u.loginSubstituicao = :loginDefensorSubstitui "
							+ "WHERE u.login = :loginDefensor");
			query.setParameter("loginDefensor", loginDefensor);
			query.setParameter("loginDefensorSubstitui", loginDefensorSubstitui);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
