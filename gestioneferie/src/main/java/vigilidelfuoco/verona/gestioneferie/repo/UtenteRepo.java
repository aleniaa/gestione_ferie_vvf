package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Long>{

	void deleteUtenteById(Long id);
	boolean findByemailVigilfuoco(String emailVigilfuoco);
	Utente findUtenteByemailVigilfuoco(String emailVigilfuoco);
	Utente findByaccountDipvvf(String accountDipvvf);
	Optional<Utente> findUtenteById(Long id);
	boolean existsUtenteByemailVigilfuoco(String emailVigilfuoco);
	Optional<List<Utente>> findUtenteByRuolo(String ruolo);
	

}
