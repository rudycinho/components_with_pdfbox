package subsistema.pdf.utils;

import java.util.Calendar;
import java.util.Date;

public class Fecha{
	private String diaLiteral;
	private String mesLiteral;
	private String anioLiteral;
	private String horaLiteral;
	private String minutoLiteral;
	private String segundoLiteral;
	
	public Fecha(Date f) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(f);
		
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		int mes = calendario.get(Calendar.MONTH) + 1;
		int anio = calendario.get(Calendar.YEAR);
		
		int hora = calendario.get(Calendar.HOUR_OF_DAY);
		int minuto = calendario.get(Calendar.MINUTE);
		int segundo = calendario.get(Calendar.SECOND);
		
		diaLiteral = dia < 10 ? String.format("0%d", dia) : String.format("%d", dia);
		mesLiteral = mes < 10 ? String.format("0%d", mes) : String.format("%d", mes);
		anioLiteral = String.format("%d", anio);
		
		horaLiteral    = hora < 10 ? String.format("0%d", hora) : String.format("%d", hora);
		minutoLiteral  = minuto < 10 ? String.format("0%d", minuto) : String.format("%d", minuto);
		segundoLiteral = segundo < 10 ? String.format("0%d", segundo) : String.format("%d", segundo);
		
	}
	
	public String getDiaLiteral() {
		return diaLiteral;
	}
	
	public String getMesLiteral() {
		return mesLiteral;
	}
	
	public String getAnioLiteral() {
		return anioLiteral;
	}
	
	public String getHoraLiteral() {
		return horaLiteral;
	}
	
	public String getMinutoLiteral() {
		return minutoLiteral;
	}
	
	public String getSegundoLiteral() {
		return segundoLiteral;
	}
	
	public String getFormatoHoraFecha() {
		return String.format("%s:%s:%s %s/%s/%s",
				horaLiteral,
				minutoLiteral,
				segundoLiteral,
				diaLiteral,
				mesLiteral,
				anioLiteral
		);
	}
	
	public String getFormatoFecha() {
		return String.format("%s/%s/%s",
				diaLiteral,
				mesLiteral,
				anioLiteral
		);
	}

	public String getFormatoTitulo() {
		return String.format("%s_%s_%s_%s_%s_%s",
				diaLiteral,
				mesLiteral,
				anioLiteral,
				horaLiteral,
				minutoLiteral,
				segundoLiteral
		);
	}
}