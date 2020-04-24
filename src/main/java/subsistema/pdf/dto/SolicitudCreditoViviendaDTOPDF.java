package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;
import subsistema.pdf.utils.Fecha;
import subsistema.pdf.utils.enums.TipoCreditoEnum;
import subsistema.pdf.utils.enums.TipoGarantiaEnum;
import subsistema.pdf.utils.enums.TipoViviendaEnum;

import java.util.Calendar;

@Getter
@Setter
public class SolicitudCreditoViviendaDTOPDF {

	// INICIO
	private String numeroCarpeta;
	private String numeroRegistro;
	
	// TIPO CREDITO
	private TipoCreditoEnum tipoCreditoODestinoPrestamoSolicitado;
	
	// TIPO GARANTIA
	private TipoGarantiaEnum tipoGarantia;
	
	// MONTO PRESTAMO SOLICITADO

	private String montoPrestamoSolicitadoBs;
	private String montoPrestamoSolicitadoUFV;
	private String tipoCambioOficialUFV;

	// DATOS SOLICITANTE

	private String apellidoPaternoSolicitante;
	private String apellidoMaternoSolicitante;
	private String nombreSolicitante;
	private String gradoSolicitante;

	private String cedulaIdentidadSolicitante;
	private String categoriaPorcentajeSolicitante;
	private String unidadSolicitante;
	private String liquidoPagableBsSueldoSolicitante;
	private String antiguedadBsSolicitante;
	private String postGradoBsSolicitante;
	private String bonoBsSegSolicitante;
	private String aniosServicioSolicitante;

	private String fechaNacimientoSolicitanteDia;
	private String fechaNacimientoSolicitanteMes;
	private String fechaNacimientoSolicitanteAnio;

	private String ciudadLocalidadSolicitante;
	private String provinciaSolicitante;
	private String departamentoSolicitante;
	 
	private String edadSolicitante;
	private String estadoCivilSolicitante;
	private String dependientesSolicitante;
	private String totalGrupoFamiliarSolicitante;

	private String calleAvenidaSolicitante;
	private String numeroCasaSolicitante;
	private String zonaBarrioSolicitante;

	private String numeroTelefonoDomicilioSolicitanteDomilicioActual;
	private String numeroCelularSolicitanteDomilicioActual;
	private String numeroTelefonoLaboralSolicitanteDomilicioActual;
	private String numeroTelefonoReferenciaSolicitanteDomilicioActual;

	private String unidadPolicialSolicitanteDomilicioActual;
	private String ciudadLocalidadSolicitanteDomilicioActual;
	private String departamentoSolicitanteDomilicioActual;

	private boolean tieneTerrenoPropioSolicitanteDestinoActual;
	private boolean tieneViviendaPropiaSolicitanteDestinoActual;
	private TipoViviendaEnum habitaEnViviendaSolicitanteDestinoActual;

	private boolean tienePrestamosTerrenoPropioSolicitanteDestinoActual;
	private boolean tienePrestamosViviendaPropiaSolicitanteDestinoActual;
	private boolean tienePrestamosAnticreticoSolicitanteDestinoActual;
	private String numeroCreditosAnteriores;

	private String lugarInmuebleCalleAvenidaDestinoActual;
	private String lugarInmuebleNumeroDomicilioDestinoActual;
	private String lugarInmuebleZonaBarrioDestinoActual;
	private String lugarInmuebleCiudadLocalidadDestinoActual;
	private String lugarInmuebleDeparmentoDestinoActual;

	private String lugarSuscripcionFormularioDestinoActual;
	private String fechaSuscripcionFormularioDestinoActual;

	private String observaciones;

	private String apellidoPaternoGarante1;
	private String apellidoMaternoGarante1;
	private String nombreGarante1;
	private String gradoGarante1;

	private String cedulaIdentidadGarante1;
	private String categoriaPorcentajeGarante1;
	private String unidadGarante1;
	private String sueldoGarante1;
	private String antiguedadGarante1;
	private String postGradoEnBsGarante1;
	private String bonoBSCBsGarante1;

	private String aniosServicioGarante1;
	private String telefonoCelularGarante1;
	private String destinoActualGarante1;
	private String departamentoGarante1;

	private String apellidoPaternoGarante2;
	private String apellidoMaternoGarante2;
	private String nombreGarante2;
	private String gradoGarante2;

