package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.Congedo;
import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface CongedoRepo  extends JpaRepository<Congedo, Long> {
	
	void deleteCongedoById(Long id);
	Optional<Congedo> findCongedoById(Long id);
	
	

}
