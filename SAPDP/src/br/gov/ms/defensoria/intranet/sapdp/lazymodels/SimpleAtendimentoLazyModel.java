package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AtendimentoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class SimpleAtendimentoLazyModel extends LazyDataModel<SimpleAtendimento> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<SimpleAtendimento> atendimentos = new ArrayList<SimpleAtendimento>();

	private AtendimentoRepositorio aRep;
	
	private String loginDefensor;

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
	
	

	public SimpleAtendimentoLazyModel(String loginDefensor) {
		this.loginDefensor = loginDefensor;
	}



	@Override
	public List<SimpleAtendimento> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		aRep = getSession();

		String orderField = "", order = "", assistido = "", atividade = "",  providencia = "", unidade = "", dataAtendimento = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("nomeAssistido") != null)
				assistido = filters.get("nomeAssistido").toString();
			if (filters.get("nomeAtividade") != null)
				atividade = filters.get("nomeAtividade").toString();
			if (filters.get("nomeProvidencia") != null)
				providencia = filters.get("nomeProvidencia").toString();
			if (filters.get("nomeUnidade") != null)
				unidade = filters.get("nomeUnidade").toString();
			if (filters.get("nomeUnidade") != null)
				unidade = filters.get("nomeUnidade").toString();
			if (filters.get("dataAtendimento") != null)
				dataAtendimento = filters.get("dataAtendimento").toString();
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		atendimentos = aRep.carregarSimpleAtendimentosLazy(this.loginDefensor, startingAt, maxPerPage,
				orderField, order, assistido, atividade, providencia, unidade, dataAtendimento);
		// rowCount
		int dataSize = aRep.carregarTotalSimpleAtendimentosLazy(this.loginDefensor, startingAt, maxPerPage,
				orderField, order, assistido, atividade, providencia, unidade, dataAtendimento);
		this.setRowCount(dataSize);
		
		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return atendimentos;
	}

	@Override
	public Object getRowKey(SimpleAtendimento a) {
		return a.getIdAtendimento();
	}

	@Override
	public SimpleAtendimento getRowData(String a) {
		Long id = Long.valueOf(a);

		for (SimpleAtendimento atendimento : atendimentos) {
			if (id.equals(atendimento.getIdAtendimento())) {
				return atendimento;
			}
		}
		return null;
	}



	public String getLoginDefensor() {
		return loginDefensor;
	}



	public void setLoginDefensor(String loginDefensor) {
		this.loginDefensor = loginDefensor;
	}
	
	
}