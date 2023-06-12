package vigilidelfuoco.verona.gestioneferie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Qualifica;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.QualificaService;

@RestController
@RequestMapping("/qualifica")
public class QualificaController {

	private final QualificaService qualificaService;

	public QualificaController(QualificaService qualificaService) {
		super();
		this.qualificaService = qualificaService;
	}
	
	@PostMapping("/addMore")
	public ResponseEntity<String> addMoreQualifiche(@RequestBody List<Qualifica> qualifiche){
	
	    boolean newUserAdded = false;

	    for (Qualifica qualifica : qualifiche) {
	        if (qualificaService.checkQualificaIfExists(qualifica)) {
	        	qualificaService.aggiungiQualifica(qualifica);
	            newUserAdded = true;
	        }
	    }

	    if (newUserAdded) {
	        return ResponseEntity.status(HttpStatus.CREATED).body("Qualifiche added successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No new qualifiche were added");
	    }
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Qualifica>> getAllQualifiche(){
		
		System.out.println("sono dentro all qualifiche");
		List<Qualifica> qualifiche = qualificaService.getAllQualifiche();
		return new ResponseEntity<>(qualifiche, HttpStatus.OK);
	}
	
	
}
