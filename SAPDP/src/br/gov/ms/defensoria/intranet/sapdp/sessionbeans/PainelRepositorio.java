package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.ms.defensoria.intranet.sapdp.dao.GenericDAOImpl;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.InfoDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Singleton
public class PainelRepositorio extends GenericDAOImpl<Painel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar c1;
	private Calendar c2;
	public PainelRepositorio() {
		super(Painel.class);
	}

	/**
	 * Normaliza as datas atuais
	 */
	public void normalizaDatasAtuais() {
		c1 = Calendar.getInstance();
		// atendimentos entre a 00:00 da data inicial passada como parametro
		c1.setTime(new Date());
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		// e as 23:59:59:99 da data final passada como parametro
		c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.MILLISECOND, 99);
	}
	
	//PAINEIS
	/**
	 * Retorna os painéis da unidade
	 * @return List<Painel>
	 */
	public List<Painel> obterPaineisPorUnidade(Long idUnidade){
		TypedQuery<Painel> query = getEm().createQuery("select p from Painel p where p.unidade.id = :idUnidade", Painel.class);
		query.setParameter("idUnidade", idUnidade);
		if (query.getResultList() == null || query.getResultList().isEmpty())
			return new ArrayList<Painel>();
		return query.getResultList();		
	}
	
	/**
	 * 
	 * @param nome - String 
	 * @return Painel
	 */
	public Painel obterPainelPorNome(String nome) {
		String jpql = "select p from Painel p where p.nome = :nome ";
		TypedQuery<Painel> query = getEm ().createQuery(jpql, Painel.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}
	
	/**
	 * 
	 * @param id - Long
	 * @return Painel
	 */
	public Painel obterPainelPorId(Long id) {
		String jpql = "select p from Painel p where p.id = :id";
		TypedQuery<Painel> query = getEm().createQuery(jpql, Painel.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	/**
	 * Obtém a ultima senha por painel
	 * @param id
	 * @return String
	 */
	public List<InfoDesignacao> obterUltimaSenhaPorDesignacao(Long idUnidade, List<String> nucleos){
		normalizaDatasAtuais();
		String jpql = "select NEW br.gov.ms.defensoria.intranet.sapdp.model.paineis.InfoDesignacao(d.id,d.dataChamado,d.senha,d.sala, d.preferencial) "+
					  "from Designacoes d where d.atendente.unidade.id = :idUnidade and d.dataChamado BETWEEN :dataInicio and :dataFim "+
					  "and d.status = :chamado and d.nucleo.nome IN :nucleos ORDER BY d.dataChamado ASC";
		TypedQuery<InfoDesignacao> query = getEm().createQuery(jpql, InfoDesignacao.class);
		
		query.setMaxResults(15);
		
		query.setParameter("dataInicio", c1.getTime());
		query.setParameter("dataFim", c2.getTime());
		query.setParameter("nucleos", nucleos);
		query.setParameter("chamado", StatusDesignacao.CHAMADO);
		query.setParameter("idUnidade", idUnidade);
		
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public void alterarStatusDesignacoes(Long id, StatusDesignacao status){
		Query query = getEm().createQuery("UPDATE Designacoes d SET d.status = :status WHERE d.id = :id");
		query.setParameter("id", id);
		query.setParameter("status", status);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * @param nome - String 
	 * @return Painel
	 *//*
	public List<String> obterUltimosChamados(Long idPainel) {
		String jpql = "select d.senha from Designacoes p where p.nome = :nome ";
		TypedQuery<Painel> query = getEm().createQuery(jpql, Painel.class);
		query.setParameter("nome", nome);
		//return query.getSingleResult();
	}*/
	
}