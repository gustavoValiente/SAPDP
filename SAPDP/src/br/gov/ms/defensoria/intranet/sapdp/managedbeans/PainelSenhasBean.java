package br.gov.ms.defensoria.intranet.sapdp.managedbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.gov.ms.defensoria.intranet.sapdp.model.atendimento.StatusDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.InfoDesignacao;
import br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel;
import br.gov.ms.defensoria.intranet.sapdp.sessionbeans.PainelRepositorio;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@ViewScoped
@ManagedBean
public class PainelSenhasBean {
	// Objetos manipuladores e representantes da classe
	// br.gov.ms.defensoria.intranet.sapdp.model.paineis.Painel
	@EJB
	private PainelRepositorio pRep;
	private Painel painel = new Painel();
	private Long idPainel = 0L, idUnidade = 0L;
	private InfoDesignacao infoDesignacao = new InfoDesignacao();
	private List<InfoDesignacao> listaCache = new ArrayList<InfoDesignacao>();

	public PainelSenhasBean() {

	}

	@PostConstruct
	public void init() {
		try {
			Map<String, String> mapa = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			this.idPainel = Long.parseLong(mapa.get("codPainel"));
			this.idUnidade = Long.parseLong(mapa.get("codUnidade"));
			this.painel = this.pRep.obterPainelPorId(this.idPainel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregaCacheDeSenhas(){
		this.listaCache = this.pRep
				.obterUltimaSenhaPorDesignacao(this.idUnidade, this.painel.getNucleos());
	}
	
	public void carregarUltimasSenhasDoPainel() {
		if(!this.listaCache.isEmpty()){
			this.infoDesignacao = this.listaCache.get(0);
			this.pRep.alterarStatusDesignacoes(this.infoDesignacao.getIdDesignacao(), StatusDesignacao.PAINEL);
			this.listaCache.remove(0);
		}else{
			carregaCacheDeSenhas();
		}		
	}

	public InfoDesignacao getInfoDesignacao() {
		return infoDesignacao;
	}

	public void setInfoDesignacao(InfoDesignacao infoDesignacao) {
		this.infoDesignacao = infoDesignacao;
	}

	public Painel getPainel() {
		return painel;
	}

	public void setPainel(Painel painel) {
		this.painel = painel;
	}

}
