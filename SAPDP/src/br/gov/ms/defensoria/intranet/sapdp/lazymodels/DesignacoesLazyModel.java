package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Designacoes;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.DesignacaoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class DesignacoesLazyModel extends LazyDataModel<Designacoes> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Designacoes> designacoes = new ArrayList<Designacoes>();

	private DesignacaoRepositorio aRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return AssistidoDesignacaoRepositorioRepositorio - Instância de um
	 *         sessionbean
	 */
	private DesignacaoRepositorio getSession() {
		try {
			return (DesignacaoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/DesignacaoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Designacoes> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		aRep = getSession();

		String orderField = "", order = "", assistido = "", nucleo = "", defensor = "", atendente = "", preferencial = "", status = "";

		// Filtro
		if (!filters.isEmpty()) {
			if (filters.get("assistido.nome") != null)
				assistido = filters.get("assistido.nome").toString();
			if (filters.get("nucleo.nome") != null)
				nucleo = filters.get("nucleo.nome").toString();
			if (filters.get("defensor.nome") != null)
				defensor = filters.get("defensor.nome").toString();
			if (filters.get("atendente.nome") != null)
				atendente = filters.get("atendente.nome").toString();
			if (filters.get("preferencial") != null)
				preferencial = filters.get("preferencial").toString();
			if (filters.get("status") != null)
				status = filters.get("status").toString();
		}

		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		designacoes = aRep.carregarDesignacoesLazy(startingAt, maxPerPage,
				orderField, order, assistido, nucleo, defensor, atendente,
				preferencial, status);
		// rowCount
		int dataSize = aRep.carregarTotallDesignacoesLazy(assistido, nucleo,
				defensor, atendente, preferencial, status);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return designacoes;
	}

	@Override
	public Object getRowKey(Designacoes p) {
		return p.getId();
	}

	@Override
	public Designacoes getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Designacoes d : designacoes) {
			if (id.equals(d.getId())) {
				return d;
			}
		}
		return null;
	}

}