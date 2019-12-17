package cl.desagen.chilquinta.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "solicitud_observacion_norma", schema = "dbo", catalog = "NORMAS")
public class SolicitudObservacionNormaEntity {

    private Integer id;

    private NormaEntity normaEntity;

    private String comments;

    private UsuarioEntity usuarioSolicitaEntity;

    private UsuarioEntity usuarioRecibeEntity;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Transient
    private Boolean isCurrentUserComment;

    public Boolean getIsCurrentUserComment() {
        return true;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "norma_id")
    public NormaEntity getNormaEntity() {
        return normaEntity;
    }

    public void setNormaEntity(NormaEntity normaEntity) {
        this.normaEntity = normaEntity;
    }

    @Basic
    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_solicitud_id")
    public UsuarioEntity getUsuarioSolicitaEntity() {
        return usuarioSolicitaEntity;
    }

    public void setUsuarioSolicitaEntity(UsuarioEntity usuarioSolicitaEntity) {
        this.usuarioSolicitaEntity = usuarioSolicitaEntity;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_recibe_id")
    public UsuarioEntity getUsuarioRecibeEntity() {
        return usuarioRecibeEntity;
    }

    public void setUsuarioRecibeEntity(UsuarioEntity usuarioRecibeEntity) {
        this.usuarioRecibeEntity = usuarioRecibeEntity;
    }

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at")
    @UpdateTimestamp
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudObservacionNormaEntity that = (SolicitudObservacionNormaEntity) o;
        return id == that.id &&
                Objects.equals(normaEntity, that.normaEntity) &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, normaEntity, comments);
    }
}
