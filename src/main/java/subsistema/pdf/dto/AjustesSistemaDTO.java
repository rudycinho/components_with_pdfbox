package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjustesSistemaDTO {

    private Double cambioUSD;
    private Double cambioUFV;
    private Integer amortizacionDias;

    private Double seguroDesgravamen;
    private Integer diasParaArchivar;
    private Double CIP;

    private Double interesAnual;

    private String correoNotificaciones;
    private Integer diasMaximoParaDescuentos;

    public AjustesSistemaDTO(){ }

    public static AjustesSistemaDTO getFake(){
        AjustesSistemaDTO ajustesSistemaDTO = new AjustesSistemaDTO();
        ajustesSistemaDTO.cambioUSD            = 6.89;
        ajustesSistemaDTO.cambioUFV            = 2.33958;
        ajustesSistemaDTO.amortizacionDias     = 30;

        ajustesSistemaDTO.seguroDesgravamen = 0.78;
        ajustesSistemaDTO.diasParaArchivar  = 35;
        ajustesSistemaDTO.CIP = 1.0;

        ajustesSistemaDTO.interesAnual = 4.5;

        ajustesSistemaDTO.correoNotificaciones     = "prueba@mail.com";
        ajustesSistemaDTO.diasMaximoParaDescuentos = 15;

        return ajustesSistemaDTO;
    }
}
