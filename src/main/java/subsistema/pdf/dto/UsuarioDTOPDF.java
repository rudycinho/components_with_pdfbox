package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;
import subsistema.pdf.utils.Fecha;

import java.util.Calendar;

@Getter
@Setter
public class UsuarioDTOPDF {
	private Long Id;

	private String fechaRegistro;

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
	
	public UsuarioDTOPDF() {}

	public static UsuarioDTOPDF getFake(){
		UsuarioDTOPDF usuarioDTO        = new UsuarioDTOPDF();

		usuarioDTO.Id                = 345L;

		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, Calendar.MAY,28);
		usuarioDTO.fechaRegistro     = new Fecha(calendar.getTime()).getFormatoFecha();

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
