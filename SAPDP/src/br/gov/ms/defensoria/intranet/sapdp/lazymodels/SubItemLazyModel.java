package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.SubItemRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class SubItemLazyModel extends LazyDataModel<SubItemAtendimento> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<SubItemAtendimento> subItens = new ArrayList<SubItemAtendimento>();

	private SubItemRepositorio sRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return SubItemRepositorio - Instância de um sessionbean
	 */
	private SubItemRepositorio getSession() {
		try {
			return (SubItemRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/SubItemRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<SubItemAtendimento> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		sRep = getSession();

		String orderField = "", order = "", nome = "";

		// Filtro
		if (!filters.isEmpty()) {
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();			
		}
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		subItens = sRep.carregarSubItensLazy(startingAt, maxPerPage,
				orderField, order, nome);
		// rowCount
		int dataSize = sRep.carregarTotalSubItensLazy(startingAt, maxPerPage,
				orderField, order, nome);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return subItens;
	}

	@Override
	public Object getRowKey(SubItemAtendimento i) {
		return i.getId();
	}

	@Override
	public SubItemAtendimento getRowData(String p) {
		Long id = Long.valueOf(p);

		for (SubItemAtendimento item : subItens) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}
}