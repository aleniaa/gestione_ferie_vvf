package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface PermessoRepo  extends JpaRepository<Permesso, Long> {
	
	void deletePermessoById(Long id);
	Optional<Permesso> findPermessoById(Long id);
	//List<Permesso> findPermessoByIdUtente(Long idUtente);
	List<Permesso> findPermessoBytipoPermesso(String tipoPermesso);
	@Query(nativeQuery = true, value= "SELECT utente.* FROM permesso "
			+ "INNER JOIN gestioneferie.utente "
			+ "ON gestioneferie.permesso.id_utente_approvazione = gestioneferie.utente.id "
			+ "WHERE gestioneferie.permesso.id_utente_approvazione = 852 AND gestioneferie.permesso.id = 611")
	Utente findUtenteByIdUtenteApprovazione();
	//List<Utente> findUtenteByIdUtenteApprovazione(Long idUtenteApprovazione, Long idPermesso);
	
	

}
