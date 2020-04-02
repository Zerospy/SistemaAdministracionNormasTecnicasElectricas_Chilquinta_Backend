package cl.desagen.chilquinta.entities;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSaveRequest {
	
    private Integer id;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String clave;
    private Boolean estado;
    private Timestamp timestamp;
    private String email;
    private String claveTextoPlano;
    private String avatarBase64;
    private Boolean administrador;
	private Integer perfil;
	private Long idPerfil;

}
