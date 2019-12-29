package cl.desagen.chilquinta.entities;

import cl.desagen.chilquinta.dto.NormaDto;
import cl.desagen.chilquinta.enums.TipoNorma;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "norma", schema = "dbo", catalog = "NORMAS")
@Data
public class NormaEntity {

    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @JsonProperty
    @Column(name = "cod_norma")
    private String codNorma;

    @JsonProperty
    @Column(name = "nombre")
    private String nombre;

    @JsonProperty
    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty
    @JoinColumn(name = "estado_id")
    private EstadosEntity estado;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "norma_id")
    private List<SolicitudObservacionNormaEntity> usersToComment;

    @JsonProperty
    @Column(name = "fecha")
    private Timestamp fecha;

    @JsonProperty
    @Column(name = "tipo_tabla")
    private Integer tipoTabla;

    @JsonProperty
    @Column(name = "Url_pdf")
    private String urlPdf;

    @JsonProperty
    @Column(name = "Url_cad")
    private String urlCad;

    @JsonProperty
    @Column(name = "download_counter")
    private Integer downloadCounter;

    @JsonProperty
    @Column(name = "tipo_norma")
    @Enumerated(EnumType.STRING)
    private TipoNorma tipoNorma;

    @JsonProperty
    @Column(name = "nombre_ingles")
    private String nombreIngles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormaEntity that = (NormaEntity) o;
        return id == that.id &&
                Objects.equals(codNorma, that.codNorma) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(estado, that.estado) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(tipoTabla, that.tipoTabla) &&
                Objects.equals(urlPdf, that.urlPdf) &&
                Objects.equals(urlCad, that.urlCad) &&
                Objects.equals(downloadCounter, that.downloadCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codNorma, nombre, descripcion, estado, fecha, tipoTabla, urlPdf, urlCad);
    }

    public NormaDto toDto() {
        NormaDto normaDto = new NormaDto();

        normaDto.setId(this.id);
        normaDto.setCodNorma(this.codNorma);
        normaDto.setNombre(this.nombre);
        normaDto.setDescripcion(this.descripcion);
        normaDto.setEstado(this.estado);
        normaDto.setUsersToComment(this.usersToComment);
        normaDto.setFecha(this.fecha);
        normaDto.setTipoTabla(this.tipoTabla);
        normaDto.setUrlPdf(this.urlPdf);
        normaDto.setUrlCad(this.urlCad);
        normaDto.setDownloadCounter(this.downloadCounter);
        normaDto.setTipoNorma(this.tipoNorma);
        normaDto.setNombreIngles(this.nombreIngles);

        return normaDto;
    }
}
