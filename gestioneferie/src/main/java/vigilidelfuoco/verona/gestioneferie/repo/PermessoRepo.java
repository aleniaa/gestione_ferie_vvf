package vigilidelfuoco.verona.gestioneferie.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;

public interface PermessoRepo  extends JpaRepository<Permesso, Long> {
	
	void deletePermessoById(Long id);
	Optional<Permesso> findPermessoById(Long id);
	//List<Permesso> findPermessoByIdUtente(Long idUtente);
	List<Permesso> findPermessoBytipoPermesso(String tipoPermesso);
	
	

}
