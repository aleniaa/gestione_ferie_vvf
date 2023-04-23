//package vigilidelfuoco.verona.gestioneferie.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import vigilidelfuoco.verona.gestioneferie.model.Utente;
//
//
//@RestController
//@RequestMapping("/login")
//public class WelcomeController {
//
//	@PostMapping
//	public ResponseEntity<String> login(@RequestBody Utente utente) {
//		System.out.println("si sta provamdo a loggare "+ utente.getAccountDipvvf() + " " + utente.getPassword());
//		
//		
//		return ResponseEntity.ok("loggato");
//		
//	}
//
//}



package vigilidelfuoco.verona.gestioneferie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.LoginService;


@RestController
@RequestMapping("/login")
public class WelcomeController {

	private final LoginService loginService;
	
	public WelcomeController(LoginService loginService) {
		super();
		this.loginService = loginService;
	}
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody Utente utente) {
		System.out.println("si sta provamdo a loggare "+ utente.getAccountDipvvf() + " " + utente.getPassword());
		boolean loginSuccess = loginService.validaLogin(utente);
		

		if(loginSuccess) {
			System.out.println("login success true");

			return new ResponseEntity<String>("LOGGATO", HttpStatus.OK);
			//return ResponseEntity.ok("loggato");
			//return new ResponseEntity<>(HttpStatus.OK);
		}else {
			System.out.println("login success false");

			return new ResponseEntity<String>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
		}
		

	}
}

