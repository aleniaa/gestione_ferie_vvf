package vigilidelfuoco.verona.gestioneferie.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;


public interface FileRepo extends JpaRepository<FileEntity, Long> {

}
