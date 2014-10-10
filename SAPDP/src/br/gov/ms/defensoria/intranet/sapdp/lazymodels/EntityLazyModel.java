package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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

	private Map<String, String> parametros;

	private List<IGenericEntity> listEntity;

	public EntityLazyModel(IGenericEntity entityClass) {
		this.entityClass = entityClass;
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
		parametros = new HashMap<String, String>();
		listEntity = new ArrayList<IGenericEntity>();

		Field[] fields = entityClass.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!filters.isEmpty()) {
				if (filters.get(field.getName()) != null) {
					parametros.put(field.getName(), filters
							.get(field.getName()).toString());
				}
			}
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
