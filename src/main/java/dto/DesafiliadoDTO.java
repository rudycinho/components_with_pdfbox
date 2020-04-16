package dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DesafiliadoDTO {
	private Long Id;

	private Date fechaSolicitud;

	private String grado;
	private String nombreCompleto;
	private String carnetIdentidad;
	private String unidadPolicial;
	private String departamento;

	private String causaDesafiliacion;

	private String observaciones;

	public DesafiliadoDTO() { }

	public static DesafiliadoDTO getFake(){
		DesafiliadoDTO desafiliadoDTO = new DesafiliadoDTO();

		desafiliadoDTO.Id                = 204L;

		desafiliadoDTO.fechaSolicitud    = new Date(2020,8,6);

		desafiliadoDTO.grado             = "CORONEL";
		desafiliadoDTO.nombreCompleto    = "JUAN JOSE PEREZ CAMACHO DE LA SANTISIMA TRINIDAD";
		desafiliadoDTO.carnetIdentidad   = "9977761 CB";
		desafiliadoDTO.unidadPolicial    = "EPI NORTE";
		desafiliadoDTO.departamento      = "COCHABAMBA";

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
