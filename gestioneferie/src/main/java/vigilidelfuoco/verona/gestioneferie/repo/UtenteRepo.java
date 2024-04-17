package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Long>{

	void deleteUtenteById(Long id);
	boolean findByemailVigilfuoco(String emailVigilfuoco);
	boolean findByAccountDipvvf(String accountDipvvf);
	Utente findUtenteByemailVigilfuoco(String emailVigilfuoco);
	Utente findByaccountDipvvf(String accountDipvvf);
	Optional<Utente> findUtenteById(Long id);
	@Query(nativeQuery = true, value= "SELECT * FROM gestioneferie.utente "
			+ "WHERE utente.id =?1")
	Utente findUtenteByIdsenzaoptional(Long idUtente);
	
	boolean existsUtenteByemailVigilfuoco(String emailVigilfuoco);
	boolean existsUtenteByAccountDipvvf(String accountDipvvf);
	boolean existsUtenteById(Long id);

	Optional<List<Utente>> findUtenteByRuolo(String ruolo);
	
	@Query(nativeQuery = true, value = "SELECT email_vigilfuoco FROM utente WHERE ruolo = ?1")
	List<String> findEmailVigilfuocoByRuolo(String ruolo);

	
//	@Query("SELECT utente.nome, utente.cognome FROM gestioneferie.permesso "
//			+ "INNER JOIN gestioneferie.utente "
//			+ "ON gestioneferie.permesso.id_utente_approvazione = gestioneferie.utente.id "
//			+ "WHERE gestioneferie.permesso.id_utente_approvazione = 852 AND gestioneferie.permesso.id = 611")
//	Utente findUtenteByIdUtenteApprovazione();
	
	@Query(nativeQuery = true, value = "SELECT * from utente WHERE nome= ?1")
	Optional<Utente> findByNome(String nome);
	
	//List<Utente> findUtenteByIdUtenteApprovazione(Long idUtenteApprovazione, Long idPermesso);

}
