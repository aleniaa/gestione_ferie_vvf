package vigilidelfuoco.verona.gestioneferie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
