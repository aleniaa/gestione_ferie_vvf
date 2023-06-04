package vigilidelfuoco.verona.gestioneferie.controller;

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
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/statusApprovatore/{status}") //restituisce i permessi relativi all'approvatore
	public ResponseEntity<List<Permesso>> getPermessiApprovatoreByStatus(@PathVariable("status") int status, @RequestParam("idApprovatore") Long idApprovatore ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idApprovatore);

		List<Permesso> permessi = permessoService.findPermessoApprovatoreByStatus(status, idApprovatore);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PutMapping("/approvaPermesso")
	public ResponseEntity<Permesso> changeStatusPermesso(@RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e le note sono : "+ permesso.getNote());
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.aggiornaStatusPermesso(permesso);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	@PutMapping("/respingiPermesso")
	public ResponseEntity<Permesso> respingiPermesso(@RequestParam("note") String note, @RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e le note sono : "+ permesso.getNote());
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.respingiPermesso(note, permesso);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	
	@GetMapping("/allCongedo")
	public ResponseEntity<List<Permesso>> getAllCongedo(){
		List<Permesso> permessi = permessoService.trovaTipoCongedo();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/search") 
	public ResponseEntity<List<Permesso>> getFilteredPermessi(@RequestBody Permesso permesso){
		List<Permesso> permessi = permessoService.getFilteredPermessi(permesso);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//public ResponseEntity<Permesso> aggiungiPermesso(@RequestBody Permesso permesso){
		public ResponseEntity<String> aggiungiPermesso(@RequestBody Permesso permesso){

		
		//Permesso newPermesso= permessoService.aggiungiPermesso(permesso);
		permessoService.aggiungiPermesso(permesso);

		//return new ResponseEntity<>(newPermesso, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body("Richiesta di permesso inviata correttamente!");

	}
	
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaPermesso(@PathVariable("id") Long id){
		permessoService.deletePermessoById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
