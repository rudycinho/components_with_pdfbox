package dto;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class FilaPagoMensualDTO {

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

	public FilaPagoMensualDTO(){ }

	public static FilaPagoMensualDTO getFake(){
		FilaPagoMensualDTO fila = new FilaPagoMensualDTO();

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

	public List<String> getContenido(){
		List<String> lista;
		lista = new LinkedList<String>();
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
