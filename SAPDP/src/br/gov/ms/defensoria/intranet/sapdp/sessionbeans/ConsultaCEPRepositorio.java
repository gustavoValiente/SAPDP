package br.gov.ms.defensoria.intranet.sapdp.sessionbeans;

import java.rmi.RemoteException;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.tempuri.IEnderecoServiceProxy;

/**
 * 
 * @author Equipe de desenvolvimento DPGE-MS.
 * @since JDK7
 * @version 1.0 - 2014
 * 
 */
@Startup
@Singleton
public class ConsultaCEPRepositorio {
	public org.datacontract.schemas._2004._07.SGEN_ModelData_Logic_SGENContext_Model.MEndereco[] serviceCep;

	public ConsultaCEPRepositorio() {

	}

	public void consultarCEP(String cep) {
		try {
			this.serviceCep = new IEnderecoServiceProxy()
					.buscarEnderecoPorFiltrosDiversos(cep);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int retornaTamanho() {
		return this.serviceCep.length;
	}

	public org.datacontract.schemas._2004._07.SGEN_ModelData_Logic_SGENContext_Model.MEndereco[] getServiceCep() {
		return serviceCep;
	}

	public void setServiceCep(
			org.datacontract.schemas._2004._07.SGEN_ModelData_Logic_SGENContext_Model.MEndereco[] serviceCep) {
		this.serviceCep = serviceCep;
	}

}
