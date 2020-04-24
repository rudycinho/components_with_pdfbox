package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictamenDTOPDF {
	private String nombreCliente;
	private String ci;
	private String montoSolicitadoUFV;
	private String montoSolicitadoUS;
	private String montoSolicitadoBS;
	private String cambioUFV;
	private String cambioUS;
	private String cantidadDiasDeAmortizacion;
	private String plazoCuotas;
	private String tasaInteresFija;
	private String maximoComprometido;
	private String cuotaParcial;
	private String seguroDesgravamen;
	private String cip;
	private String cuotaTotal;
	private String sueldoLiquidoPagable;
	private String dictamenLiteral;
	private String porcentajeDestinadoAPago;

	public DictamenDTOPDF() { }

	public static DictamenDTOPDF getFake() {
		DictamenDTOPDF dictamenDTO = new DictamenDTOPDF();

		dictamenDTO.nombreCliente = "JORGE LUIS PATTY";
		dictamenDTO.ci="3456656";
		dictamenDTO.montoSolicitadoUFV="45250.92";
		dictamenDTO.montoSolicitadoUS ="14367.82";
		dictamenDTO.montoSolicitadoBS ="100000.00";
		dictamenDTO.cambioUFV="2.2099";//
		dictamenDTO.cambioUS ="6.96";

		dictamenDTO.montoSolicitadoBS ="100000.00";
		dictamenDTO.cantidadDiasDeAmortizacion="30";//
		dictamenDTO.plazoCuotas= "180";//
		dictamenDTO.tasaInteresFija="4.5";//
		dictamenDTO.maximoComprometido="50";

		dictamenDTO.cuotaParcial="765.00";//
		dictamenDTO.seguroDesgravamen="78.00";//
		dictamenDTO.cip="8.43";//
		dictamenDTO.cuotaTotal="851.43";//

		dictamenDTO.sueldoLiquidoPagable="3000.00";//
		dictamenDTO.dictamenLiteral="PROCEDENTE";
		dictamenDTO.porcentajeDestinadoAPago="28.39";

		return dictamenDTO;
	}
}
