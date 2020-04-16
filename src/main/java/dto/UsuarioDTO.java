package dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioDTO {
	private Long Id;

	private Date   fechaRegistro;

	private String nombreCompleto;

	private String carnetIdentidad;
	private String telefono;
	private String correoElectronico;

	private String nombreCargo;
	private String nombreArea;
	private String nombreSucursal;

	private String nombreUsuario;
	private String estado;

	private String observaciones;
	
	public UsuarioDTO() {}

	public static UsuarioDTO getFake(){
		UsuarioDTO usuarioDTO        = new UsuarioDTO();

		usuarioDTO.Id                = 345L;
		usuarioDTO.fechaRegistro     = new Date(2018,5,8);

		usuarioDTO.nombreCompleto    = "JUAN JOSE PEREZ CAMACHO DE LA SANTISIMA TRINIDAD";

		usuarioDTO.carnetIdentidad   = "9977761";
		usuarioDTO.telefono          = "22334443";
		usuarioDTO.correoElectronico = "juan.perez@gmail.com";

		usuarioDTO.nombreCargo       = "JEFE";
		usuarioDTO.nombreArea        = "SISTEMAS";
		usuarioDTO.nombreSucursal    = "EL RELOJ";

		usuarioDTO.nombreUsuario     = "juancho";
		usuarioDTO.estado            = "HABILITADO";

		usuarioDTO.observaciones     = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
						"Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
						"Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
						"Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
						"Ut congue erat vel ullamcorper tempor.\n"+
						"Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

		return usuarioDTO;
	}
	
}
