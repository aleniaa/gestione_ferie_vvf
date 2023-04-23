package vigilidelfuoco.verona.gestioneferie.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
import vigilidelfuoco.verona.gestioneferie.exception.UserAlreadyExistsException;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class GestioneUtentiService {

	private final UtenteRepo utenteRepo;

	
	public GestioneUtentiService(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
	}
	
	public Utente aggiungiUtente(Utente utente){

		utente.setCodiceUtente(UUID.randomUUID().toString());
		utente.setAccountDipvvf();
		return utenteRepo.save(utente);
		
		
	}
	
//	public Utente aggiungiPiuUtenti(List<Utente> utente){
//		for(int i=0; i<= utente.size();i++) {
//			
//		}
//		utente.setCodiceUtente(UUID.randomUUID().toString());
//		return utenteRepo.save(utente);
//		
//		
//	}
	
	public List<Utente> trovaUtenti(){
		return utenteRepo.findAll();
	}
	
	public Utente aggiornaUtente(Utente utente) {
		return utenteRepo.save(utente);
	}
	
	public Utente findUtenteById(Long id) {
		return utenteRepo.findUtenteById(id)
				.orElseThrow(() -> new UserNotFoundException("Utente con id "+id +" non trovato"));
	}
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deleteUtenteById(Long id) {
		utenteRepo.deleteUtenteById(id);
	}
	
	public boolean checkUtenteIfExists(Utente utente) {
		if(!utenteRepo.existsUtenteByemailVigilfuoco(utente.getEmailVigilfuoco())) {
			return true;
		}else {
			 new UserNotFoundException("Utente già presente");
			 return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}