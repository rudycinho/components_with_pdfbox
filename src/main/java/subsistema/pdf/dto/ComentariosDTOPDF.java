package subsistema.pdf.dto;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ComentariosDTOPDF {
    private String prestamoCodigo;
    private String prestamoModalidad;
    private String prestamoTipoGarantia;
    private String fechaInicioPrestamo;
    private String gradoAfiliado;
    private String nombreAfiliado;
    private String carnetAfiliado;

    private List<ComentarioDTOPDF> comentarios;

    public ComentariosDTOPDF(){ }

    public static ComentariosDTOPDF getFake(){
        ComentariosDTOPDF comentarios = new ComentariosDTOPDF();

        comentarios.prestamoCodigo       = "CP00000001";
        comentarios.prestamoModalidad    = "COMPRA TERRENO VIVIENDA O DEPARTAMENTO";
        comentarios.prestamoTipoGarantia = "REAL (GRAVAMEN HIPOTECARIO)";
        comentarios.fechaInicioPrestamo  = "25-04-2020";
        comentarios.gradoAfiliado        = "CORONEL";
        comentarios.nombreAfiliado       = "MARIO CASTEÑEDA";
        comentarios.carnetAfiliado       = "43463636";


        comentarios.comentarios = new LinkedList<>();
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());
        comentarios.comentarios.add(ComentarioDTOPDF.getFake());

        return comentarios;
    }
}
