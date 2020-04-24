package dto;

import lombok.Getter;
import lombok.Setter;
import utils.enums.TipoCreditoEnum;
import utils.enums.TipoGarantiaEnum;
import utils.enums.TipoViviendaEnum;

import java.util.Date;

@Getter
@Setter
public class SolicitudCreditoViviendaDTO {

	// INICIO
	private String numeroCarpeta;
	private String numeroRegistro;
	
	// TIPO CREDITO
	private TipoCreditoEnum tipoCreditoODestinoPrestamoSolicitado;
	
	// TIPO GARANTIA
	private TipoGarantiaEnum tipoGarantia;
	
	// MONTO PRESTAMO SOLICITADO

	private float montoPrestamoSolicitadoBs;
	private float montoPrestamoSolicitadoUFV;
	private float tipoCambioOficialUFV;

	// DATOS SOLICITANTE

	private String apellidoPaternoSolicitante;
	private String apellidoMaternoSolicitante;
	private String nombreSolicitante;
	private String gradoSolicitante;

	private String cedulaIdentidadSolicitante;
	private String categoriaPorcentajeSolicitante;
	private String unidadSolicitante;
	private float liquidoPagableBsSueldoSolicitante;
	private float antiguedadBsSolicitante;
	private float postGradoBsSolicitante;
	private float bonoBsSegSolicitante;
	private int aniosServicioSolicitante;

	private Date fechaNacimientoSolicitante;
	private String ciudadLocalidadSolicitante;
	private String provinciaSolicitante;
	private String departamentoSolicitante;
	 
	private int edadSolicitante;
	private String estadoCivilSolicitante;
	private int dependientesSolicitante;
	private int totalGrupoFamiliarSolicitante;

	private String calleAvenidaSolicitante;
	private int numeroCasaSolicitante;
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
	private int numeroCreditosAnteriores;

	private String lugarInmuebleCalleAvenidaDestinoActual;
	private int lugarInmuebleNumeroDomicilioDestinoActual;
	private String lugarInmuebleZonaBarrioDestinoActual;
	private String lugarInmuebleCiudadLocalidadDestinoActual;
	private String lugarInmuebleDeparmentoDestinoActual;

	private String lugarSuscripcionFormularioDestinoActual;
	private Date fechaSuscripcionFormularioDestinoActual;

	private String observaciones;


	private String apellidoPaternoGarante1;
	private String apellidoMaternoGarante1;
	private String nombreGarante1;
	private String gradoGarante1;

	private String cedulaIdentidadGarante1;
	private String categoriaPorcentajeGarante1;
	private String unidadGarante1;
	private float sueldoGarante1;
	private float antiguedadGarante1;
	private float postGradoEnBsGarante1;
	private float bonoBSCBsGarante1;

	private int aniosServicioGarante1;
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
	private float sueldoGarante2;
	private float antiguedadGarante2;
	private float postGradoEnBsGarante2;
	private float bonoBSCBsGarante2;

	private int aniosServicioGarante2;
	private String telefonoCelularGarante2;
	private String destinoActualGarante2;
	private String departamentoGarante2;

	private String lugarGarante1;
	private Date fechaGarante1;

	private String lugarGarante2;
	private Date fechaGarante2;
	
	public SolicitudCreditoViviendaDTO() {
		
	}
	
