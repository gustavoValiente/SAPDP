package br.gov.ms.defensoria.intranet.sapdp.bo;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericsSearchDAO;
import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;

@Stateless
@LocalBean
public class GenericsSearchBO {

	@EJB
	private GenericsSearchDAO dao;

	public List<IGenericEntity> carregarPesquisaLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros, IGenericEntity entity) {
		
		return dao.carregarPesquisaLazy(startingAt, maxPerPage, fieldOrder,
				order, parametros, entity);
	}

	public List<IGenericEntity> carregarPesquisaUsuarioLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros) {
		return dao.carregarPesquisaUsuarioLazy(startingAt, maxPerPage,
				fieldOrder, order, parametros);
	}

}
