package subsistema.pdf.dto;

import lombok.Getter;

@Getter
public class ComentarioDTOPDF {

    private String nombreUsuario;
    private String nombreCargo;
    private String nombreArea;

    private String fechaComentario;
    private String horaComentario;

    private String contenidoComentario;

    public ComentarioDTOPDF(){ }

    public static ComentarioDTOPDF getFake(){
        ComentarioDTOPDF comentario = new ComentarioDTOPDF();

        comentario.nombreUsuario = "Linus Tolbar";
        comentario.nombreCargo   = "SISTEMAS";
        comentario.nombreArea    = "ADMINISTRADOR";

        comentario.fechaComentario = "30-04-2019";
        comentario.horaComentario  = "04:43:50";

        comentario.contenidoComentario = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";
        return comentario;
    }
}
