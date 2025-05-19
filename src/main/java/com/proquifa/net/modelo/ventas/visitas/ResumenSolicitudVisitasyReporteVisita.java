package com.proquifa.net.modelo.ventas.visitas;

import java.util.List;

public class ResumenSolicitudVisitasyReporteVisita {
	
	private List<VisitaCliente> visitas;
	private List<ReportarVisita> reporteVisita;

	public List<VisitaCliente> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<VisitaCliente> visitas) {
		this.visitas = visitas;
	}

	public List<ReportarVisita> getReporteVisita() {
		return reporteVisita;
	}

	public void setReporteVisita(List<ReportarVisita> reporteVisita) {
		this.reporteVisita = reporteVisita;
	} 

}
