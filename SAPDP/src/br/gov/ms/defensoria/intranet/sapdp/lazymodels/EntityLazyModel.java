package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.generics.IGenericEntity;
import br.gov.ms.defensoria.intranet.sapdp.model.usuarios.Usuario;
import br.gov.ms.defensoria.intranet.sapdp.service.SegurancaService;
import br.gov.ms.defensoria.intranet.sapdp.util.SimpleValidate;

public class EntityLazyModel extends LazyDataModel<IGenericEntity>
		implements
			Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IGenericEntity entityClass;

	private Map<String, Object> parametros;

	private List<IGenericEntity> listEntity;

	private Object[] argumentos;

	public EntityLazyModel(IGenericEntity entityClass, Object... argumentos) {
		this.entityClass = entityClass;
		this.argumentos = argumentos;

	}

	private SegurancaService getService() {
		try {
			return (SegurancaService) new InitialContext()
					.lookup("java:global/SAPDP/SegurancaService");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<IGenericEntity> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		String orderField = "", order = "";
		parametros = new HashMap<String, Object>();
		listEntity = new ArrayList<IGenericEntity>();

		for (Iterator<String> parametro = filters.keySet().iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			parametros.put(campo, filters.get(campo).toString());
		}

		for (int i = 0; i < argumentos.length; i++) {
			String campo = (String) argumentos[0];
			Object valorArgumento = argumentos[1];
			parametros.put(campo, valorArgumento);
		}

		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}
		if (entityClass instanceof Usuario) {
			listEntity = getService().carregarPesquisaUsuarioLazy(startingAt,
					maxPerPage, orderField, order, parametros);
		} else {
			listEntity = getService().carregarPesquisaLazy(startingAt,
					maxPerPage, orderField, order, parametros, entityClass);
		}

		int dataSize = 0;
		setRowCount(0);
		if (!SimpleValidate.isNullOrEmpty(listEntity)) {
			setRowCount(listEntity.size());
			dataSize = listEntity.size();
		}

		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return listEntity;
	}
}
