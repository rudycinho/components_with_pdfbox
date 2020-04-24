package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjustesSistemaDTOPDF {

    private String cambioUSD;
    private String cambioUFV;
    private String amortizacionDias;

    private String seguroDesgravamen;
    private String diasParaArchivar;
    private String CIP;

    private String interesAnual;

    private String correoNotificaciones;
    private String diasMaximoParaDescuentos;

    public AjustesSistemaDTOPDF(){ }

    public static AjustesSistemaDTOPDF getFake(){
        AjustesSistemaDTOPDF ajustesSistemaDTO = new AjustesSistemaDTOPDF();
        ajustesSistemaDTO.cambioUSD         = ""+6.89;
        ajustesSistemaDTO.cambioUFV         = ""+2.33958;
        ajustesSistemaDTO.amortizacionDias  = ""+30;

        ajustesSistemaDTO.seguroDesgravamen = ""+0.78;
        ajustesSistemaDTO.diasParaArchivar  = ""+35;
        ajustesSistemaDTO.CIP               = ""+1.0;

        ajustesSistemaDTO.interesAnual      = ""+4.5;

        ajustesSistemaDTO.correoNotificaciones     = "prueba@mail.com";
        ajustesSistemaDTO.diasMaximoParaDescuentos = ""+15;

        return ajustesSistemaDTO;
    }
}
