package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.Nucleo;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NucleoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class NucleoLazyModel extends LazyDataModel<Nucleo> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Nucleo> nucleos = new ArrayList<Nucleo>();

	private NucleoRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return NucleoRepositorio - Instância de um sessionbean
	 */
	private NucleoRepositorio getSession() {
		try {
			return (NucleoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/NucleoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Nucleo> load(int startingAt, int maxPerPage, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		pRep = getSession();

		String orderField = "", order = "", nome = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();

		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		nucleos = pRep.carregarNucleoLazy(startingAt, maxPerPage, orderField,
				order, nome);
		// rowCount
		int dataSize = pRep.carregarTotalNucleoLazy(startingAt, maxPerPage,
				orderField, order, nome);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return nucleos;
	}

	@Override
	public Object getRowKey(Nucleo p) {
		return p.getId();
	}

	@Override
	public Nucleo getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Nucleo nucleo : nucleos) {
			if (id.equals(nucleo.getId())) {
				return nucleo;
			}
		}
		return null;
	}
}