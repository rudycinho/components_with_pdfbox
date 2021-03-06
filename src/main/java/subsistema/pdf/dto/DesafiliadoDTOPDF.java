package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;
import subsistema.pdf.utils.Fecha;

import java.util.Calendar;

@Getter
@Setter
public class DesafiliadoDTOPDF {
	private Long Id;

	private String fechaSolicitud;

	private String grado;
	private String nombreCompleto;
	private String carnetIdentidad;
	private String unidadPolicial;
	private String departamento;

	private String causaDesafiliacion;

	private String observaciones;

	public DesafiliadoDTOPDF() { }

	public static DesafiliadoDTOPDF getFake(){
		DesafiliadoDTOPDF desafiliadoDTO = new DesafiliadoDTOPDF();

		desafiliadoDTO.Id                = 204L;

		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, Calendar.MAY,28);
		desafiliadoDTO.fechaSolicitud  = new Fecha(calendar.getTime()).getFormatoFecha();

		desafiliadoDTO.grado           = "CORONEL";
		desafiliadoDTO.nombreCompleto  = "JUAN JOSE PEREZ CAMACHO DE LA SANTISIMA TRINIDAD";
		desafiliadoDTO.carnetIdentidad = "9977761 CB";
		desafiliadoDTO.unidadPolicial  = "EPI NORTE";
		desafiliadoDTO.departamento    = "COCHABAMBA";

		desafiliadoDTO.causaDesafiliacion= "Quisque in tincidunt felis, quis faucibus tortor.\n"+
				"Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
				"Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
				"Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
				"Ut congue erat vel ullamcorper tempor.\n"+
				"Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

		desafiliadoDTO.observaciones     = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
						"Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
						"Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
						"Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
						"Ut congue erat vel ullamcorper tempor.\n"+
						"Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

		return desafiliadoDTO;
	}
}
