/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

/**
 * @author vromero
 *
 */
public class PedidosCliente {
	
	private String cpedido;
	private String referencia;
	private Double montoPedido;
	private Double montoCancelado;
	private int partidas;
	private int partidasCanceladas;
	private int mes;
	private String esac;
	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}
	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the montoPedido
	 */
	public Double getMontoPedido() {
		return montoPedido;
	}
	/**
	 * @param montoPedido the montoPedido to set
	 */
	public void setMontoPedido(Double montoPedido) {
		this.montoPedido = montoPedido;
	}
	/**
	 * @return the montoCancelado
	 */
	public Double getMontoCancelado() {
		return montoCancelado;
	}
	/**
	 * @param montoCancelado the montoCancelado to set
	 */
	public void setMontoCancelado(Double montoCancelado) {
		this.montoCancelado = montoCancelado;
	}
	/**
	 * @return the partidas
	 */
	public int getPartidas() {
		return partidas;
	}
	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(int partidas) {
		this.partidas = partidas;
	}
	/**
	 * @return the partidasCanceladas
	 */
	public int getPartidasCanceladas() {
		return partidasCanceladas;
	}
	/**
	 * @param partidasCanceladas the partidasCanceladas to set
	 */
	public void setPartidasCanceladas(int partidasCanceladas) {
		this.partidasCanceladas = partidasCanceladas;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}
	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}
	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}
	/**
	 * @param esac the esac to set
	 */
	public void setEsac(String esac) {
		this.esac = esac;
	}

}

