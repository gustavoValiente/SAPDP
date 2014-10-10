package br.gov.ms.defensoria.intranet.sapdp.model.assistido.preso;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.gov.ms.defensoria.intranet.sapdp.model.assistido.Assistido;
import br.gov.ms.defensoria.intranet.sapdp.model.penal.Processos;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Entity
@Table(name = "TB_ASSISTIDO_PRESO")
public class AssistidoPreso {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name = "id_estabelecimento")
	private EstabelecimentoPenal estabelecimento;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private CondutaCarceraria condutaCarceraria;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Processos> processos;	
	@ManyToOne	
	@JoinColumn(name = "id_assistido")
	private Assistido assistido;
	
	public AssistidoPreso() {

	}

	public EstabelecimentoPenal getEstabelecimento() {
		return estabelecimento;
	}


	public void setEstabelecimento(EstabelecimentoPenal estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public CondutaCarceraria getCondutaCarceraria() {
		return condutaCarceraria;
	}

	public void setCondutaCarceraria(CondutaCarceraria condutaCarceraria) {
		this.condutaCarceraria = condutaCarceraria;
	}

	public List<Processos> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processos> processos) {
		this.processos = processos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Assistido getAssistido() {
		return assistido;
	}

	public void setAssistido(Assistido assistido) {
		this.assistido = assistido;
	}
	
	
}
