package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;


public interface FileRepo extends JpaRepository<FileEntity, Long> {

	Optional<FileEntity> findByIdPermessoAssociato(Long idPermessoAssociato);
}
