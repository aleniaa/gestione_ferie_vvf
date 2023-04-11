package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Long>{

	void deleteUtenteById(Long id);

	Optional<Utente> findUtenteById(Long id);

}
