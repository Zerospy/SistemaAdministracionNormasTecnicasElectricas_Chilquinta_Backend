package cl.desagen.chilquinta.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.desagen.chilquinta.entities.DetallePerfilEntity;

public interface DetallePerfilRepository extends PagingAndSortingRepository<DetallePerfilEntity, Long> {

    
	Optional<DetallePerfilEntity> findByUsuarioId(Integer id);
	

}