	@SuppressWarnings("deprecation")
	public static SolicitudCreditoViviendaDTO getFake() {
		
		SolicitudCreditoViviendaDTO solicitudDTO = new SolicitudCreditoViviendaDTO();
		
		// INICIO
		solicitudDTO.numeroCarpeta ="CP00000007";
		solicitudDTO.numeroRegistro="1364";
		
		// TIPO CREDITO
		solicitudDTO.tipoCreditoODestinoPrestamoSolicitado = TipoCreditoEnum.COMPRA_TERRENO_VIVIENDA_DEPARTAMENTO;
		
		// TIPO GARANTIA
		solicitudDTO.tipoGarantia = TipoGarantiaEnum.REAL;
		
		// MONTO PRESTAMO SOLICITADO
		solicitudDTO.montoPrestamoSolicitadoBs = 10000.00f;
		solicitudDTO.montoPrestamoSolicitadoUFV= 49263.51f;
		solicitudDTO.tipoCambioOficialUFV      = 2.0299f;

		// DATOS SOLICITANTE

		
		solicitudDTO.apellidoPaternoSolicitante = "TORREZ";
		solicitudDTO.apellidoMaternoSolicitante = "ALVAREZ";
		solicitudDTO.nombreSolicitante = "MARCO ANDRES";
		solicitudDTO.gradoSolicitante = "SUBTENIENTE";

		solicitudDTO.cedulaIdentidadSolicitante = "4235678";
		solicitudDTO.categoriaPorcentajeSolicitante = "045";
		solicitudDTO.unidadSolicitante = "39A";
		solicitudDTO.liquidoPagableBsSueldoSolicitante = 3000.00f;
		solicitudDTO.antiguedadBsSolicitante = 139.54f;
		solicitudDTO.postGradoBsSolicitante = 225.17f;
		solicitudDTO.bonoBsSegSolicitante = 71.84f;
		solicitudDTO.aniosServicioSolicitante = 16;

		solicitudDTO.fechaNacimientoSolicitante = new Date(1982, 9, 1982);
		solicitudDTO.ciudadLocalidadSolicitante = "COCHABAMBA";
		solicitudDTO.provinciaSolicitante = "CERCADO";
		solicitudDTO.departamentoSolicitante = "COCHABAMBA";
		 
		solicitudDTO.edadSolicitante = 37;
		solicitudDTO.estadoCivilSolicitante = "CASADO";
		solicitudDTO.dependientesSolicitante = 3;
		solicitudDTO.totalGrupoFamiliarSolicitante = 4;

		solicitudDTO.calleAvenidaSolicitante = "AV. VILLANUEVA";
		solicitudDTO.numeroCasaSolicitante = 123;
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
		solicitudDTO.numeroCreditosAnteriores = 0;

		solicitudDTO.lugarInmuebleCalleAvenidaDestinoActual   ="CALLE JUAN OSORIO";
		solicitudDTO.lugarInmuebleNumeroDomicilioDestinoActual=17;
		solicitudDTO.lugarInmuebleZonaBarrioDestinoActual     ="ALTO HEROES";
		solicitudDTO.lugarInmuebleCiudadLocalidadDestinoActual="LA PAZ";
		solicitudDTO.lugarInmuebleDeparmentoDestinoActual     ="LA PAZ";

		solicitudDTO.lugarSuscripcionFormularioDestinoActual="LA PAZ";
		solicitudDTO.fechaSuscripcionFormularioDestinoActual= new Date();

		solicitudDTO.observaciones = "Tiene pendiente la veficacion de su carnet de identidad, Verificar con el comando";

		solicitudDTO.apellidoPaternoGarante1 = "CARTAGENA";
		solicitudDTO.apellidoMaternoGarante1 = "SEJAS";
		solicitudDTO.nombreGarante1 = "MONICA LIA";
		solicitudDTO.gradoGarante1  = "POLICIA";

		solicitudDTO.cedulaIdentidadGarante1 = "44876532";
		solicitudDTO.categoriaPorcentajeGarante1 = "055";
		solicitudDTO.unidadGarante1 = "37";
		solicitudDTO.sueldoGarante1 = 2770.45f;
		solicitudDTO.antiguedadGarante1 = 121.89f;
		solicitudDTO.postGradoEnBsGarante1 = 83.78f;
		solicitudDTO.bonoBSCBsGarante1 = 109.55f;

		solicitudDTO.aniosServicioGarante1  = 10;
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
		solicitudDTO.sueldoGarante2 = 4500.67f;
		solicitudDTO.antiguedadGarante2 = 177.90f;
		solicitudDTO.postGradoEnBsGarante2 = 98.05f;
		solicitudDTO.bonoBSCBsGarante2 = 112.67f;

		solicitudDTO.aniosServicioGarante2  = 8;
		solicitudDTO.telefonoCelularGarante2= "7899876";
		solicitudDTO.destinoActualGarante2  = "COMANDO LA PAZ";
		solicitudDTO.departamentoGarante2   = "LA PAZ"; 

		solicitudDTO.lugarGarante1 = "LA PAZ";
		solicitudDTO.fechaGarante1 = new Date();

		solicitudDTO.lugarGarante2 = "LA PAZ";
		solicitudDTO.fechaGarante2 = new Date();
		
		return solicitudDTO;
	}

}
