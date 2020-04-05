package cl.desagen.chilquinta.entities;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "detalle_perfil", schema = "dbo", catalog = "NORMAS")
public class DetallePerfilEntity {
    private Long id;
    private Integer usuarioId;
    private Integer perfilId;

    @Id
    @JsonPropertyOrder
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "usuario_id")
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Basic
    @Column(name = "perfil_id")
    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePerfilEntity that = (DetallePerfilEntity) o;
        return id == that.id &&
                Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(perfilId, that.perfilId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, usuarioId, perfilId);
    }
}
