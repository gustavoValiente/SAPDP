package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.Unidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.UnidadeRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class UnidadeLazyModel extends LazyDataModel<Unidade> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Unidade> unidades = new ArrayList<Unidade>();

	private UnidadeRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return UnidadeRepositorio - Instância de um sessionbean
	 */
	private UnidadeRepositorio getSession() {
		try {
			return (UnidadeRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/UnidadeRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Unidade> load(int startingAt, int maxPerPage, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
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

		unidades = pRep.carregarUnidadesLazy(startingAt, maxPerPage,
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

		return unidades;
	}

	@Override
	public Object getRowKey(Unidade p) {
		return p.getId();
	}

	@Override
	public Unidade getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Unidade unidade : unidades) {
			if (id.equals(unidade.getId())) {
				return unidade;
			}
		}
		return null;
	}
}