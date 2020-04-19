package dto.complex;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DefinicionCreditoDTO {
    private String gradoNombreAfiliado;
    private String carnetIdentidad;
    private String numeroCarpeta;
    private Date fechaCreacion;
    private String modalidadCredito;
    private String tipoDeGarantia;
    private String origenTramite;

    // "Area de Prestamos"
    private String formularioSolicitud_B1;
    private String carnetIdentidadSolicitante;
    private String boletasPagoSolicitante;
    private String carnetIdentidadGarantes;
    private String boletaPagoGarantes;
    private String documentosRespaldo;
    private String calificacionAceptacionCredito;

    // "Area tecnica";
    private String planoLote;
    private String planoConstruccion;
    private String fotografias;
    private String otrosDocumentos;
    private String formulario_B4;
    private String formulario_B5;
    private String formulario_B6;
    private String catastro;

    // "Area Social";
    private String formulario_B3;

    // "Area Legal";

    private String certificadoNoPropiedad;
    private String carnetIdentidadPropietarios;
    private String informacionRapida;
    private String folioLegal;
    private String testimonioPropiedadTestAclaratorios;
    private String ultimoPagoImpuesto;
    private String otrosDocumentos2;
    private String contratoPrestamo;
    private String informeLegal;
    private String resolucionLegal;

    // "Area Administracion Financiera";

    private String formularioRegistro_SIGEP_C3;

    // "Area Ejecutiva";
    private String aprobacionDesembolso;

    public static DefinicionCreditoDTO getFake(){
        DefinicionCreditoDTO definicionCredito = new DefinicionCreditoDTO();

        definicionCredito.gradoNombreAfiliado ="POLICIA ROBERTO JUAN ZENTENO APAZA";
        definicionCredito.carnetIdentidad     ="34567789";
        definicionCredito.numeroCarpeta       ="CP00000011";
        definicionCredito.fechaCreacion       =new Date(3,03,2020);
        definicionCredito.modalidadCredito    ="ANTICRETICO";
        definicionCredito.tipoDeGarantia      ="Personal (Garantia de haberes y presentacion de dos garantes)";
        definicionCredito.origenTramite       ="Local";

        definicionCredito.formularioSolicitud_B1="?";
        definicionCredito.carnetIdentidadSolicitante="?";
        definicionCredito.boletasPagoSolicitante="?";
        definicionCredito.carnetIdentidadGarantes="?";
        definicionCredito.boletaPagoGarantes="?";
        definicionCredito.documentosRespaldo="?";
        definicionCredito.calificacionAceptacionCredito="?";

        definicionCredito.planoLote="?";
        definicionCredito.planoConstruccion="?";
        definicionCredito.fotografias="?";
        definicionCredito.otrosDocumentos="?";
        definicionCredito.formulario_B4="?";
        definicionCredito.formulario_B5="?";
        definicionCredito.formulario_B6="?";
        definicionCredito.catastro="?";

        definicionCredito.formulario_B3="?";

        definicionCredito.certificadoNoPropiedad="?";
        definicionCredito.carnetIdentidadPropietarios="?";
        definicionCredito.informacionRapida="?";
        definicionCredito.folioLegal="?";
        definicionCredito.testimonioPropiedadTestAclaratorios="?";
        definicionCredito.ultimoPagoImpuesto="?";
        definicionCredito.otrosDocumentos2="?";
        definicionCredito.contratoPrestamo="?";
        definicionCredito.informeLegal="?";
        definicionCredito.resolucionLegal="?";

        definicionCredito.formularioRegistro_SIGEP_C3="?";

        definicionCredito.aprobacionDesembolso="?";

        return definicionCredito;
    }
}
