package vigilidelfuoco.verona.gestioneferie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.PermessoService;

@RestController
@RequestMapping("/permesso")
public class PermessoController {

	private final PermessoService permessoService;
	public PermessoController(PermessoService permessoService) {
		super();
		this.permessoService = permessoService;
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
	
	@GetMapping("/allCongedo")
	public ResponseEntity<List<Permesso>> getAllCongedo(){
		List<Permesso> permessi = permessoService.trovaTipoCongedo();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/search") //da modificare in caso la get in post
	public ResponseEntity<List<Permesso>> getFilteredPermessi(@RequestBody Permesso permesso){
		List<Permesso> permessi = permessoService.getFilteredPermessi(permesso);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Permesso> aggiungiPermesso(@RequestBody Permesso permesso){
		
		Permesso newPermesso= permessoService.aggiungiPermesso(permesso);
		return new ResponseEntity<>(newPermesso, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaPermesso(@PathVariable("id") Long id){
		permessoService.deletePermessoById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@GetMapping("/find/{idUtente}")
//	public ResponseEntity<List<Permesso>> findPermessiByIdUtente(@PathVariable("idUtente") Long id){
//		List<Permesso> permessi = permessoService.findPermessiByIdUtente(id);
//		return new ResponseEntity<>(permessi, HttpStatus.OK);
//	}
	
}
