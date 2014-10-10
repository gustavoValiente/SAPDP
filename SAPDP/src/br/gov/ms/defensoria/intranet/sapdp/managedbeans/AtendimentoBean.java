package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import br.gov.ms.defensoria.intranet.sapdp.lazymodels.EntityLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.lazymodels.SimpleAtendimentoLazyModel;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.ItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SimpleAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.SubItemAtendimento;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.AtendimentoRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class AtendimentoBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.atendimento.Atendimento
	@EJB
	private AtendimentoRepositorio aRep;
	private Atendimento atendimento = new Atendimento();
	private SimpleAtendimento simpleAtendimento = new SimpleAtendimento();
	private List<SimpleAtendimento> atendimentos = new ArrayList<SimpleAtendimento>();
	private List<Atendimento> atendimentos2 = new ArrayList<Atendimento>();
	private EntityLazyModel atendimentosLazy;
	private LazyDataModel<SimpleAtendimento> simpleAtendimentosLazy;
	private Boolean juizado;

	@ManagedProperty(value = "#{designacaoBean}")
	private DesignacaoBean designacaoBean;

	public AtendimentoBean() {

	}

	public EntityLazyModel getAllAtendimentos() {
		if (atendimentosLazy == null) {
			atendimentosLazy = new EntityLazyModel(atendimento);
		}
		return atendimentosLazy;
	}

	/**
	 * Insere/Altera o objeto Atendimento
	 */
	public void insert(Atendimento atendimento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println(">>>>>>>>>>>>>>"
				+ this.designacaoBean.getDesignacao().getAssistido().getNome());
		atendimento.setDesignacao(this.designacaoBean.getDesignacao());
		atendimento.setUnidade(this.designacaoBean.getUsuarioServiceBean()
				.obterUsuarioDaSessao().getUnidade());
		atendimento.setIdDefensoriaDefensor(this.designacaoBean.getDesignacao()
				.getDefensor().getIdDefensoria());
		if (this.designacaoBean.getDesignacao().getLoginSubstituicao() != null) {
			atendimento.setIdDefensoriaSubstituicao(this.designacaoBean
					.getUsuarioServiceBean()
					.obterUsuarioPorLogin(
							this.designacaoBean.getDesignacao()
									.getLoginSubstituicao()).getIdDefensoria());
		}
		this.designacaoBean.getDesignacao().setAssistido(
				this.designacaoBean.getDesignacao().getAssistido());
		this.designacaoBean.getDesignacao().setDefensor(
				this.designacaoBean.getDesignacao().getDefensor());

		if (atendimento.getId() == null) {
			atendimento.setUsuarioCadastro(this.designacaoBean
					.getUsuarioServiceBean().obterUsuarioDaSessao().getLogin());
			atendimento.setDataAtendimento(new Date());
			atendimento.setUltimoAtendimento("SIM");
			atendimento.setJuizado(this.getJuizado() == true ? 1 : 0);
			this.aRep.insert(atendimento);
			this.atendimento = new Atendimento();
			fc.addMessage(null, new FacesMessage(
					"Atendimento cadastrado com sucesso!"));
			this.designacaoBean
					.alteraStatusDesignacao(StatusDesignacao.CONCLUIDO);
		} else {
			atendimento.setUsuarioAlteracao(this.designacaoBean
					.getUsuarioServiceBean().obterUsuarioDaSessao().getLogin());
			atendimento.setDataAlteracao(new Date());
			atendimento.setJuizado(this.getJuizado() == true ? 1 : 0);
			this.atendimento = this.aRep.update(this.atendimento);
			fc.addMessage(null, new FacesMessage(
					"Atendimento Alterado com sucesso!"));
			if (this.designacaoBean.getSimpleDesignacao() != null) {
				System.out.println("AIIIIIIIIIIIIII IDPAI"
						+ this.designacaoBean.getSimpleDesignacao()
								.getIdAtendimentoPai());
				this.montaListaAtendimentos(this.designacaoBean
						.getSimpleDesignacao().getIdAtendimentoPai());
			}
		}
		criarNovo();
	}

	public Atendimento obterAtendimentoPorId(Long idAtendimento) {
		System.out.println("X>>>>>>>>>X>X>X>>X>X" + idAtendimento);
		return this.aRep.obterAtendimentoPorId(idAtendimento);
	}

	public void alteraUtimoAtendimento(Long idAtendimentoPai, String tipo) {
		System.out.println(">>>>>>>>>>>>>>>>> ID PAI" + idAtendimentoPai);
		this.aRep.alteraUltimoAtendimento(idAtendimentoPai, tipo);
	}

	/**
	 * Busca todos atendimentos
	 */
	public List<Atendimento> getSelecionarTudo() {
		return aRep.selectAll();
	}

	/**
	 * Obter atendimentos do dia por Defensor
	 * 
	 * @param loginDefensor
	 * @return List<Atendimento>
	 */
	public LazyDataModel<SimpleAtendimento> obterAtendimentosPorDefensorData(
			String loginDefensor) {
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + loginDefensor);
			if (simpleAtendimentosLazy == null) {
				simpleAtendimentosLazy = new SimpleAtendimentoLazyModel(
						loginDefensor);
			}
			return simpleAtendimentosLazy;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Obter hist�rico por atendimento
	 * 
	 * @param idAtendimentoPai
	 * @return List<Atendimento>
	 */
	public List<Atendimento> montaListaAtendimentos(Long idAtendimentoPai) {
		Atendimento atend;
		try {

			this.atendimentos2 = new ArrayList<Atendimento>();

			do {
				atend = this.aRep.obterAtendimentoPorId(idAtendimentoPai);
				idAtendimentoPai = atend.getDesignacao().getIdAtendimentoPai();
				this.atendimentos2.add(atend);

			} while (atend.getDesignacao().getIdAtendimentoPai() != null);

			if (idAtendimentoPai != null) {
				atend = this.aRep.obterAtendimentoPorId(idAtendimentoPai);
				this.atendimentos2.add(atend);
			}

			return this.atendimentos2;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Atendimento>();
		}
	}

	/**
	 * Obter atendimentos por Assitido
	 * 
	 * @param idAssistido
	 * @return List<Atendimento>
	 */
	public List<SimpleAtendimento> obterAtendimentosPorAssistido(
			Long idAssistido) {
		try {
			if (this.atendimentos.isEmpty())
				this.atendimentos = new ArrayList<SimpleAtendimento>();
			this.atendimentos = this.aRep
					.obterAtendimentosPorAssistido(idAssistido);
			return this.atendimentos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<SimpleAtendimento>();
		}

	}

	/**
	 * Exclui um objeto Atendimento
	 */
	public void excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		this.aRep.remove(this.atendimento);
		fc.addMessage(null, new FacesMessage(
				"Atendimento exclu�do com sucesso!"));
	}

	/**
	 * Cria um novo atendimento
	 */
	public void criarNovo() {
		this.atendimento = new Atendimento();
		this.atendimento.setItem(new ItemAtendimento());
		this.atendimento.setSubItem(new SubItemAtendimento());
		this.setJuizado(false);
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public SimpleAtendimento getSimpleAtendimento() {
		return simpleAtendimento;
	}

	public void setSimpleAtendimento(SimpleAtendimento simpleAtendimento) {
		this.simpleAtendimento = simpleAtendimento;
	}

	public List<SimpleAtendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(List<SimpleAtendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

	public List<Atendimento> getAtendimentos2() {
		return atendimentos2;
	}

	public void setAtendimentos2(List<Atendimento> atendimentos2) {
		this.atendimentos2 = atendimentos2;
	}

	public EntityLazyModel getAtendimentosLazy() {
		return atendimentosLazy;
	}

	public void setAtendimentosLazy(EntityLazyModel atendimentosLazy) {
		this.atendimentosLazy = atendimentosLazy;
	}

	public DesignacaoBean getDesignacaoBean() {
		return designacaoBean;
	}

	public void setDesignacaoBean(DesignacaoBean designacaoBean) {
		this.designacaoBean = designacaoBean;
	}

	public Boolean getJuizado() {
		return juizado;
	}

	public void setJuizado(Boolean juizado) {
		this.juizado = juizado;
	}
}
