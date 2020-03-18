package cl.desagen.chilquinta.dto;

import cl.desagen.chilquinta.entities.EstadosEntity;
import cl.desagen.chilquinta.entities.NormaEntity;
import cl.desagen.chilquinta.entities.SolicitudObservacionNormaEntity;
import cl.desagen.chilquinta.enums.TipoNorma;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class NormaDto {

    private Integer id;

    private String codNorma;

    private String nombre;

    private String descripcion;

    private EstadosEntity estado;

    private List<SolicitudObservacionNormaEntity> usersToComment;

    private Timestamp fecha;

    private Integer tipoTabla;

    private String urlPdf;

    private String urlCad;

    private Integer downloadCounter;

    private TipoNorma tipoNorma;

    private String nombreIngles;

    private String pdfFileName;

    private String cadFileName;

    public NormaEntity toEntity() {

        NormaEntity normaEntity = new NormaEntity();

        normaEntity.setId(this.id);
        normaEntity.setCodNorma(this.codNorma);
        normaEntity.setNombre(this.nombre);
        normaEntity.setDescripcion(this.descripcion);
        normaEntity.setEstado(this.estado);
        normaEntity.setUsersToComment(this.usersToComment);
        normaEntity.setFecha(this.fecha);
        normaEntity.setTipoTabla(this.tipoTabla);
        normaEntity.setUrlPdf(this.urlPdf);
        normaEntity.setUrlCad(this.urlCad);
        normaEntity.setDownloadCounter(this.downloadCounter);
        normaEntity.setTipoNorma(this.tipoNorma);
        normaEntity.setNombreIngles(this.nombreIngles);

        return normaEntity;

    }
}
