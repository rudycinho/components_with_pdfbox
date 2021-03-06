package subsistema.pdf.dto;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class FilaPagoMensualDTOPDF {

	private String numeroFila;
	private String fechaPago;
	private String saldoAmortizable;
	private String interes;
	private String amortizacion;
	private String cuotaParcial;
	private String seguroDesgravamen;
	private String subTotal;
	private String cipMensual;
	private String cuotaTotal;

	private static int count=0;

	public FilaPagoMensualDTOPDF(){ }

	public static FilaPagoMensualDTOPDF getFake(){
		FilaPagoMensualDTOPDF fila = new FilaPagoMensualDTOPDF();

		fila.numeroFila       = String.format("%d",count++);
		fila.fechaPago        = "";
		fila.saldoAmortizable = "10000.00";
		fila.amortizacion     = "928.19";
		fila.interes          = "1292.00";
		fila.cuotaParcial     = "2220.19";
		fila.seguroDesgravamen= "268.74";
		fila.subTotal         = "2488.92";
		fila.cipMensual       = "24.89";
		fila.cuotaTotal       = "2513.8";

		return fila;
	}

	public static FilaPagoMensualDTOPDF getFake2(){
		FilaPagoMensualDTOPDF fila = new FilaPagoMensualDTOPDF();

		fila.numeroFila       = "";
		fila.fechaPago        = "TOTAL";
		fila.saldoAmortizable = "TOTAL";
		fila.amortizacion     = "TOTAL";
		fila.interes          = "TOTAL";
		fila.cuotaParcial     = "TOTAL";
		fila.seguroDesgravamen= "TOTAL";
		fila.subTotal         = "TOTAL";
		fila.cipMensual       = "TOTAL";
		fila.cuotaTotal       = "TOTAL";

		return fila;
	}

	public List<String> getContenido(){
		List<String> lista;
		lista = new LinkedList<>();
		lista.add(numeroFila);
		lista.add(fechaPago);
		lista.add(saldoAmortizable);
		lista.add(amortizacion);
		lista.add(interes);
		lista.add(cuotaParcial);
		lista.add(seguroDesgravamen);
		lista.add(subTotal);
		lista.add(cipMensual);
		lista.add(cuotaTotal);
		
		return lista;
	}
}
