package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Grupo;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Stateless
public class UsuarioRepositorio extends GenericDAOImpl<Usuario> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioRepositorio() {
		super(Usuario.class);
	}

	/**
	 * Retorna uma lista de usuarios, implementa��o LazyModel
	 * 
	 * @return <code>List<Usuario></code>
	 */
	public List<Usuario> carregarUsuarioLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String login,
			String municipio, String unidade) {
		String jpql = "select u from Usuario u where u.login <> 'master' ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND u.nome LIKE :nome";
		if (!login.isEmpty())
			jpql += " AND u.login LIKE :login";
		if (!municipio.isEmpty())
			jpql += " AND u.municipioDistrito.nome LIKE :municipio";
		if (!unidade.isEmpty())
			jpql += " AND u.unidade.nome LIKE :unidade";
		// Ordena��o
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;

		TypedQuery<Usuario> query = getEm().createQuery(jpql, Usuario.class);

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!login.isEmpty())
			query.setParameter("login", "%" + login + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");
		if (!unidade.isEmpty())
			query.setParameter("unidade", "%" + unidade + "%");
		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Usuario>();
		return query.getResultList();
	}

	/**
	 * Retorna total usu�rios , implementa��o LazyModel
	 * 
	 * @return <code>int</code>
	 */
	public int carregarTotalUsuariosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String login,
			String municipio, String unidade) {
		String jpql = "select count(u) from Usuario u where u.login <> 'master' ";
		// Filtros
		if (!nome.isEmpty())
			jpql += " AND u.nome LIKE :nome";
		if (!login.isEmpty())
			jpql += " AND u.login LIKE :login";
		if (!municipio.isEmpty())
			jpql += " AND u.municipioDistrito.nome LIKE :municipio";
		if (!unidade.isEmpty())
			jpql += " AND u.unidade.nome LIKE :unidade";

		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);

		if (!nome.isEmpty())
			query.setParameter("nome", "%" + nome + "%");
		if (!login.isEmpty())
			query.setParameter("login", "%" + login + "%");
		if (!municipio.isEmpty())
			query.setParameter("municipio", "%" + municipio + "%");
		if (!unidade.isEmpty())
			query.setParameter("unidade", "%" + unidade + "%");
		return query.getSingleResult().intValue();
	}

	/**
	 * Verifica disponibilidade do Login
	 * 
	 * @param login
	 * @return boolean
	 */
	public boolean verificaDisponibilidadeDeLogin(String login) {
		TypedQuery<String> query = getEm().createQuery(
				"select u.login from Usuario u where u.login = :login",
				String.class);
		query.setParameter("login", login);
		try {
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
		TypedQuery<Usuario> query = getEm()
				.createQuery("select u from Usuario u where u.login = :login",
						Usuario.class);
		query.setParameter("login", login);
		try {
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
	public Usuario obterUsuarioPorNome(String nome) {
		TypedQuery<Usuario> query = getEm()
				.createQuery("select u from Usuario u where u.nome = :nome",
						Usuario.class);
		query.setParameter("nome", nome);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Obter usuario substitui��o por nome
	 * 
	 * @param nome
	 * @return Substituicao
	 */
	public Substituicao obterUsuarioSubstituicaoPorNome(String nome) {
		TypedQuery<Substituicao> query = getEm()
				.createQuery("select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) from Usuario u where u.nome = :nome",
						Substituicao.class);
		query.setParameter("nome", nome);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Obter usuario substitui��o por nome
	 * 
	 * @param nome
	 * @return Substituicao
	 */
	public Substituicao obterUsuarioSubstituicaoPorLogin(String login, String tipoConsulta) {
		String sqlCondicao = "";
		if(tipoConsulta.equalsIgnoreCase("EU_SUBSTITUO")){
			sqlCondicao = sqlCondicao + " u.loginSubstituicao ";
		}else{
			sqlCondicao = sqlCondicao + " u.login ";
		}
		TypedQuery<Substituicao> query = getEm()
				.createQuery("select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) "
						+ "from Usuario u where "+sqlCondicao+" = :login",
						Substituicao.class);
		query.setParameter("login", login);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Filtrar usu�rios
	 * 
	 * @param nome
	 * @return List<String>
	 */
	public List<String> filtrarUsuariosPorNome(String nome) {
		TypedQuery<String> query = getEm()
				.createQuery("select u.nome from Usuario u where u.nome LIKE :nome and not exists( select tag from u.grupo tag where tag in :master)",
						String.class);
		query.setParameter("nome", "%"+nome+"%");
		query.setParameter("master", Grupo.MASTER);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Filtrar usu�rios por unidade
	 * 
	 * @param idUnidade
	 * @return List<Usuario>
	 */
	public List<Usuario> filtrarUsuariosPorUnidade(Long idUnidade) {
		TypedQuery<Usuario> query = getEm()
				.createQuery("select u from Usuario u where u.unidade.id = :idUnidade",
						Usuario.class);
		query.setParameter("idUnidade", idUnidade);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Usuario>();
		}
	}
	
	/**
	 * Filtrar usu�rios por grupo/unidade
	 * 
	 * @param nome
	 * @return List<Usuario>
	 */
	public List<Usuario> obterDefensoresParaDesignacao(Long idUnidade, Grupo grupo) {
		TypedQuery<Usuario> query = getEm()
				.createQuery("select u from Usuario u where (u.unidade.id = :idUnidade or exists(select ass from u.dadosAssessoria ass where ass.unidade.id = :idUnidade)) "
						+ "and exists(select tag from u.grupo tag where tag in :grupo and tag <> :master)",
						Usuario.class);
		query.setParameter("idUnidade", idUnidade);
		query.setParameter("grupo", grupo);
		query.setParameter("master", Grupo.MASTER);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Usuario>();
		}
	}
	
	/**
	 * Filtrar usu�rios defensor
	 * 
	 * @param nome
	 * @return List<Usuario>
	 */
	public List<Substituicao> obterDefensores(Grupo grupo) {
		TypedQuery<Substituicao> query = getEm()
				.createQuery("select NEW br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Substituicao(u.login, u.nome, u.idDefensoria) from Usuario u where exists(select tag from u.grupo tag where tag in :grupo and tag <> :master)",
						Substituicao.class);		
		query.setParameter("grupo", grupo);
		query.setParameter("master", Grupo.MASTER);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Substituicao>();
		}
	}
	
	public Usuario obterDefensorDoAssessor(String assessor){
		TypedQuery<Usuario> query = getEm().createQuery("select u from Usuario u where exists(select ass from u.dadosAssessoria ass "
				+ "where ass.usuario.login = :assessor)", Usuario.class);
		query.setParameter("assessor", assessor);
		try {
			return query.getSingleResult();	
		} catch (Exception e) {
			return new Usuario();
		}
		
	}
	
	public void registraLoginSubstituicao(String loginDefensor, String loginDefensorSubstitui){		
		Query query = getEm().createQuery("UPDATE Usuario u SET u.loginSubstituicao = :loginDefensorSubstitui "								
								+ "WHERE u.login = :loginDefensor");
		query.setParameter("loginDefensor", loginDefensor);
		query.setParameter("loginDefensorSubstitui", loginDefensorSubstitui);		
		query.executeUpdate();
		
	}
	

	@Override
	public void insert(Usuario obj) {
		// TODO Auto-generated method stub
		super.insert(obj);
	}

	@Override
	public Usuario update(Usuario obj) {
		// TODO Auto-generated method stub
		return super.update(obj);
	}

	@Override
	public boolean remove(Usuario obj) {
		// TODO Auto-generated method stub
		return super.remove(obj);
	}

	@Override
	public Usuario find(Object id) {
		// TODO Auto-generated method stub
		return super.find(id);
	}

	@Override
	public List<Usuario> selectAll() {
		// TODO Auto-generated method stub
		return super.selectAll();
	}
	
	
}
