package subsistema.pdf.dto;

import lombok.Getter;
import subsistema.pdf.utils.Fecha;

import java.util.Calendar;

@Getter
public class EstadoAfiliacionDTOPDF {
    private String causa;
    private String observaciones;
    private String fechaSolicitud;

    public EstadoAfiliacionDTOPDF(){ }

    public static EstadoAfiliacionDTOPDF getFake(){
        EstadoAfiliacionDTOPDF estado = new EstadoAfiliacionDTOPDF();
        estado.causa = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n";
        estado.observaciones = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.MAY,28);
        estado.fechaSolicitud = new Fecha(calendar.getTime()).getFormatoFecha();

        return estado;
    }
}
