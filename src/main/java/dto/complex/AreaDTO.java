package dto.complex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AreaDTO {
    private String nombreArea;
    private List<String> secciones;

    public AreaDTO(){ }

    public static AreaDTO getFake(){
        AreaDTO area = new AreaDTO();
        area.nombreArea = "Area de Prestamos";
        area.secciones  = Arrays.asList(new String[]{
                "Formulario de Solicitud B-1",
                "Carnet de identidad solicitante",
                "Boletas de pago solicitante",
                "Carnet de identidad garantes",
                "Boleta de pago garantes",
                "Documentos de respaldo(si corresponde)",
                "Calificacion y Aceptacion del Credito",

        });
        return area;
    }

    public static List<AreaDTO> getFakes(){
        List<AreaDTO> areas = new LinkedList<>();
        AreaDTO area;

        area = new AreaDTO();
        area.nombreArea = "Area de Prestamos";
        area.secciones  = Arrays.asList(new String[]{
                "Formulario de Solicitud B-1",
                "Carnet de identidad solicitante",
                "Boletas de pago solicitante",
                "Carnet de identidad garantes",
                "Boleta de pago garantes",
                "Documentos de respaldo(si corresponde)",
                "Calificacion y Aceptacion del Credito",

        });
        areas.add(area);


        area = new AreaDTO();
        area.nombreArea = "Area tecnica";
        area.secciones  = Arrays.asList(new String[]{
                "Plano de lote",
                "Plano de construccion",
                "Fotografias",
                "Otros documentos",
                "Formulario B-4",
                "Formulario B-5",
                "Formulario B-6",
                "Catastro",
        });
        areas.add(area);


        area = new AreaDTO();
        area.nombreArea = "Area Social";
        area.secciones  = Arrays.asList(new String[]{
                "Formulario B-3",
        });
        areas.add(area);


        area = new AreaDTO();
        area.nombreArea = "Area Legal";
        area.secciones  = Arrays.asList(new String[]{
                "Certificado de no propiedad NAL. de DD.RR.",
                "Carnet de identidad propietarios",
                "Informacion rapida de DD.RR.",
                "Folio legal",
                "Testimonio de propiedad y/o test aclaratorios",
                "Ultimo pago de impuesto",
                "Otros documentos",
                "Contrato de prestamo",
                "Informe legal",
                "Resolucion legal",
        });
        areas.add(area);


        area = new AreaDTO();
        area.nombreArea = "Area Administracion Financiera";
        area.secciones  = Arrays.asList(new String[]{
                "Formulario de registro - SIGEP (C-31)"
        });
        areas.add(area);


        area = new AreaDTO();
        area.nombreArea = "Area Ejecutiva";
        area.secciones  = Arrays.asList(new String[]{
                "Aprobacion de desembolso"
        });
        areas.add(area);

        return areas;
    }
}
