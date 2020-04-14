package cl.desagen.chilquinta.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDownload {


    private String codNorma;


    private String nombre;


    private String descripcion;


    private String estado;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date fecha;


    private String tipoNorma;


	@Override
	public String toString() {
		return "ResponseDownload [codNorma=" + codNorma + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", estado=" + estado + ", fecha=" + fecha + ", tipoNorma=" + tipoNorma + "]";
	}


    

}
