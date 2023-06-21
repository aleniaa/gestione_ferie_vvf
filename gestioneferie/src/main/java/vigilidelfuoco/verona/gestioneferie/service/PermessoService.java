package vigilidelfuoco.verona.gestioneferie.service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.repo.FileRepo;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;
import vigilidelfuoco.verona.gestioneferie.model.FileEntity;


@Service
public class PermessoService {
	
	private final PermessoRepo permessoRepo;
	private final UtenteRepo utenteRepo;
	private final  FileRepo fileRepo;

	@Autowired
	public PermessoService(PermessoRepo permessoRepo, UtenteRepo utenteRepo, FileRepo fileRepo) {
		super();
		this.permessoRepo = permessoRepo;
		this.utenteRepo= utenteRepo;
		this.fileRepo= fileRepo;
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
	
	public List<Permesso> findPermessoRichiedenteByStatus(int status, Long idUtente){
		return permessoRepo.findPermessoByStatusAndIdUtenteRichiedenteOrderByDataApprovazioneDesc(status, idUtente);
	}
	
	public List<Permesso> findPermessoApprovatoreByStatus(int status, Long idUtente){
		return permessoRepo.findPermessoByStatusAndIdUtenteApprovazioneOrIdUtenteApprovazioneDueOrderByDataApprovazioneDesc(status, idUtente, idUtente);

		//return permessoRepo.findPermessoByStatusAndIdUtenteApprovazioneOrderByDataApprovazioneDesc(status, idUtente);
	}
	
	public Permesso findPermessoById(Long id) {
		return permessoRepo.findPermessoById(id)
				.orElseThrow(() -> new UserNotFoundException("Permesso con id "+id +" non trovato"));
	}
	
	
	
	public List<Permesso> getFilteredPermessi(Permesso permesso){
		
		List<Permesso> permessiTot= new ArrayList<Permesso>();
		
		Permesso filtroPermesso= new Permesso();
		System.out.println("filtro permesso iniziale : "+filtroPermesso.toString());
		
		filtroPermesso.setDataApprovazione(permesso.getDataApprovazione());
		filtroPermesso.setIdUtenteApprovazione(permesso.getIdUtenteApprovazione());
		filtroPermesso.setIdUtenteRichiedente(permesso.getIdUtenteRichiedente()); //per l'id del richiedente
		filtroPermesso.setStatus(1);
 
		
		if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
			System.out.println("tipo permesso uguale stringa vuota");
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAll(permessoExample);
			
			//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();

			
		}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
			
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAltriPermessi(permessoExample);
			
		}else { // se il permesso è congedo o recupero ore o permeso breve
			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")) {
				filtroPermesso.setTipoPermesso(null);

			}else {
				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());

			}
			
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAll(permessoExample);

		}
		


		System.out.println(filtroPermesso.toString());

		
		return permessiTot;
	} 
	

	
	public void aggiungiPermesso(Permesso permesso){
		
	
		if(permesso.getDataInizio()!=null && permesso.getDataFine()!=null){
			System.out.println("sono dentro permesso service setTotGiorni");

			permesso.setTotGiorni();
		}
		System.out.println("id utente approvazione = "+ permesso.getIdUtenteApprovazione());
		System.out.println("id utente approvazione DUE = "+ permesso.getIdUtenteApprovazioneDue());
		System.out.println("id utente richiedente = "+ permesso.getIdUtenteRichiedente());
		Utente utenteApprovazione = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazione());
		Utente utenteApprovazioneDue = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazioneDue());
		Utente utenteRichiedente = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteRichiedente());
//		Utente utenteApprovazione = permessoRepo.findUtenteByIdUtenteApprovazione();
		
		System.out.println("utente richiedente trovato: = "+ utenteRichiedente.toString());
		
		
		permesso.setUtenteRichiedente(utenteRichiedente);
		permesso.setUtenteApprovazione(utenteApprovazione);
		
		permesso.setUtenteApprovazioneDue(utenteApprovazioneDue);
		System.out.println("utente approvazione DUE è"+ permesso.getUtenteApprovazioneDue());
		permesso.setStatus(0);
		
		permessoRepo.save(permesso);
		//return permessoRepo.save(permesso);
	
	}
	
	public Permesso aggiornaStatusPermesso(Permesso permesso) {
		
		System.out.println("sono dentro aggiornastatuspermesso service e le note sono : "+ permesso.getNote());
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		if(permessoDaAggiornare.getUtenteApprovazioneDue()==null || permessoDaAggiornare.getStatus()==1) {
			permessoDaAggiornare.setStatus(2);
		}else {
			permessoDaAggiornare.setStatus(1);
		}
		
		
		
		
		LocalDate dataApprovazione = LocalDate.now();
		permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		

		return permessoRepo.save(permessoDaAggiornare);
	}
	
	public Permesso respingiPermesso(String note, Permesso permesso) {
		
		System.out.println("sono dentro aggiornastatuspermesso service e le note sono : "+ note);
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		LocalDate dataApprovazione = LocalDate.now();
		permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		permessoDaAggiornare.setStatus(2);
		permessoDaAggiornare.setNote(note);
		return permessoRepo.save(permessoDaAggiornare);
	}
	
	
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deletePermessoById(Long id) {
		permessoRepo.deletePermessoById(id);
	}

}
