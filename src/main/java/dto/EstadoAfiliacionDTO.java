package dto;

import lombok.Getter;

@Getter
public class EstadoAfiliacionDTO {
    private String causa;
    private String observaciones;

    public EstadoAfiliacionDTO(){ }

    public static EstadoAfiliacionDTO getFake(){
        EstadoAfiliacionDTO estado = new EstadoAfiliacionDTO();
        estado.causa = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n";
        estado.observaciones = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";
        return estado;
    }
}
