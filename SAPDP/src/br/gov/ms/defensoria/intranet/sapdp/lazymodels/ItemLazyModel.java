package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ItemRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class ItemLazyModel extends LazyDataModel<ItemAtendimento> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<ItemAtendimento> itens = new ArrayList<ItemAtendimento>();

	private ItemRepositorio iRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return ItemRepositorio - Instância de um sessionbean
	 */
	private ItemRepositorio getSession() {
		try {
			return (ItemRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/ItemRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<ItemAtendimento> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		iRep = getSession();

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

		itens = iRep.carregarItensLazy(startingAt, maxPerPage,
				orderField, order, nome);
		// rowCount
		int dataSize = iRep.carregarTotalItensLazy(startingAt, maxPerPage,
				orderField, order, nome);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return itens;
	}

	@Override
	public Object getRowKey(ItemAtendimento i) {
		return i.getId();
	}

	@Override
	public ItemAtendimento getRowData(String p) {
		Long id = Long.valueOf(p);

		for (ItemAtendimento item : itens) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}
}