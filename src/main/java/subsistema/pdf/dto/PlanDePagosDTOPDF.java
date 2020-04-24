package subsistema.pdf.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class PlanDePagosDTOPDF {
	private final List<FilaPagoMensualDTOPDF> planDePagosLista;

	public PlanDePagosDTOPDF(){
		planDePagosLista = new LinkedList<>();
	}

	public static PlanDePagosDTOPDF getFake(){
		PlanDePagosDTOPDF planDePagos = new PlanDePagosDTOPDF();
		for(int i=0; i<70; i++){
			planDePagos.planDePagosLista.add(FilaPagoMensualDTOPDF.getFake());
		}
		return planDePagos;
	}

	public List<List<String>> getContenido(){
		return planDePagosLista
				.stream()
				.map(FilaPagoMensualDTOPDF::getContenido)
				.collect(Collectors.toList());
	}

	public List<String> getContenidoCabecera() {
		List<String> lista = new LinkedList<>();
		lista.add("Numero Fila");
		lista.add("Fecha Pago");
		lista.add("Saldo Amortizable");
		lista.add("Amortizacion");
		lista.add("Interes");
		lista.add("Cuota Parcial");
		lista.add("Seguro Desgravamen");
		lista.add("SubTotal");
		lista.add("CIP Mensual");
		lista.add("Cuota Total");
		return lista;
	}

	public List<String> getSumatoria(){
		return FilaPagoMensualDTOPDF.getFake2().getContenido();
	}
	
}
