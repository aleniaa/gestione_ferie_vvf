package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Long>{

	Utente findUtenteById(Long id);
	void deleteUtenteById(Long id);
	Utente findByaccountDipvvf(String accountDipvvf);

	Utente findUtenteByemailVigilfuoco(String emailVigilfuoco);

	List<Utente> findUtenteByRuolo(String ruolo);

}
