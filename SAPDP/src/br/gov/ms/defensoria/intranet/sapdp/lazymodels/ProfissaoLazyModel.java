package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Profissao;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.ProfissaoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class ProfissaoLazyModel extends LazyDataModel<Profissao> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Profissao> profissoes = new ArrayList<Profissao>();

	private ProfissaoRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return ProfissaoRepositorio - Instância de um sessionbean
	 */
	private ProfissaoRepositorio getSession() {
		try {
			return (ProfissaoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/ProfissaoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Profissao> load(int startingAt, int maxPerPage,
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

		profissoes = pRep.carregarProfissoesLazy(startingAt, maxPerPage,
				orderField, order, nome);
		// rowCount
		int dataSize = pRep.carregarTotalProfissoesLazy(startingAt, maxPerPage,
				orderField, order, nome);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return profissoes;
	}

	@Override
	public Object getRowKey(Profissao p) {
		return p.getId();
	}

	@Override
	public Profissao getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Profissao profissao : profissoes) {
			if (id.equals(profissao.getId())) {
				return profissao;
			}
		}
		return null;
	}
}