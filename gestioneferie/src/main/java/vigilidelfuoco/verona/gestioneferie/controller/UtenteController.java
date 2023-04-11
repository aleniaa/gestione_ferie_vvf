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
import vigilidelfuoco.verona.gestioneferie.service.GestioneUtenti;

@RestController
@RequestMapping("/utente")
public class UtenteController {
	private final GestioneUtenti gestioneUtenti;

	public UtenteController(GestioneUtenti gestioneUtenti) {
		super();
		this.gestioneUtenti = gestioneUtenti;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Utente>> getAllUtenti(){
		List<Utente> utenti = gestioneUtenti.trovaUtenti();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Utente> getUtentebyId(@PathVariable("id") Long id){
		Utente utente = gestioneUtenti.findUtenteById(id);
		return new ResponseEntity<>(utente, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Utente> aggiungiUtente(@RequestBody Utente utente){
		Utente newUtente = gestioneUtenti.aggiungiUtente(utente);
		return new ResponseEntity<>(newUtente, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Utente> aggiornaUtente(@RequestBody Utente utente){
		Utente utenteAggiornato = gestioneUtenti.aggiornaUtente(utente);
		return new ResponseEntity<>(utenteAggiornato, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete{id}")
	public ResponseEntity<?> cancellaUtente(@PathVariable("id") Long id){
		gestioneUtenti.deleteUtenteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
