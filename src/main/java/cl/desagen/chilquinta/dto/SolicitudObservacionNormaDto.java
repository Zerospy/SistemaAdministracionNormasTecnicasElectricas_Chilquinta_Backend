package cl.desagen.chilquinta.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SolicitudObservacionNormaDto {

    private Integer id;

    private Integer normaID;

    private String comments;

    private List<Integer> usuarioRecibeID;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
