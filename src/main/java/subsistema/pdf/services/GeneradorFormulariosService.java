package subsistema.pdf.services;

import com.lowagie.text.DocumentException;
import subsistema.pdf.dto.*;
import subsistema.pdf.form.*;
import subsistema.pdf.utils.Fecha;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class GeneradorFormulariosService {


	private final String DEST = "/home/rudy/forms";

	public String FormularioInformacionUsuario(
			UsuarioDTOPDF usuarioEditado,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {

		Date fechaExportacion        = new Date();

		String nombreArchivo = String.format("Formulario_Usuario_%s_%s_%s.pdf",
				usuarioEditado.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form1.pdf");

		new FormularioInformacionUsuario(ruta,usuarioEditado,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionSucursal(
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {

		Date fechaExportacion        = new Date();

		String nombreArchivo = String.format("Formulario_Sucursal_%s_%s_%s.pdf",
				sucursal.getId().toString(),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form2.pdf");

		new FormularioInformacionSucursal(ruta,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioInformacionPerfil(
			PerfilDTOPDF perfil,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaExportacion = new Date();

		String nombreArchivo = String.format("Formulario_Perfil_%s_%s_%s.pdf",
				perfil.getId().toString(),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form3.pdf");

		new FormularioInformacionPerfil(ruta,perfil,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioAjustesSistema(
			AjustesSistemaDTOPDF ajustesSistema,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaExportacion               = new Date();

		String nombreArchivo = String.format("Formulario_Ajustes_Sistema_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form4.pdf");

		new FormularioAjustesSistema(ruta,ajustesSistema,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioAfiliacion(
			AfiliadoDTOPDF afiliado,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal,
			List<EstadoAfiliacionDTOPDF> estados
	) throws IOException {
		Date fechaExportacion = new Date();

		String nombreArchivo = String.format("Formulario_Afiliacion_%s_%s_%s.pdf",
				afiliado.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form5.pdf");

		new FormularioAfiliacion(ruta,afiliado,estados,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioDesafiliacion(
			DesafiliadoDTOPDF desafiliado,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaExportacion = new Date();

		String nombreArchivo = String.format("Formulario_Desafiliacion_%s_%s_%s.pdf",
				desafiliado.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form6.pdf");

		new FormularioDesafiliacion(ruta,desafiliado,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

    public String FormularioPrecalificadorPrestamos(
			DictamenDTOPDF dictamen,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaExportacion = new Date();


		String nombreArchivo = String.format("Formulario_Precalificador_Prestamos_%s_%s_%s.pdf",
				dictamen.getCi().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form7.pdf");


		new FormularioPrecalificadorPrestamos(ruta,dictamen,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
    }

	public String FormularioPlanPagos(
			DictamenDTOPDF dictamen,
			PlanDePagosDTOPDF planDePagos,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaExportacion = new Date();


		String nombreArchivo = String.format("Formulario_Plan_Pagos_%s_%s_%s.pdf",
				dictamen.getCi().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form8.pdf");


		new FormularioPlanPagos(ruta,dictamen,planDePagos,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioDefinicionCredito(
			DefinicionCreditoDTOPDF definicionCredito,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException, DocumentException {

		Date fechaExportacion                     = new Date();

		String nombreArchivo = String.format("Formulario_Definicion_Credito_%s_%s_%s.pdf",
				definicionCredito.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form9.pdf");

		new FormularioDefinicionCredito(ruta,definicionCredito,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioRequisitosSolicitudCredito(
			String porcionHTML,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException, DocumentException {
		Date fechaExportacion        = new Date();

		String nombreArchivo = String.format("Formulario_Requisitos_Solicitud_Credito_%s_%s.pdf",
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaExportacion).getFormatoTitulo());

		//String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form10.pdf");

		new FormularioRequisitosSolicitudCredito(ruta,porcionHTML,usuarioEditor,sucursal,fechaExportacion);

		return ruta;
	}

	public String FormularioSolicitudCreditoVivienda(
			SolicitudCreditoViviendaDTOPDF solicitud,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal
	) throws IOException {
		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Solicitud_Credito_%s_%s_%s.pdf",
				solicitud.getCedulaIdentidadSolicitante().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaCreacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form11.pdf");

		new FormularioSolicitudCreditoVivienda(ruta,solicitud,usuarioEditor,sucursal,fechaCreacion);

        return ruta;
	}

    public String FormularioCarpetaComentarios(
    		ComentariosDTOPDF comentarios,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal) throws IOException {
		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Carpeta_Comentarios_%s_%s_%s.pdf",
				comentarios.getCarnetAfiliado().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaCreacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form12.pdf");

		new FormularioCarpetaComentarios(ruta,comentarios,usuarioEditor,sucursal,fechaCreacion);

		return ruta;
    }

	public String FormularioCarpetaDatosAfiliadio(
			DatosCarpetaAfiliadioDTOPDF datos,
			UsuarioDTOPDF usuarioEditor,
			SucursalDTOPDF sucursal) {
		Date fechaCreacion = new Date();

		String nombreArchivo = String.format("Formulario_Carpeta_Datos_%s_%s_%s.pdf",
				datos.getCarnetIdentidad().trim().replaceAll(" ", "_"),
				usuarioEditor.getNombreCompleto().trim().replaceAll(" ", "_"),
				new Fecha(fechaCreacion).getFormatoTitulo());

//		String ruta = String.format("%s%s%s",DEST,"/",nombreArchivo);
		String ruta = String.format("%s%s%s",DEST,"/","form13.pdf");

		new FormularioCarpetaDatosAfiliadio(ruta,datos,usuarioEditor,sucursal,fechaCreacion);

		return ruta;
	}
}
