package vigilidelfuoco.verona.gestioneferie.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface PermessoRepo  extends JpaRepository<Permesso, Long> {
	
	
	
	void deletePermessoById(Long id);
	Optional<Permesso> findPermessoById(Long id);
	@Query(nativeQuery = true, value= "SELECT * FROM gestioneferie.permesso "
			+ "WHERE permesso.id =?1")
	Permesso findPermessoByIdsenzaoptional(Long id);
	
	List<Permesso> findPermessoByDataApprovazione(LocalDate dataAprovazione);
	List<Permesso> findPermessoByStatusAndIdUtenteApprovazione(int status, Long idUtenteApprovazione);
	List<Permesso> findPermessoByStatus(int status);
	List<Permesso> findPermessoBytipoPermesso(String tipoPermesso);
	
	
//	@Query(nativeQuery = true, value= "SELECT utente.* FROM permesso "
//			+ "INNER JOIN gestioneferie.utente "
//			+ "ON gestioneferie.permesso.id_utente_approvazione = gestioneferie.utente.id "
//			+ "WHERE gestioneferie.permesso.id_utente_approvazione = 852 AND gestioneferie.permesso.id = 611")
//	Utente findUtenteByIdUtenteApprovazione();
	//List<Utente> findUtenteByIdUtenteApprovazione(Long idUtenteApprovazione, Long idPermesso);
	
	

}
