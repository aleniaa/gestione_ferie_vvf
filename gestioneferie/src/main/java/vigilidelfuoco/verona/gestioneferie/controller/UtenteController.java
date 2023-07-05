package vigilidelfuoco.verona.gestioneferie.controller;

import java.util.List;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.model.UtenteRuolo;
import vigilidelfuoco.verona.gestioneferie.service.UtenteService;
import vigilidelfuoco.verona.gestioneferie.service.interfaces.IUtenteService;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/utente")
public class UtenteController {


	@Autowired
	IUtenteService gestioneUtenti;
	/*private final IUtenteService gestioneUtenti;

	@Autowired
	public UtenteController(IUtenteService gestioneUtenti) {
		super();
		this.gestioneUtenti = gestioneUtenti;
	}*/

	@GetMapping("/all")
	public ResponseEntity<List<Utente>> getAllUtenti(){
		List<Utente> utenti = gestioneUtenti.trovaUtenti();
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}

	// questo metodo è stato aggiunto per permettere di recuperare un utente in base al suo ruolo
	@GetMapping("/find/all/role/{role}")
	public ResponseEntity<List<Utente>> getUtentiFerie(@PathVariable("role") String role){
		try {
			List<Utente> utenti = gestioneUtenti.findUtenteByRuolo(UtenteRuolo.valueOf(role.toUpperCase()));
			return new ResponseEntity<>(utenti, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/ferie")
	public ResponseEntity<List<Utente>> getUtentiFerie(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuolo(UtenteRuolo.FERIE);
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}

	@GetMapping("/utenti")
	public ResponseEntity<List<Utente>> getUtenti(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuolo(UtenteRuolo.UTENTE);
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<List<Utente>> getAdmin(){
		List<Utente> utenti = gestioneUtenti.findUtenteByRuolo(UtenteRuolo.ADMIN);
		return new ResponseEntity<>(utenti, HttpStatus.OK);
	}
	@SneakyThrows
	@GetMapping("/find/{id}")
	public ResponseEntity<Utente> getUtentebyId(@PathVariable("id") Long id){
		Utente utente = gestioneUtenti.findUtenteById(id);
		if(!isNull(utente)){
			return new ResponseEntity<>(utente, HttpStatus.OK);
		}
		throw new Exception("Utente non trovato");
	}
	/*
	 * Utilizzare un dto
	 */
	@PostMapping("/add")
	@SneakyThrows
	public ResponseEntity<Utente> aggiungiUtente(@RequestBody Utente utente){
		if(gestioneUtenti.checkUtenteIfExists(utente)) {
			throw new Exception("Utente già esistente");
		}
		Utente newUtente = gestioneUtenti.aggiungiUtente(utente);
		return new ResponseEntity<>(newUtente, HttpStatus.CREATED);

	}
	
	@PostMapping("/addMore")
	public ResponseEntity<String> addMoreUtenti(@RequestBody List<Utente> utenti){
	
	    boolean newUserAdded = false;
	    for (Utente utente : utenti) {
	        if (!gestioneUtenti.checkUtenteIfExists(utente)) {
	            gestioneUtenti.aggiungiUtente(utente);
	            newUserAdded = true;
	        }
	    }
	    if (newUserAdded) {
	        return ResponseEntity.status(HttpStatus.CREATED).body("Utenti added successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No new users were added");
	    }
	}
		
	
	
	@PutMapping("/update")
	public ResponseEntity<Utente> aggiornaUtente(@RequestBody Utente utente){
		Utente utenteAggiornato = gestioneUtenti.aggiornaUtente(utente);
		return new ResponseEntity<>(utenteAggiornato, HttpStatus.OK);
	}
	
	@PutMapping("/changePass")
	public ResponseEntity<String> changePass(
			@RequestParam("idUtenteLoggato") Long idUtenteLoggato, 
			@RequestParam("oldPass") String oldPass, 
			@RequestParam("newPass") String newPass 
			)
	{
		System.out.println("idUtenteLoggato: " + idUtenteLoggato);

		boolean matchingPass = gestioneUtenti.changePass(oldPass, newPass, idUtenteLoggato);
		return matchingPass ? ResponseEntity.status(HttpStatus.OK).body("Password aggiornata correttamente!")
				: ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("La password vecchia non è corretta.");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaUtente(@PathVariable("id") Long id){
		gestioneUtenti.deleteUtenteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
