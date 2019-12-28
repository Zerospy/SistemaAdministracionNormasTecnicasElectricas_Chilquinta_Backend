package cl.desagen.chilquinta.repositories;

import cl.desagen.chilquinta.entities.SolicitudObservacionNormaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitudObservacionNormaRepository extends PagingAndSortingRepository<SolicitudObservacionNormaEntity, Integer> {

    Iterable<SolicitudObservacionNormaEntity> findAllByNormaEntityId(Integer normaId);

    @Query("SELECT o FROM SolicitudObservacionNormaEntity o order by o.createdAt desc")
    List<SolicitudObservacionNormaEntity> findAllByNormaEntityId(Pageable pageable);

    @Query("SELECT o.normaEntity.id FROM SolicitudObservacionNormaEntity o WHERE o.usuarioRecibeEntity.id = :id GROUP BY o.normaEntity.id")
    List<Integer> getUsersIds(@Param("id") Integer id);
}
