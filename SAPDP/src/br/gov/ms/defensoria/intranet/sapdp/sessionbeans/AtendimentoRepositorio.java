package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;

@Stateless
public class AtendimentoRepositorio extends GenericDAOImpl<Atendimento> {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	private Calendar c1;
	private Calendar c2;

	
	public AtendimentoRepositorio() {
		super(Atendimento.class);
	}

	/**
	 * Normaliza as datas atuais
	 * @throws ParseException 
	 */	
	public void normalizaDatasAtuais(String data){
		Date dt;
		if(data == null){
			dt = new Date();	
		}else{
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(data);
				dt = date;
			} catch (Exception e) {
				// TODO: handle exception
				dt = new Date();
			}				
			
		}
		c1 = Calendar.getInstance();
		// atendimentos entre a 00:00 da data inicial passada como parametro
		c1.setTime(dt);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		// e as 23:59:59:99 da data final passada como parametro
		c2 = Calendar.getInstance();
		c2.setTime(dt);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.MILLISECOND, 99);
	}	
	
	/**
	 * Obter atendimento por assistido
	 * @param idAssistido
	 * @return List<Atendimento>
	 */
	public List<SimpleAtendimento> obterAtendimentosPorAssistido(Long idAssistido) {
		TypedQuery<SimpleAtendimento> query = getEm().createQuery(
				"select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento(a.id, a.designacao.id, "
				+ "a.designacao.assistido.nome, a.item.nome, a.subItem.nome, a.unidade.nome, a.designacao.defensor.nome, "
				+ "a.dataAtendimento, a.observacao, a.fatoNarrado, a.designacao.loginSubstituicao, a.designacao.defensor.idDefensoria, "
				+ "a.juizado) "
				+ "from Atendimento a where a.designacao.assistido.id = :idAssistido "
				+ "and a.ultimoAtendimento = 'SIM' and not exists(select d from Designacoes d where d.idAtendimentoPai = a.id "
				+ "and d.status = :status) ", SimpleAtendimento.class);
		query.setParameter("idAssistido", idAssistido);
		query.setParameter("status", StatusDesignacao.DESIGNADO);
		return query.getResultList();
	}
	
	/**
	 * Obter atendimento por defensor
	 * @param idDefensor
	 * @return List<Atendimento>
	 */
	public List<SimpleAtendimento> obterAtendimentosPorDefensorData(String loginDefensor) {
		try {
			normalizaDatasAtuais(null);
			
			TypedQuery<SimpleAtendimento> query = getEm().createQuery(
					"select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento(a.id, a.designacao.id, "
					+ "a.designacao.assistido.nome, a.item.nome, a.subItem.nome, a.unidade.nome, a.designacao.defensor.nome, "
					+ "a.dataAtendimento, a.observacao, a.fatoNarrado, a.designacao.loginSubstituicao, a.designacao.defensor.idDefensoria,"
					+ "a.juizado) "
					+ "from Atendimento a where a.designacao.defensor.login = :loginDefensor "
					+ "and a.ultimoAtendimento = 'SIM' "
					+ "and a.dataAtendimento BETWEEN :dataInicio and :dataFim ", SimpleAtendimento.class);
			query.setParameter("loginDefensor", loginDefensor);
			query.setParameter("dataInicio", c1.getTime());
			query.setParameter("dataFim", c2.getTime());
			return query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ArrayList<SimpleAtendimento>();
		}
	}
	
	/**
	 * Obter atendimento por defensor e data
	 * @param idDefensor
	 * @return List<Atendimento>
	 */
	public List<Atendimento> obterAtendimentosPorDefensor(String loginDefensor) {
		TypedQuery<Atendimento> query = getEm().createQuery(
				"select a from Atendimento a where a.designacao.defensor.login = :loginDefensor "
				+ "and a.ultimoAtendimento = 'SIM' and a.designacao.idAtendimentoPai <> null ", Atendimento.class);
		query.setParameter("loginDefensor", loginDefensor);
		return query.getResultList();
	}
	
	
	/**
	 * Obter atendimentoPai
	 * @param idAtendimentoPai
	 * @return Atendimento
	 */
	public Atendimento obterAtendimentoPorId(Long id) {
		TypedQuery<Atendimento> query = getEm().createQuery("select a from Atendimento a where a.id = :id ", Atendimento.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	
	/**
	 * Obter simple atendimento por id
	 * @param id
	 * @return SimpleAtendimento
	 */
	public SimpleAtendimento obterSimpleAtendimentoPorId(Long id) {
		TypedQuery<SimpleAtendimento> query = getEm().
				createQuery("select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento(a.id, a.designacao.id, "
						+ "a.designacao.assistido.nome, a.item.nome, a.subItem.nome, a.unidade.nome, a.designacao.defensor.nome, "
						+ "a.dataAtendimento, a.observacao, a.fatoNarrado, a.designacao.loginSubstituicao, a.designacao.defensor.idDefensoria, "
						+ "a.juizado) "
					+ "from Atendimento a where a.id = :id ", 
				SimpleAtendimento.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	/**
	 * Retorna uma lista de Atendimentos, implementação LazyModel
	 * @return <code>List<Atendimento></code>
	 */
	public List<Atendimento> carregarAtendimentosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String assistido, String defensor, String unidade){
		String jpql  = "select a from Atendimento a where a.id > 0 ";
		//Filtros
		if(!assistido.isEmpty())
			jpql += " AND a.assistido.nome LIKE :assistido";
		if(!defensor.isEmpty())
			jpql += " AND a.defensor.nome LIKE :defensor";
		if(!unidade.isEmpty())
			jpql += " AND a.unidade.nome LIKE :unidade";
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<Atendimento> query = getEm().createQuery(jpql, Atendimento.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!assistido.isEmpty())
			query.setParameter("assistido", "%"+assistido+"%");
		if(!defensor.isEmpty())
			query.setParameter("defensor", "%"+defensor+"%");
		if(!unidade.isEmpty())
			query.setParameter("unidade", "%"+unidade+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Atendimento>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total enderecos , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalAtendimentosLazy(int startingAt, int maxPerPage,
			String fieldOrder, String order, String assistido, String defensor, String unidade){
		String jpql  = "select count(a) from Atendimento a where a.id > 0 ";
		//Filtros
		if(!assistido.isEmpty())
			jpql += " AND a.assistido.nome LIKE :assistido";
		if(!defensor.isEmpty())
			jpql += " AND a.defensor.nome LIKE :defensor";
		if(!unidade.isEmpty())
			jpql += " AND a.unidade.nome LIKE :unidade";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!assistido.isEmpty())
			query.setParameter("assistido", "%"+assistido+"%");
		if(!defensor.isEmpty())
			query.setParameter("defensor", "%"+defensor+"%");
		if(!unidade.isEmpty())
			query.setParameter("unidade", "%"+unidade+"%");
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}
	
	public void alteraUltimoAtendimento(Long id, String tipo){		
		Query query = getEm().createQuery("UPDATE Atendimento a SET a.ultimoAtendimento = :tipo WHERE a.id = :id");
		query.setParameter("id", id);
		query.setParameter("tipo", tipo);
		query.executeUpdate();
		
	}
	
	
	/**
	 * Retorna uma lista de Atendimentos, implementação LazyModel
	 * @return <code>List<Atendimento></code>
	 */
	public List<SimpleAtendimento> carregarSimpleAtendimentosLazy(String loginDefensor, int startingAt, int maxPerPage,
			String fieldOrder, String order, String assistido, String atividade, String providencia, String unidade, String dataAtendimento){
		
		if(!dataAtendimento.isEmpty()){
			normalizaDatasAtuais(dataAtendimento);			
		}else
			normalizaDatasAtuais(null);
		
		String jpql  = "select NEW br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento(a.id, a.designacao.id, "
						+ "a.designacao.assistido.nome, a.item.nome, a.subItem.nome, a.unidade.nome, a.designacao.defensor.nome, a.dataAtendimento, "
						+ "a.observacao, a.fatoNarrado, a.designacao.loginSubstituicao, a.designacao.defensor.idDefensoria, a.juizado) "
						+ "from Atendimento a where a.id > 0 AND a.ultimoAtendimento = 'SIM' "						
						+ "AND a.dataAtendimento BETWEEN :dataInicial and :dataFim ";

		//Filtros
		if(!loginDefensor.isEmpty())
			jpql += " AND (a.designacao.defensor.login = :loginDefensor or a.designacao.loginSubstituicao = :loginDefensor)";
		if(!assistido.isEmpty())
			jpql += " AND a.designacao.assistido.nome LIKE :assistido";
		if(!atividade.isEmpty())
			jpql += " AND a.item.nome LIKE :atividade";
		if(!providencia.isEmpty())
			jpql += " AND a.subItem.nome LIKE :providencia";
		if(!unidade.isEmpty())
			jpql += " AND a.unidade.nome LIKE :unidade";
		
		//Ordenação
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		
		TypedQuery<SimpleAtendimento> query = getEm().createQuery(jpql, SimpleAtendimento.class);
		
		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		if(!loginDefensor.isEmpty())
			query.setParameter("loginDefensor", loginDefensor);
		if(!assistido.isEmpty())
			query.setParameter("assistido", "%"+assistido+"%");
		if(!atividade.isEmpty())
			query.setParameter("atividade", "%"+atividade+"%");
		if(!providencia.isEmpty())
			query.setParameter("providencia", "%"+providencia+"%");
		if(!unidade.isEmpty())
			query.setParameter("unidade", "%"+unidade+"%");
		
		query.setParameter("dataInicial", c1.getTime());
		query.setParameter("dataFim", c2.getTime());
		
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<SimpleAtendimento>();
		return query.getResultList();
	}
	
	/**
	 * Retorna total enderecos , implementação LazyModel
	 * @return <code>int</code>
	 */
	public int carregarTotalSimpleAtendimentosLazy(String loginDefensor, int startingAt, int maxPerPage,
			String fieldOrder, String order, String assistido, String atividade, String providencia, String unidade, String dataAtendimento){
		String jpql  = "select count(a) from Atendimento a where a.id > 0 AND a.ultimoAtendimento = 'SIM' "
						+ "AND a.dataAtendimento BETWEEN :dataInicial and :dataFim ";
		
		if(!dataAtendimento.isEmpty())
			normalizaDatasAtuais(dataAtendimento);
		else
			normalizaDatasAtuais(null);
		
		//Filtros
		if(!loginDefensor.isEmpty())
			jpql += " AND (a.designacao.defensor.login = :loginDefensor or a.designacao.loginSubstituicao = :loginDefensor)";
		if(!assistido.isEmpty())
			jpql += " AND a.designacao.assistido.nome LIKE :assistido";
		if(!atividade.isEmpty())
			jpql += " AND a.item.nome LIKE :atividade";
		if(!providencia.isEmpty())
			jpql += " AND a.subItem.nome LIKE :providencia";
		if(!unidade.isEmpty())
			jpql += " AND a.unidade.nome LIKE :unidade";
		
		TypedQuery<Long> query = getEm().createQuery(jpql, Long.class);
		
		if(!loginDefensor.isEmpty())
			query.setParameter("loginDefensor", loginDefensor);
		if(!assistido.isEmpty())
			query.setParameter("assistido", "%"+assistido+"%");
		if(!atividade.isEmpty())
			query.setParameter("atividade", "%"+atividade+"%");
		if(!providencia.isEmpty())
			query.setParameter("providencia", "%"+providencia+"%");
		if(!unidade.isEmpty())
			query.setParameter("unidade", "%"+unidade+"%");
		
		query.setParameter("dataInicial", c1.getTime());
		query.setParameter("dataFim", c2.getTime());		
		
		if(query.getResultList() == null || query.getResultList().isEmpty())
			return 0;
		return query.getSingleResult().intValue();
	}

}
