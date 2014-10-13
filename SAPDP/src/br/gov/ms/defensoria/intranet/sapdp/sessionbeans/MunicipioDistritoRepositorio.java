package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.Bairro;
import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;

@Stateless
public class MunicipioDistritoRepositorio extends
		GenericDAOImpl<MunicipioDistrito> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MunicipioDistritoRepositorio() {
		super(MunicipioDistrito.class);
	}
	/**
	 * Retorna uma lista de String com os nomes dos MunicipiosDistritos
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarMunicipiosDistritosPorNome(String nome) {
		TypedQuery<String> query = getEm().createQuery("select n.nome from MunicipioDistrito n where n.nome LIKE :nome",String.class);
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	/**
	 * Retorna uma lista de String com os nomes dos MunicipiosDistritos por Estado
	 * @param query - String
	 * @return String
	 */
	public List<String> filtrarMunicipiosDistritosPorEstado(String nome) {
		TypedQuery<String> query = getEm().createQuery("select n.nome from MunicipioDistrito n "
				+ "where n.estado.sigla = :siglaEstado and n.nome LIKE :nome",String.class);		
		query.setParameter("siglaEstado", "MS");
		query.setParameter("nome", "%"+nome+"%");
		return query.getResultList();
	}
	
	/**
	 * Retorna um objeto MunicipioDistrito
	 * @param query - String
	 * @return MunicipioDistrito
	 */
	public MunicipioDistrito buscarMunicipioDistritoPorNome(String nome) {
		TypedQuery<MunicipioDistrito> query = getEm().createQuery("select n from MunicipioDistrito n where n.nome = :nome",MunicipioDistrito.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna munic�pio por Id
	 * @param id - Long
	 * @return MunicipioDistrito
	 */
	public MunicipioDistrito obterMunicipioPorId(Long id) {
		TypedQuery<MunicipioDistrito> query = getEm().createQuery("select n from MunicipioDistrito n where n.id = :id",MunicipioDistrito.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna uma lista de profiss�es, implementa��o LazyModel
	 * @return <code>List<Profissao></code>
	 */
	public List<MunicipioDistrito> carregarMunicipiosDistritosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String estado, String regional){
		String jpql  = "select m from MunicipioDistrito m where m.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND m.nome LIKE :nome";
		if(!estado.isEmpty())
			jpql += " AND m.estado.nome LIKE :estado";
		if(!regional.isEmpty())
			jpql += " AND m.regional.nome LIKE :regional";
		//Ordena��o
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<MunicipioDistrito> query = getEm().createQuery(jpql, MunicipioDistrito.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!estado.isEmpty())
			query.setParameter("estado", "%"+estado+"%");
		if(!regional.isEmpty())
			query.setParameter("regional", "%"+regional+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<MunicipioDistrito>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total profissoes , implementa��o LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalMunicipiosDistritosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String nome, String estado, String regional){
		String jpql  = "select count(m) from MunicipioDistrito m where m.id > 0 ";
		//Filtros
		if(!nome.isEmpty())
			jpql += " AND m.nome LIKE :nome";
		if(!estado.isEmpty())
			jpql += " AND m.estado.nome LIKE :estado";
		if(!regional.isEmpty())
			jpql += " AND m.regional.nome LIKE :regional";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!nome.isEmpty())
			query.setParameter("nome", "%"+nome+"%");
		if(!estado.isEmpty())
			query.setParameter("estado", "%"+estado+"%");
		if(!regional.isEmpty())
			query.setParameter("regional", "%"+regional+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
	
	public List<Bairro> obterBairrosPorMunicipio(Long id){
		TypedQuery<Bairro> query = getEm().createQuery("select b from Bairro b where b.municipio.id = :id",Bairro.class);
		query.setParameter("id", id);
		return query.getResultList(); 
	}
	
}
