package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisoDTO {
    private Long Id;

    private String nombre;
    private String descripcion;
    private String estado;

    public PermisoDTO(){ }

    public static PermisoDTO getFake(){
        PermisoDTO permisoDTO = new PermisoDTO();

        permisoDTO.Id          = 456L;
        permisoDTO.nombre      = "HABLAR EN LATIN";
        permisoDTO.descripcion = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";
        permisoDTO.estado      = "HABILITADO";

        return permisoDTO;
    }
}
