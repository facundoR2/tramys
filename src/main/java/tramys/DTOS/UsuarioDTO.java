package tramys.DTOS;


import tramys.Models.Usuario;

public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;





    public UsuarioDTO(Usuario usuario){
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.correo = usuario.getCorreo();

    }
}
