package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Nacionalidade;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.NacionalidadeRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class NacionalidadeLazyModel extends LazyDataModel<Nacionalidade>
		implements Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Nacionalidade> nacionalidades = new ArrayList<Nacionalidade>();

	private NacionalidadeRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return NacionalidadeRepositorio - Instância de um sessionbean
	 */
	private NacionalidadeRepositorio getSession() {
		try {
			return (NacionalidadeRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/NacionalidadeRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Nacionalidade> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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

		nacionalidades = pRep.carregarNacionalidadesLazy(startingAt,
				maxPerPage, orderField, order, nome);
		// rowCount
		int dataSize = pRep.carregarTotalNacionalidadesLazy(startingAt,
				maxPerPage, orderField, order, nome);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return nacionalidades;
	}

	@Override
	public Object getRowKey(Nacionalidade p) {
		return p.getId();
	}

	@Override
	public Nacionalidade getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Nacionalidade nacionalidade : nacionalidades) {
			if (id.equals(nacionalidade.getId())) {
				return nacionalidade;
			}
		}
		return null;
	}
}