package vigilidelfuoco.verona.gestioneferie.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class GestioneUtenti {

	private final UtenteRepo utenteRepo;

	
	public GestioneUtenti(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
	}
	
	public Utente aggiungiUtente(Utente utente){
		utente.setCodiceUtente(UUID.randomUUID().toString());
		return utenteRepo.save(utente);
	}
	
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
	
	public void deleteUtenteById(Long id) {
		utenteRepo.deleteUtenteById(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
