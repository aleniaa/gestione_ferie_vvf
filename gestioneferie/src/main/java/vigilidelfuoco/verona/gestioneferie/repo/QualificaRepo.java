package vigilidelfuoco.verona.gestioneferie.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.Qualifica;

public interface QualificaRepo extends JpaRepository<Qualifica, Long>  {

	
	boolean existsByNome(String nome);
	
	
	
	
}
