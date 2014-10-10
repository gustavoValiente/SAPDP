package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.Defensoria;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.DefensoriaRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class DefensoriaLazyModel extends LazyDataModel<Defensoria> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Defensoria> defensorias = new ArrayList<Defensoria>();

	private DefensoriaRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return DefensoriaRepositorio - Instância de um sessionbean
	 */
	private DefensoriaRepositorio getSession() {
		try {
			return (DefensoriaRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/DefensoriaRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Defensoria> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		pRep = getSession();

		String orderField = "", order = "", nome = "", municipio = "";

		// Filtro
		if (!filters.isEmpty()) {
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();
			if (filters.get("municipioDistrito.nome") != null)
				municipio = filters.get("municipioDistrito.nome").toString();
		}
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		defensorias = pRep.carregarDefensoriasLazy(startingAt, maxPerPage,
				orderField, order, nome, municipio);
		// rowCount
		int dataSize = pRep.carregarTotalUnidadesLazy(startingAt, maxPerPage,
				orderField, order, nome, municipio);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return defensorias;
	}

	@Override
	public Object getRowKey(Defensoria p) {
		return p.getId();
	}

	@Override
	public Defensoria getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Defensoria def : defensorias) {
			if (id.equals(def.getId())) {
				return def;
			}
		}
		return null;
	}
}