	private String cedulaIdentidadGarante2;
	private String categoriaPorcentajeGarante2;
	private String unidadGarante2;
	private String sueldoGarante2;
	private String antiguedadGarante2;
	private String postGradoEnBsGarante2;
	private String bonoBSCBsGarante2;

	private String aniosServicioGarante2;
	private String telefonoCelularGarante2;
	private String destinoActualGarante2;
	private String departamentoGarante2;

	private String lugarGarante1;
	private String fechaGarante1;

	private String lugarGarante2;
	private String fechaGarante2;
	
	public SolicitudCreditoViviendaDTOPDF() {
		
	}

	public static SolicitudCreditoViviendaDTOPDF getFake() {
		
		SolicitudCreditoViviendaDTOPDF solicitudDTO = new SolicitudCreditoViviendaDTOPDF();
		
		// INICIO
		solicitudDTO.numeroCarpeta ="CP00000007";
		solicitudDTO.numeroRegistro="1364";
		
		// TIPO CREDITO
		solicitudDTO.tipoCreditoODestinoPrestamoSolicitado = TipoCreditoEnum.COMPRA_TERRENO_VIVIENDA_DEPARTAMENTO;
		
		// TIPO GARANTIA
		solicitudDTO.tipoGarantia = TipoGarantiaEnum.REAL;
		
		// MONTO PRESTAMO SOLICITADO
		solicitudDTO.montoPrestamoSolicitadoBs = 10000.00f+"";
		solicitudDTO.montoPrestamoSolicitadoUFV= 49263.51f+"";
		solicitudDTO.tipoCambioOficialUFV      = 2.0299f+"";

		// DATOS SOLICITANTE

		
		solicitudDTO.apellidoPaternoSolicitante = "TORREZ";
		solicitudDTO.apellidoMaternoSolicitante = "ALVAREZ";
		solicitudDTO.nombreSolicitante = "MARCO ANDRES";
		solicitudDTO.gradoSolicitante = "SUBTENIENTE";

		solicitudDTO.cedulaIdentidadSolicitante = "4235678";
		solicitudDTO.categoriaPorcentajeSolicitante = "045";
		solicitudDTO.unidadSolicitante = "39A";
		solicitudDTO.liquidoPagableBsSueldoSolicitante = 3000.00f+"";
		solicitudDTO.antiguedadBsSolicitante = 139.54f+"";
		solicitudDTO.postGradoBsSolicitante = 225.17f+"";
		solicitudDTO.bonoBsSegSolicitante = 71.84f+"";
		solicitudDTO.aniosServicioSolicitante = 16+"";

		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, Calendar.MAY,28);

		solicitudDTO.fechaNacimientoSolicitanteDia  = new Fecha(calendar.getTime()).getDiaLiteral()  + "";
		solicitudDTO.fechaNacimientoSolicitanteMes  = new Fecha(calendar.getTime()).getMesLiteral()  + "";
		solicitudDTO.fechaNacimientoSolicitanteAnio = new Fecha(calendar.getTime()).getAnioLiteral() + "";

		solicitudDTO.ciudadLocalidadSolicitante = "COCHABAMBA";
		solicitudDTO.provinciaSolicitante = "CERCADO";
		solicitudDTO.departamentoSolicitante = "COCHABAMBA";
		 
		solicitudDTO.edadSolicitante = 37+"";
		solicitudDTO.estadoCivilSolicitante = "CASADO";
		solicitudDTO.dependientesSolicitante = 3+"";
		solicitudDTO.totalGrupoFamiliarSolicitante = 4+"";

		solicitudDTO.calleAvenidaSolicitante = "AV. VILLANUEVA";
		solicitudDTO.numeroCasaSolicitante = 123+"";
		solicitudDTO.zonaBarrioSolicitante = "PAMPALINA";

		solicitudDTO.numeroTelefonoDomicilioSolicitanteDomilicioActual = "4580956";
		solicitudDTO.numeroCelularSolicitanteDomilicioActual = "67844386";
		solicitudDTO.numeroTelefonoLaboralSolicitanteDomilicioActual = "1806754";
		solicitudDTO.numeroTelefonoReferenciaSolicitanteDomilicioActual = "4098945";

