package subsistema.pdf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class PerfilDTO {
    private Long Id;

    private String nombre;
    private String rol;
    private String descripcion;
    private String estado;
    private List<PermisoDTO> permisos;

    public PerfilDTO(){ }

    public static PerfilDTO getFake(){
        PerfilDTO perfilDTO = new PerfilDTO();

        perfilDTO.Id          = 342L;

        perfilDTO.nombre      = "PERFIL ALFIL";
        perfilDTO.descripcion = "Quisque in tincidunt felis, quis faucibus tortor.\n"+
                "Vestibulum malesuada arcu sit amet neque pulvinar, vehicula facilisis nisl semper.\n"+
                "Nullam turpis lorem, ultrices sit amet malesuada a, condimentum quis eros.\n"+
                "Nam vitae blandit justo. Donec lacus leo, congue nec ultrices in, luctus in felis.\n"+
                "Ut congue erat vel ullamcorper tempor.\n"+
                "Fusce malesuada interdum ligula, vitae eleifend leo tempor non.\n";
        perfilDTO.rol         = "SISTEMAS";
        perfilDTO.estado      = "HABILITADO";
        perfilDTO.permisos    = new LinkedList<>();
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());
        perfilDTO.permisos.add(PermisoDTO.getFake());

        return perfilDTO;
    }

}
