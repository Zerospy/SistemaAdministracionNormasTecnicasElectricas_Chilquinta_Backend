package cl.desagen.chilquinta.repositories;

import cl.desagen.chilquinta.entities.SolicitudObservacionNormaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SolicitudObservacionNormaRepository extends PagingAndSortingRepository<SolicitudObservacionNormaEntity, Integer> {

    Iterable<SolicitudObservacionNormaEntity> findAllByNormaEntityId(Integer normaId);

    @Query("SELECT o FROM SolicitudObservacionNormaEntity o order by o.createdAt desc")
    List<SolicitudObservacionNormaEntity> findAllByNormaEntityId(Pageable pageable);

    @Query("SELECT o.normaEntity.id FROM SolicitudObservacionNormaEntity o GROUP BY o.normaEntity.id")
    List<Integer> getIdsNormasWithComments();
}
