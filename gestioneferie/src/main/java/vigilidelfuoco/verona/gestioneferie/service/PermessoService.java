package vigilidelfuoco.verona.gestioneferie.service;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class PermessoService {
	
	private final PermessoRepo permessoRepo;
	private final UtenteRepo utenteRepo;

	@Autowired
	public PermessoService(PermessoRepo permessoRepo, UtenteRepo utenteRepo) {
		super();
		this.permessoRepo = permessoRepo;
		this.utenteRepo= utenteRepo;
	}
	
	public List<Permesso> trovaPermessi(){
		return permessoRepo.findAll();
	}
	
	public List<Permesso> trovaTipoCongedo(){
		return permessoRepo.findPermessoBytipoPermesso("congedo");
	}
	
	public List<Permesso> findPermessoByStatus(int status){
		return permessoRepo.findPermessoByStatus(status);
	}
	
	public Permesso findPermessoById(Long id) {
		return permessoRepo.findPermessoById(id)
				.orElseThrow(() -> new UserNotFoundException("Permesso con id "+id +" non trovato"));
	}
	
//	public List<Permesso> findPermessoByStatusAndIdUtenteApprovazione(){
//	return
//}
	
//	public List<Permesso> findPermessiByIdUtente(Long idUtente){
//		return permessoRepo.findPermessoByIdUtente(idUtente);
//	}
	
	
	public List<Permesso> getFilteredPermessi(Permesso permesso){
		
		List<Permesso> permessiTot= new ArrayList<Permesso>();
		
		Permesso filtroPermesso= new Permesso();
		System.out.println("filtro permesso iniziale : "+filtroPermesso.toString());
		
		filtroPermesso.setDataApprovazione(permesso.getDataApprovazione());
		filtroPermesso.setIdUtenteApprovazione(permesso.getIdUtenteApprovazione());
		//filtroPermesso.setId(permesso.getId()); per l'id del richiedente
		
 
		
		if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
			System.out.println("tipo permesso uguale stringa vuota");
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAll(permessoExample);
			
		}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("Altri permessi")) {
			
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAltriPermessi(permessoExample);
			
		}else { // se il permesso è congedo o recupero ore o permeso breve

			filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAll(permessoExample);
		}
		


		System.out.println(filtroPermesso.toString());

		
		return permessiTot;
	} 
	
	public Permesso aggiungiPermesso(Permesso permesso){
		if(permesso.getDalleOre()!=null) { // se ci sono delle ore di permesso e non dei giorni
			permesso.setTotOre();
		}else {
			permesso.setTotGiorni();
		}
		System.out.println("id utente approvazione = "+ permesso.getIdUtenteApprovazione());
		Utente utenteApprovazione = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazione());
		
//		Utente utenteApprovazione = permessoRepo.findUtenteByIdUtenteApprovazione();
//		System.out.println("account dipvvf utente approvazione = "+ utenteApprovazione.getAccountDipvvf());
		permesso.setUtenteApprovazione(utenteApprovazione);
		permesso.setStatus(0);
		
		return permessoRepo.save(permesso);
	
	}
	
	public Permesso aggiornaStatusPermesso(String decisione, Permesso permesso) {
		
		System.out.println("sono dentro aggiornastatuspermesso service : "+ decisione);
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		LocalDate dataApprovazione = LocalDate.now();
		permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		if(decisione.equals("approva")) {
			permessoDaAggiornare.setStatus(1);
			System.out.println(" SONO DENTRO APPROVA : "+ decisione);

		}else {
			permessoDaAggiornare.setStatus(2);
		}
		return permessoRepo.save(permessoDaAggiornare);
	}
	
	
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deletePermessoById(Long id) {
		permessoRepo.deletePermessoById(id);
	}

}
