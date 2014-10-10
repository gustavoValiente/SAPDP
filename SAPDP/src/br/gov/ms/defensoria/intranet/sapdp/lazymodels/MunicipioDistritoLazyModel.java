package br.gov.ms.defensoria.intranet.sapdp.lazymodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.gov.ms.defensoria.intranet.sapdp.model.MunicipioDistrito;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.MunicipioDistritoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
public class MunicipioDistritoLazyModel extends LazyDataModel<MunicipioDistrito> implements
		Serializable {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	private List<MunicipioDistrito> municipiosdistritos = new ArrayList<MunicipioDistrito>();

	private MunicipioDistritoRepositorio pRep;

	/**
	 * Retorna uma instância com JNDI.
	 * 
	 * @return MunicipioDistritoRepositorio - Instância de um sessionbean
	 */
	private MunicipioDistritoRepositorio getSession() {
		try {
			return (MunicipioDistritoRepositorio) new InitialContext()
					.lookup("java:global/SAPDP/MunicipioDistritoRepositorio");
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public List<MunicipioDistrito> load(int startingAt, int maxPerPage,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		pRep = getSession();

		String orderField = "", order = "", nome = "", estado = "", regional = "";

		// Filtro
		if (!filters.isEmpty())
			if (filters.get("nome") != null)
				nome = filters.get("nome").toString();
			if (filters.get("estado.nome") != null)
				estado = filters.get("estado.nome").toString();
			if (filters.get("regional.nome") != null)
				regional = filters.get("regional.nome").toString();
			
			
		// Ordenação
		if (sortField != null)
			orderField = sortField;
		if (sortOrder != null) {
			order = sortOrder.toString().equals("ASCENDING") ? "ASC" : "DESC";
		}

		municipiosdistritos = pRep.carregarMunicipiosDistritosLazy(startingAt, maxPerPage,
				orderField, order, nome, estado, regional);
		// rowCount
		int dataSize = pRep.carregarTotalMunicipiosDistritosLazy(startingAt, maxPerPage,
				orderField, order, nome, estado, regional);
		this.setRowCount(dataSize);

		// paginação
		if (dataSize > maxPerPage)
			this.setPageSize(startingAt + (dataSize % maxPerPage));
		else
			this.setPageSize(maxPerPage);

		return municipiosdistritos;
	}

	@Override
	public Object getRowKey(MunicipioDistrito p) {
		return p.getId();
	}

	@Override
	public MunicipioDistrito getRowData(String p) {
		Long id = Long.valueOf(p);

		for (MunicipioDistrito municipiodistrito : municipiosdistritos) {
			if (id.equals(municipiodistrito.getId())) {
				return municipiodistrito;
			}
		}
		return null;
	}
}