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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<String> aggiungiUtente(@RequestBody Utente utente){
	//public ResponseEntity<String> aggiungiUtente(@RequestBody Utente utente){
	
		ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if(gestioneUtenti.checkUtenteIfExists(utente)) { //se non esiste
			System.out.println("l'utente non esiste");
			//Utente newUtente = gestioneUtenti.aggiungiUtente(utente);
			
			gestioneUtenti.aggiungiUtente(utente);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Utente aggiunto correttamente!");
			//return new ResponseEntity<>("Utente creato correttamente", HttpStatus.CREATED);

		}else {
			System.out.println("l'utente esiste già");

			response= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'account dipvvf o la mail sono già esistenti ");
			//return new ResponseEntity<>("La mail Vigilfuoco o l'account Dipvvf sono già registrati", HttpStatus.NOT_ACCEPTABLE);

		}
		return response;
		
	}
	
	@PostMapping("/addMore")
	public ResponseEntity<String> addMoreUtenti(@RequestBody List<Utente> utenti){
	
	    boolean newUserAdded = false;

	    for (Utente utente : utenti) {
	        if (gestioneUtenti.checkUtenteIfExists(utente)) {
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
		System.out.println("idutenteloggato: " + idUtenteLoggato);
		System.out.println("oldPass: " + oldPass);

		System.out.println("newPass: " + newPass);

		
		boolean matchingPass = gestioneUtenti.changePass(oldPass, newPass, idUtenteLoggato);
		if(matchingPass) {
			System.out.println("sono dentro utentecontroller responsenetyty OK");

	        return ResponseEntity.status(HttpStatus.OK).body("Password aggiornata correttamente!");

		}else {
	        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("La password vecchia non è corretta.");
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaUtente(@PathVariable("id") Long id){
		gestioneUtenti.deleteUtenteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