		solicitudDTO.unidadPolicialSolicitanteDomilicioActual = "PRIMERA 34";
		solicitudDTO.ciudadLocalidadSolicitanteDomilicioActual= "LA PAZ";
		solicitudDTO.departamentoSolicitanteDomilicioActual   = "LA PAZ";

		solicitudDTO.tieneTerrenoPropioSolicitanteDestinoActual = false;
		solicitudDTO.tieneViviendaPropiaSolicitanteDestinoActual= false; 
		solicitudDTO.habitaEnViviendaSolicitanteDestinoActual   = TipoViviendaEnum.ALQUILADA;

		solicitudDTO.tienePrestamosTerrenoPropioSolicitanteDestinoActual = false;
		solicitudDTO.tienePrestamosViviendaPropiaSolicitanteDestinoActual= false;
		solicitudDTO.tienePrestamosAnticreticoSolicitanteDestinoActual   = false;
		solicitudDTO.numeroCreditosAnteriores = 0 + "";

		solicitudDTO.lugarInmuebleCalleAvenidaDestinoActual   ="CALLE JUAN OSORIO";
		solicitudDTO.lugarInmuebleNumeroDomicilioDestinoActual=17+"";
		solicitudDTO.lugarInmuebleZonaBarrioDestinoActual     ="ALTO HEROES";
		solicitudDTO.lugarInmuebleCiudadLocalidadDestinoActual="LA PAZ";
		solicitudDTO.lugarInmuebleDeparmentoDestinoActual     ="LA PAZ";

		solicitudDTO.lugarSuscripcionFormularioDestinoActual="LA PAZ";
		solicitudDTO.fechaSuscripcionFormularioDestinoActual= "";

		solicitudDTO.observaciones = "Tiene pendiente la veficacion de su carnet de identidad, Verificar con el comando";

		solicitudDTO.apellidoPaternoGarante1 = "CARTAGENA";
		solicitudDTO.apellidoMaternoGarante1 = "SEJAS";
		solicitudDTO.nombreGarante1 = "MONICA LIA";
		solicitudDTO.gradoGarante1  = "POLICIA";

		solicitudDTO.cedulaIdentidadGarante1 = "44876532";
		solicitudDTO.categoriaPorcentajeGarante1 = "055";
		solicitudDTO.unidadGarante1 = "37";
		solicitudDTO.sueldoGarante1 = 2770.45f+"";
		solicitudDTO.antiguedadGarante1 = 121.89f+"";
		solicitudDTO.postGradoEnBsGarante1 = 83.78f+"";
		solicitudDTO.bonoBSCBsGarante1 = 109.55f+"";

		solicitudDTO.aniosServicioGarante1  = 10+"";
		solicitudDTO.telefonoCelularGarante1= "65434567";
		solicitudDTO.destinoActualGarante1  = "BOMBEROS 37";
		solicitudDTO.departamentoGarante1   = "TARIJA";

		solicitudDTO.apellidoPaternoGarante2 ="LARA";
		solicitudDTO.apellidoMaternoGarante2 ="CHUQUIMIA";
		solicitudDTO.nombreGarante2          ="SILVANO JORGE";
		solicitudDTO.gradoGarante2           ="";

		solicitudDTO.cedulaIdentidadGarante2 = "9876567";
		solicitudDTO.categoriaPorcentajeGarante2 = "060";
		solicitudDTO.unidadGarante2 = "16";
		solicitudDTO.sueldoGarante2 = 4500.67f+"";
		solicitudDTO.antiguedadGarante2 = 177.90f+"";
		solicitudDTO.postGradoEnBsGarante2 = 98.05f+"";
		solicitudDTO.bonoBSCBsGarante2 = 112.67f+"";

		solicitudDTO.aniosServicioGarante2  = 8+"";
		solicitudDTO.telefonoCelularGarante2= "7899876";
		solicitudDTO.destinoActualGarante2  = "COMANDO LA PAZ";
		solicitudDTO.departamentoGarante2   = "LA PAZ"; 

		solicitudDTO.lugarGarante1 = "LA PAZ";
		solicitudDTO.fechaGarante1 = "";

		solicitudDTO.lugarGarante2 = "LA PAZ";
		solicitudDTO.fechaGarante2 = "";
		
		return solicitudDTO;
	}

}
