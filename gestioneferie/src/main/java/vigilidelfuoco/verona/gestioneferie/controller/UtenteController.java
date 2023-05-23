package vigilidelfuoco.verona.gestioneferie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.GestioneUtentiService;

@RestController
@RequestMapping("/utente")
public class UtenteController {
	
	private final GestioneUtentiService gestioneUtenti;

	public UtenteController(GestioneUtentiService gestioneUtenti) {
		super();
		this.gestioneUtenti = gestioneUtenti;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Utente>> getAllUtenti(){
		List<Utente> utenti = gestioneUtenti.trovaUtenti();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	@GetMapping("/ferie")
	public ResponseEntity<List<Utente>> getUtentiFerie(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuoloFerie();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}

	@GetMapping("/utenti")
	public ResponseEntity<List<Utente>> getUtenti(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuoloUtente();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<List<Utente>> getAdmin(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuoloAdmin();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Utente> getUtentebyId(@PathVariable("id") Long id){
		Utente utente = gestioneUtenti.findUtenteById(id);
		return new ResponseEntity<>(utente, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Utente> aggiungiUtente(@RequestBody Utente utente){
	//public ResponseEntity<String> aggiungiUtente(@RequestBody Utente utente){
	
		if(gestioneUtenti.checkUtenteIfExists(utente)) { //se non esiste
			System.out.println("l'utente non esiste");
			Utente newUtente = gestioneUtenti.aggiungiUtente(utente);
			
			
			return new ResponseEntity<>(newUtente, HttpStatus.CREATED);
			//return new ResponseEntity<>("Utente creato correttamente", HttpStatus.CREATED);

		}else {
			System.out.println("l'utente esiste già");

			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			//return new ResponseEntity<>("La mail Vigilfuoco o l'account Dipvvf sono già registrati", HttpStatus.NOT_ACCEPTABLE);

		}
		
	}
	
//	@PostMapping("/addMore")
//	public ResponseEntity<Utente> aggiungipiuUtenti(@RequestBody List<Utente> utente){
//		if(gestioneUtenti.checkUtenteIfExists(utente)) { //se non esiste
//			Utente newUtente = gestioneUtenti.aggiungiUtente(utente);
//			
//			return new ResponseEntity<>(newUtente, HttpStatus.CREATED);
//		}else {
//			return new ResponseEntity<>(HttpStatus.ACCEPTED);
//		}
//		
//	}
	
	@PutMapping("/update")
	public ResponseEntity<Utente> aggiornaUtente(@RequestBody Utente utente){
		Utente utenteAggiornato = gestioneUtenti.aggiornaUtente(utente);
		return new ResponseEntity<>(utenteAggiornato, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaUtente(@PathVariable("id") Long id){
		gestioneUtenti.deleteUtenteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
