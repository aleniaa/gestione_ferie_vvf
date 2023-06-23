package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;


public interface FileRepo extends JpaRepository<FileEntity, Long> {

	List<FileEntity> findByIdPermessoAssociato(Long idPermessoAssociato);
	Optional<FileEntity> findByIdPermessoAssociatoAndFilename(Long idPermessoAssociato, String filename);

	@Query(nativeQuery = true, value= "SELECT filename FROM gestioneferie.uploaded_file "
			+ "WHERE uploaded_file.id_Permesso_Associato =?1")
	List<String> findFileNameByIdPermessoAssociato(Long idPermessoAssociato);
	
	void deleteById(Long id);
	void deleteByIdPermessoAssociato(Long idPermessoAssociato);

	void deleteByIdPermessoAssociatoAndFilename(Long idPermessoAssociato, String filename);

}
