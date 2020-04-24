package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;
import subsistema.pdf.utils.Fecha;

import java.util.Calendar;

@Getter
@Setter
public class SucursalDTOPDF {
    private Long Id;

    private String fechaRegistro;

    private String nombre;
    private String departamento;
    private String zona;
    private String direccion;
    private String telefono;
    private String fax;
    private String estado;

    private String horarioAtencion;

    private String observaciones;

    public SucursalDTOPDF(){ }

    public static SucursalDTOPDF getFake(){
        SucursalDTOPDF sucursalDTO = new SucursalDTOPDF();

        sucursalDTO.Id           = 450L;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.MAY,28);
        sucursalDTO.fechaRegistro= new Fecha(calendar.getTime()).getFormatoFecha();

        sucursalDTO.nombre       = "GRAN CENTRAL";
        sucursalDTO.departamento = "COCHABAMBA";
        sucursalDTO.zona         = "SUDESTES";
        sucursalDTO.direccion    = "CORONILLA 565";
        sucursalDTO.telefono     = "44565656";
        sucursalDTO.fax          = "5666744675";
        sucursalDTO.estado       = "HABILITADO";

        sucursalDTO.horarioAtencion = "8:00 - 12:00 , 14:00 - 20:00";

        sucursalDTO.observaciones =
                "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

        return sucursalDTO;
    }
}
