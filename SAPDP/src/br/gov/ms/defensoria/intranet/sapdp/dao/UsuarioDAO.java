package br.gov.ms.defensoria.intranet.sapdp.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.generics.DAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.GenericsDAO;
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
	
	public Usuario obterDefensorDoAssessor(String assessor){
		TypedQuery<Usuario> query = dao.getEntityManager().createQuery(""
				+ "select u from Usuario u where exists(select ass "
				+ "from u.dadosAssessoria ass "
				+ "where ass.usuario.login = :assessor)", Usuario.class);
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

	public void remover(Long id) {
		dao.remove(id);
	}

}
