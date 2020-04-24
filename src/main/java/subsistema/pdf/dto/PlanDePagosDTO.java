package subsistema.pdf.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class PlanDePagosDTO {
	private List<FilaPagoMensualDTO> planDePagosLista;

	public PlanDePagosDTO(){
		planDePagosLista = new LinkedList<>();
	}

	public static PlanDePagosDTO getFake(){
		PlanDePagosDTO planDePagos = new PlanDePagosDTO();
		for(int i=0; i<70; i++){
			planDePagos.planDePagosLista.add(FilaPagoMensualDTO.getFake());
		}
		return planDePagos;
	}

	public List<List<String>> getContenido(){
		return planDePagosLista
				.stream()
				.map(s -> s.getContenido())
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
		return FilaPagoMensualDTO.getFake2().getContenido();
	}
	
}
