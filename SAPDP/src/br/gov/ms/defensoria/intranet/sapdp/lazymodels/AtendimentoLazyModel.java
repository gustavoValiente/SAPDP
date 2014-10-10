package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AtendimentoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class AtendimentoLazyModel extends LazyDataModel<Atendimento> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Atendimento> atendimentos = new ArrayList<Atendimento>();

	private AtendimentoRepositorio aRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return AtendimentoRepositorio - Instância de um sessionbean
	 */
	private AtendimentoRepositorio getSession() {
		try {
			return (AtendimentoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/AtendimentoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Atendimento> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		aRep = getSession();

		String orderField = "", order = "", assistido = "", defensor = "", unidade = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("assistido.nome") != null)
				assistido = filters.get("assistido.nome").toString();
			if (filters.get("defensor.nome") != null)
				defensor = filters.get("defensor.nome").toString();
			if (filters.get("unidade.nome") != null)
				unidade = filters.get("unidade.nome").toString();
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		atendimentos = aRep.carregarAtendimentosLazy(startingAt, maxPerPage,
				orderField, order, assistido, defensor, unidade);
		// rowCount
		int dataSize = aRep.carregarTotalAtendimentosLazy(startingAt, maxPerPage,
				orderField, order, assistido, defensor, unidade);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return atendimentos;
	}

	@Override
	public Object getRowKey(Atendimento a) {
		return a.getId();
	}

	@Override
	public Atendimento getRowData(String a) {
		Long id = Long.valueOf(a);

		for (Atendimento atendimento : atendimentos) {
			if (id.equals(atendimento.getId())) {
				return atendimento;
			}
		}
		return null;
	}
}