package cl.desagen.chilquinta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "solicitud_observacion_norma", schema = "dbo", catalog = "NORMAS")
public class SolicitudObservacionNormaEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "norma_id")
    private NormaEntity normaEntity;

    @Basic
    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_solicitud_id", referencedColumnName = "id")
    private UsuarioEntity usuarioSolicitaEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_recibe_id", referencedColumnName = "id")
    private UsuarioEntity usuarioRecibeEntity;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Basic
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

}
