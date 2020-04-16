package services;

import dto.*;
import form.*;
import utils.Fecha;

import java.io.IOException;
import java.util.Date;

public class GeneradorFormulariosService {
	
	private final String DEST = "~";

	public String FormularioInformacionUsuario() throws IOException {

		Date fechaExportacion        = new Date();
		UsuarioDTO usuarioEditadoDTO = UsuarioDTO.getFake();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Usuario_%s_%s_%s.pdf",
				usuarioEditadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionUsuario(ruta,usuarioEditadoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionSucursal() throws IOException {

		Date fechaExportacion        = new Date();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Sucursal_%s_%s_%s.pdf",
				sucursalDTO.getId().toString(),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionSucursal(ruta,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionPerfil() throws IOException {
		Date fechaExportacion = new Date();
		PerfilDTO perfilDTO          = PerfilDTO.getFake();
		UsuarioDTO usuarioEditorDTO  = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO      = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Perfil_%s_%s_%s.pdf",
				perfilDTO.getId().toString(),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioInformacionPerfil(ruta,perfilDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioAjustesSistema() throws IOException {
		Date fechaExportacion               = new Date();
		AjustesSistemaDTO ajustesSistemaDTO = AjustesSistemaDTO.getFake();
		UsuarioDTO  usuarioEditorDTO        = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO             = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Ajustes_Sistema_%s_%s.pdf",
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioAjustesSistema(ruta,ajustesSistemaDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioAfiliacion() throws IOException {
		Date fechaExportacion = new Date();
		AfiliadoDTO afiliadoDTO     = AfiliadoDTO.getFake();
 		UsuarioDTO usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO     = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Afiliacion_%s_%s_%s.pdf",
				afiliadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioAfiliacion(ruta,afiliadoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}

	public String FormularioDesafiliacion() throws IOException {
		Date fechaExportacion = new Date();
		DesafiliadoDTO desafiliadoDTO  = DesafiliadoDTO.getFake();
		UsuarioDTO usuarioEditorDTO = UsuarioDTO.getFake();
		SucursalDTO sucursalDTO     = SucursalDTO.getFake();

		String nombreArchivo = String.format("Formulario_Desafiliacion_%s_%s_%s.pdf",
				desafiliadoDTO.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditorDTO.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioDesafiliacion(ruta,desafiliadoDTO,usuarioEditorDTO,sucursalDTO,fechaExportacion);

		return ruta;
	}


	/*public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado, 
			Usuario usuarioEditor) throws IOException {
		
		Date fechaCreacion = new Date();
		
		String nombreArchivo = String.format("Formulario_Solicitud_Credito_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				usuarioEditado.getId(),
				convertirDateToString(fechaCreacion));
		
		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		
		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);
		
		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
        return ruta;
	}

*//*

	public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {
		
		Date fechaCreacion = new Date();
		
		String ruta = String.format("%s%s%s",DEST,"/","form.pdf");
		
		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);
		
		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
        return ruta;
	}




	


	private final String DEST = "/home/rudy";

	public String FormularioInformacionUsuario(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Usuario_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				usuarioEditado.getId(),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioInformacionSucursal(
			Sucursal sucursal,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Sucursal_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				sucursal.getNombre().toString().trim().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioSucursal(ruta,sucursal,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioAjustesSistema(
			AjustesSistema ajustes,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Ajustes_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioAjustesSistema(ruta,ajustes,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioAfiliacion(
			Afiliado afiliado,
			Usuario usuarioEditor,
			List<EstadoAfiliado> estados) {

		Date fechaCreacion = new Date();
		String ci = afiliado.getCarnetIdentidad();

		String nombreArchivo = String.format("Formulario_Afiliado_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				ci == null ? "no_carnet": ci.toString().trim().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		//FormularioAfiliado formularioAfiliado = new FormularioAfiliado(ruta,afiliado,usuarioEditor,estados,fechaCreacion);
        return ruta;
	}

	public String FormularioDesafiliacion(
			Afiliado afiliado,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();
		String ci = afiliado.getCarnetIdentidad();

		String nombreArchivo = String.format("Formulario_Desafiliado_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				ci == null ? "no_carnet": ci.toString().trim().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		//new FormularioDesafiliado(ruta,afiliado,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioInformacionPerfil(
			Perfil perfil,
			Sucursal sucursal,
			Usuario usuarioEditor) {
		return null;

	}

	// 	COMPLEX

	public String FormularioPlanPagos(
			PlanDePagos planDePagos,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();
		String ci = planDePagos.getDictamen().getCi();

		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				ci==null ? "no_carnet" : ci.toString().trim().replace(" " , "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioPlanPagos(ruta,planDePagos,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioPrecalificadorPrestamos(
			Dictamen dictamen,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();
		String ci = dictamen.getCi();

		String nombreArchivo = String.format("Formulario_Precalificador_Prestamos_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				ci==null ? "no_carnet" : ci.toString().trim().replace(" " , "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		//new FormularioPrecalificadorPrestamos(ruta,dictamen,usuarioEditor,fechaCreacion);
        return ruta;

	}

	public String FormularioRequisitosSolicitudCredito(
			String htmlLiteral,
			Usuario usuarioEditor) {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioRequisitosCredito(ruta,htmlLiteral,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Solicitud_Credito_%s_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				usuarioEditado.getId(),
				convertirDateToString(fechaCreacion));

		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);

		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);

		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
        return ruta;
	}

	public String FormularioSolicitudCreditoVivienda(
			Usuario usuarioEditado,
			Usuario usuarioEditor) throws IOException {

		Date fechaCreacion = new Date();

		String ruta = String.format("%s%s%s",DEST,"/","form.pdf");

		new FormularioUsuario(ruta,usuarioEditado,usuarioEditor,fechaCreacion);

		new FormularioSolicitudCreditoVivienda(ruta,usuarioEditor,fechaCreacion);
		return ruta;
	}

 */
}
