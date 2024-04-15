package vigilidelfuoco.verona.gestioneferie.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.FileStorageService;
import vigilidelfuoco.verona.gestioneferie.service.PermessoService;
import vigilidelfuoco.verona.gestioneferie.model.FileEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@RestController
@RequestMapping("/permesso")
public class PermessoController {

	private final PermessoService permessoService;
	
	  @Autowired
	  private FileStorageService storageService;
	  
	public PermessoController(PermessoService permessoService, FileStorageService storageService ) {
		super();
		this.permessoService = permessoService;
		this.storageService= storageService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Permesso>> getAllPermessi(){
		List<Permesso> permessi = permessoService.trovaPermessi();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Permesso>> getPermessoByStatus(@PathVariable("status") int status){
		List<Permesso> permessi = permessoService.findPermessoByStatus(status);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/statusRichiedente/{status}") //restituisce i permessi dell'utente loggato
	public ResponseEntity<List<Permesso>> getPermessoRichiedenteByStatus(@PathVariable("status") int status, @RequestParam("idRichiedente") Long idRichiedente ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idRichiedente);

		List<Permesso> permessi = permessoService.findPermessoRichiedenteByStatus(status, idRichiedente);
		
		if (!permessi.isEmpty()) {
		   
		    System.out.println(permessi.get(0).toString());
		}
		
		
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/permessiRichiedente") //restituisce i permessi dell'utente loggato
	public ResponseEntity<List<Permesso>> getPermessoRichiedente(@RequestParam("idRichiedente") Long idRichiedente ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idRichiedente);

		List<Permesso> permessi = permessoService.findPermessoRichiedente(idRichiedente);

			
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/statusApprovatore/{status}") //restituisce i permessi relativi all'approvatore
	public ResponseEntity<List<Permesso>> getPermessiApprovatoreByStatus(@PathVariable("status") int status, @RequestParam("idApprovatore") Long idApprovatore ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idApprovatore);

		List<Permesso> permessi = permessoService.findPermessoApprovatoreByStatus(status, idApprovatore);

		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	
	// USATO ATTUALMENTE
	@GetMapping("/permessiApprovatore") //restituisce i permessi relativi all'approvatore
	public ResponseEntity<List<Permesso>> getPermessiApprovatore(@RequestParam("idApprovatore") Long idApprovatore ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idApprovatore);

		List<Permesso> permessi = permessoService.findPermessoApprovatore( idApprovatore);

		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PutMapping("/approvaPermesso")
	public ResponseEntity<Permesso> changeStatusPermesso(@RequestParam("idApprovatore") Long idApprovatore, @RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e l'id dell'approvatore è : "+ idApprovatore);
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		//Permesso permessoAggiornato = permessoService.aggiornaStatusPermesso(permesso, idApprovatore);
		Permesso permessoAggiornato = permessoService.aggiornaStatusPermesso2(permesso, idApprovatore);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
//	@PutMapping("/aggiornaPermesso")
//	public ResponseEntity<Permesso> aggiornaPermesso(@RequestParam("idApprovatore") Long idApprovatore, @RequestParam("statusPermesso") int statusPermesso, @RequestBody Permesso permesso ){
//		
//		System.out.println("sono dentro aggiornaPermesso  controller e l'id dell'approvatore è : "+ idApprovatore);
//		System.out.println("sono dentro aggiornaPermesso  controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
//		System.out.println("sono dentro aggiornaPermesso  controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());
//		System.out.println("sono dentro aggiornaPermesso  controller  e statusPermesso è : "+ statusPermesso);
//
//		Permesso permessoAggiornato = permessoService.aggiornaStatusPermesso2(permesso, idApprovatore,statusPermesso);
//
//		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
//	}
//	
	
	@PutMapping("/confermaPermessoPersonale")
	public ResponseEntity<Permesso> aggiornaPermessoPersonale(@RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.aggiornaPermessoPersonale(permesso);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	@PutMapping("/respingiPermesso")
	public ResponseEntity<Permesso> respingiPermesso(@RequestParam("idApprovatore") Long idApprovatore, @RequestParam("note") String note, @RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e le note sono : "+ permesso.getNote());
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.respingiPermesso(note, permesso, idApprovatore);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	@PutMapping("/respingiPermessoPersonale")
	public ResponseEntity<Permesso> respingiPermessoPersonale(@RequestParam("idApprovatore") Long idApprovatore, @RequestParam("note") String note, @RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e le note sono : "+ permesso.getNote());
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.respingiPermessoPersonale(note, permesso, idApprovatore);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	
	@GetMapping("/allCongedo")
	public ResponseEntity<List<Permesso>> getAllCongedo(){
		List<Permesso> permessi = permessoService.trovaTipoCongedo();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
//	@PostMapping("/search") 
//	//public ResponseEntity<List<Permesso>> getFilteredPermessi(@RequestParam("dataAssenza") String dataAssenza, @RequestParam("status") int status, @RequestBody Permesso permesso){
//	public ResponseEntity<List<Permesso>> getFilteredPermessi(@RequestParam("dataAssenza") String dataAssenza, @RequestBody Permesso permesso){
//		//List<Permesso> permessi = permessoService.getFilteredPermessi(permesso,dataAssenza, status);
//		List<Permesso> permessi = permessoService.getFilteredPermessi(permesso,dataAssenza);
//
//		return new ResponseEntity<>(permessi, HttpStatus.OK);
//	}
	
	
	@PostMapping("/searchNew") 
	public ResponseEntity<List<Permesso>> getFilteredPermessiNew(@RequestParam("dataAssenza") String dataAssenza, @RequestParam("tipoPermesso") String tipoPermesso, @RequestParam("utenteRichiedente") Long utenteRichiedente, @RequestParam("dataApprovazione") String dataApprovazione, @RequestParam("utenteApprovatore") Long utenteApprovatore){
		//List<Permesso> permessi = permessoService.getFilteredPermessi(permesso,dataAssenza, status);
		//System.out.println(utenteRichiedente.toString() + " is of type " + ((Object)utenteRichiedente).getClass().getSimpleName());  
		
		List<Permesso> permessi = permessoService.getFilteredPermessiNew(dataAssenza, tipoPermesso, utenteRichiedente, dataApprovazione, utenteApprovatore  );
		
		   
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//public ResponseEntity<Permesso> aggiungiPermesso(@RequestBody Permesso permesso){
		public ResponseEntity<String> aggiungiPermesso(@RequestBody Permesso permesso, @RequestParam("idUtenteLoggato") Long idUtenteLoggato ){
		
		
		ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		System.out.println("ID 1 è: " + permesso.getIdUtenteApprovazione().toString() );
//		System.out.println("ID 2 è: " + permesso.getIdUtenteApprovazioneDue().toString() );
		
//		Permesso permessoValido = permessoService.aggiungiPermesso(permesso);
//		
//		if(permessoValido != null) {
//			response= ResponseEntity.status(HttpStatus.CREATED).body("Richiesta di permesso inviata correttamente!");
//		}else {
//			response= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Qualcosa è andato storto");
//		}
		
		if(permesso.getIdUtenteApprovazioneDue()!= null && permesso.getIdUtenteApprovazione().equals(permesso.getIdUtenteApprovazioneDue())) {
				System.out.println("I DUE SONO UGUALI");

				response= ResponseEntity.status(HttpStatus.CONFLICT).body("I due funzionari/capiturno non possono essere uguali!");
		} 
		
		else if(permesso.getIdUtenteApprovazione()==null && !(permesso.getTipoPermesso().contains("Malattia") || permesso.getTipoPermesso().contains("salvavita"))){
				response= ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Devi selezionare almeno un approvatore!");
		} 
		
		else {
				//Permesso newPermesso= permessoService.aggiungiPermesso(permesso);
//				System.out.println("I DUE non SONO UGUALI");
//				System.out.println("Id dell'utente loggato= " +idUtenteLoggato.toString());
				
				permessoService.aggiungiPermesso(permesso, idUtenteLoggato);

				//return new ResponseEntity<>(newPermesso, HttpStatus.CREATED);
		        response= ResponseEntity.status(HttpStatus.CREATED).body("Richiesta di permesso inviata correttamente!");
		}
		

		return response;

	}
	
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> cancellaPermesso(@PathVariable("id") Long id) throws IOException{
		boolean ok= permessoService.deletePermessoById(id);
		if(ok) {
			//return new ResponseEntity<>(HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Permesso eliminato correttamente!");
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: permesso già confermato o non trovato!");
		}
	}
	

	
}
