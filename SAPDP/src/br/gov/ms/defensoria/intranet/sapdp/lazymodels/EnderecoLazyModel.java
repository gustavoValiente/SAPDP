package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.Endereco;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.EnderecoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class EnderecoLazyModel extends LazyDataModel<Endereco> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Endereco> enderecos = new ArrayList<Endereco>();

	
	private EnderecoRepositorio eRep;

	/**
	 * Retorna uma inst�ncia com JNDI.
	 * 
	 * @return EnderecoRepositorio - Inst�ncia de um sessionbean
	 */
	private EnderecoRepositorio getSession() {
		try {
			return (EnderecoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/EnderecoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Endereco> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		eRep = getSession();

		String orderField = "", order = "", rua = "", bairro = "", cep = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("rua") != null)
				rua = filters.get("rua").toString();
			if (filters.get("bairro.nome") != null)
				bairro = filters.get("bairro.nome").toString();
			if (filters.get("cep") != null)
				cep = filters.get("cep").toString();
		// Ordena��o
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		enderecos = eRep.carregarEnderecosLazy(startingAt, maxPerPage,
				orderField, order, rua, bairro, cep);
		// rowCount
		int dataSize = eRep.carregarTotalEnderecosLazy(startingAt, maxPerPage,
				orderField, order, rua, bairro, cep);
		this.setRowCount(dataSize);

		// pagina��o
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return enderecos;
	}

	@Override
	public Object getRowKey(Endereco p) {
		return p.getId();
	}

	@Override
	public Endereco getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Endereco endereco : enderecos) {
			if (id.equals(endereco.getId())) {
				return endereco;
			}
		}
		return null;
	}
}