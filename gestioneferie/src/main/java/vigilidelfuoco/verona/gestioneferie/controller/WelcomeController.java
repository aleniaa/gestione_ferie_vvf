package vigilidelfuoco.verona.gestioneferie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/login")
	public String login() {
		return "Benvenuto su gestione ferie!";
		
	}

}
