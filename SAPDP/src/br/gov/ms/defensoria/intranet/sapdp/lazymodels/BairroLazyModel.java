package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.Bairro;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.BairroRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class BairroLazyModel extends LazyDataModel<Bairro> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<Bairro> bairros = new ArrayList<Bairro>();

	private BairroRepositorio pRep;
	
	private Long idMunicipio;
	
	public BairroLazyModel(String idMunicipio){		
			this.idMunicipio = (idMunicipio != null)?Long.parseLong(idMunicipio):null;
	}

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return BairroRepositorio - Instância de um sessionbean
	 */
	private BairroRepositorio getSession() {
		try {
			return (BairroRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/BairroRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<Bairro> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		pRep = getSession();

		String orderField = "", order = "", nome = "", municipio = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();
			if (filters.get("municipio.nome") != null)
				municipio = filters.get("municipio.nome").toString();
			
			
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		bairros = pRep.carregarBairrosLazy(startingAt, maxPerPage,
				orderField, order, nome, municipio ,idMunicipio);
		// rowCount
		int dataSize = pRep.carregarTotalBairrosLazy(startingAt, maxPerPage,
				orderField, order, nome, municipio, idMunicipio);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return bairros;
	}

	@Override
	public Object getRowKey(Bairro p) {
		return p.getId();
	}

	@Override
	public Bairro getRowData(String p) {
		Long id = Long.valueOf(p);

		for (Bairro bairro : bairros) {
			if (id.equals(bairro.getId())) {
				return bairro;
			}
		}
		return null;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	
	
	
}