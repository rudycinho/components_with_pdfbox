package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosCarpetaAfiliadioDTOPDF {
    private String grado;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String carnetIdentidad;
    private String procedenciaCarnet;
    private String fechaNacimiento;
    private String estadoPolicial;
    private String fechaAfiliacion;

    private String telefono;
    private String telefonoMovil;
    private String correoElectronico;
    private String unidadPolicial;
    private String departamento;
    private String tipoAfiliacion;
    private String estadoAfiliacion;
    private String observacion;
    
    private String prestamoCodigo;
    private String prestamoModalidad;
    private String prestamoTipoGarantia;
    private String fechaInicio;

    public DatosCarpetaAfiliadioDTOPDF() {}

    public static DatosCarpetaAfiliadioDTOPDF getFake(){
        DatosCarpetaAfiliadioDTOPDF datos = new DatosCarpetaAfiliadioDTOPDF();

        datos.prestamoCodigo       = "CP00000001";
        datos.prestamoModalidad    = "COMPRA TERRENO VIVIENDA O DEPARTAMENTO";
        datos.prestamoTipoGarantia = "REAL (GRAVAMEN HIPOTECARIO)";
        datos.fechaInicio          = "25-04-2020";

        datos.grado            ="GOKU";
        datos.nombres          ="MARIO";
        datos.apellidoPaterno  ="CASTAÑEDA";
        datos.apellidoMaterno  ="";
        datos.carnetIdentidad  ="4524842";
        datos.procedenciaCarnet="CB";
        datos.fechaNacimiento  ="";
        datos.estadoPolicial   ="ACTIVO";
        datos.fechaAfiliacion  ="";

        datos.telefono         ="77546767";
        datos.telefonoMovil    ="23467767";
        datos.correoElectronico="mario@casteñeda";
        datos.unidadPolicial   ="CENTER";
        datos.departamento     ="CENTER";
        datos.tipoAfiliacion   ="AUTOMATICA";
        datos.estadoAfiliacion ="ACTIVO";
        datos.observacion      = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

        return datos;
    }
}
