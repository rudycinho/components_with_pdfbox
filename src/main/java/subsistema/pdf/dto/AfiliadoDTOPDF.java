package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;
import subsistema.pdf.utils.Fecha;

import java.util.Calendar;

@Getter
@Setter
public class AfiliadoDTO{
	private Long Id;
	private String fechaSolicitud;

	private String grado;
	private String nombreCompleto;
	private String carnetIdentidad;
	private String fechaNacimiento;
	private String telefono;
	private String celular;
	private String correoElectronico;
	private String unidadPolicial;
	private String departamento;

	private String observaciones;

	public AfiliadoDTO(){ }

	public static AfiliadoDTO getFake(){
		AfiliadoDTO afiliadoDTO = new AfiliadoDTO();

		afiliadoDTO.Id                = 204L;

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(2010, Calendar.MAY,2);
		afiliadoDTO.fechaSolicitud    = new Fecha(calendar1.getTime()).getFormatoFecha();

		afiliadoDTO.grado             = "CORONEL";
		afiliadoDTO.nombreCompleto    = "JUAN JOSE PEREZ CAMACHO DE LA SANTISIMA TRINIDAD";
		afiliadoDTO.carnetIdentidad   = "9977761 CB";

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2000, Calendar.OCTOBER,8);
		afiliadoDTO.fechaNacimiento   = new Fecha(calendar2.getTime()).getFormatoFecha();

		afiliadoDTO.telefono          = "44546474";
		afiliadoDTO.celular           = "77476787";
		afiliadoDTO.correoElectronico = "juan.perez@gmail.com";
		afiliadoDTO.unidadPolicial    = "EPI NORTE";
		afiliadoDTO.departamento      = "COCHABAMBA";

		afiliadoDTO.observaciones =
				"Quisque in tincidunt felis, quis faucibus tortor.\n"+
				"Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
				"Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
				"Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
				"Ut congue erat vel ullamcorper tempor.\n"+
				"Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

		return afiliadoDTO;
	}
}
