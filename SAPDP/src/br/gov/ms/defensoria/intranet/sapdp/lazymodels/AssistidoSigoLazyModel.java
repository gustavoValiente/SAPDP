package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.AssistidoSigo;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AssistidoSigoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class AssistidoSigoLazyModel extends LazyDataModel<AssistidoSigo> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<AssistidoSigo> assistidos = new ArrayList<AssistidoSigo>();

	private AssistidoSigoRepositorio aSigoRep;
	private String nomeAssistido = "", cpfAssistido = "", rgAssistido = "", maeAssistido = "", paiAssistido = "";
	
	public AssistidoSigoLazyModel() {
	
	}

	public AssistidoSigoLazyModel(String nome, String cpf, String rg, String mae, String pai) {
		this.nomeAssistido = nome;
		this.cpfAssistido = cpf;
		this.rgAssistido = rg;
		this.maeAssistido = mae;
		this.paiAssistido = pai;
	}

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return AssistidoRepositorio - Instância de um sessionbean
	 */
	private AssistidoSigoRepositorio getSession() {
		try {
			return (AssistidoSigoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/AssistidoSigoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<AssistidoSigo> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		aSigoRep = getSession();

		String orderField = "", order = "", nome = "", mae = "", pai = "", cpf = "", rg = "";

		// Filtro
		if (!filters.isEmpty()){
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();
			if (filters.get("mae") != null)
				mae = filters.get("mae").toString();
			if (filters.get("pai") != null)
				pai = filters.get("pai").toString();
			if (filters.get("cpf") != null)
				cpf = filters.get("cpf").toString();
			if (filters.get("rg") != null)
				rg = filters.get("rg").toString();
		}
		
		if(this.nomeAssistido != "" || this.cpfAssistido != "" || this.rgAssistido != "" || 
		   this.maeAssistido != "" || this.paiAssistido != ""){
			nome = this.nomeAssistido != "" ? this.nomeAssistido : "";
			cpf = this.cpfAssistido != "" ? this.cpfAssistido : "";
			rg = this.rgAssistido != "" ? this.rgAssistido : "";
			mae = this.maeAssistido != "" ? this.maeAssistido : "";
			pai = this.paiAssistido != "" ? this.paiAssistido : "";
		}
			
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		assistidos = aSigoRep.carregarAssistidosSigoLazy(startingAt, maxPerPage,
				orderField, order, nome, mae, pai, cpf, rg);
		// rowCount
		int dataSize = aSigoRep.carregarTotalAssistidosSigoLazy(startingAt, maxPerPage,
				orderField, order,  nome, mae, pai, cpf, rg);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return assistidos;
	}

	@Override
	public Object getRowKey(AssistidoSigo p) {
		return p.getId();
	}

	@Override
	public AssistidoSigo getRowData(String p) {
		Long id = Long.valueOf(p);

		for (AssistidoSigo assistido : assistidos) {
			if (id.equals(assistido.getId())) {
				return assistido;
			}
		}
		return null;
	}

	public String getNomeAssistido() {
		return nomeAssistido;
	}

	public void setNomeAssistido(String nomeAssistido) {
		this.nomeAssistido = nomeAssistido;
	}

	public String getCpfAssistido() {
		return cpfAssistido;
	}

	public void setCpfAssistido(String cpfAssistido) {
		this.cpfAssistido = cpfAssistido;
	}

	public String getRgAssistido() {
		return rgAssistido;
	}

	public void setRgAssistido(String rgAssistido) {
		this.rgAssistido = rgAssistido;
	}

	public String getMaeAssistido() {
		return maeAssistido;
	}

	public void setMaeAssistido(String maeAssistido) {
		this.maeAssistido = maeAssistido;
	}

	public String getPaiAssistido() {
		return paiAssistido;
	}

	public void setPaiAssistido(String paiAssistido) {
		this.paiAssistido = paiAssistido;
	}

	
}