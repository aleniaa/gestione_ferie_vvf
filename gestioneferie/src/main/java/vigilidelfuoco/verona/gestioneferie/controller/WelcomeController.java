
package vigilidelfuoco.verona.gestioneferie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class WelcomeController {
	/*
	private final LoginService loginService;
	private final GestioneUtentiService utenteService;

	
	public WelcomeController(LoginService loginService, GestioneUtentiService utenteService) {
		super();
		this.loginService = loginService;
		this.utenteService = utenteService;
		
	}
	
	@PostMapping
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
	
	//public ResponseEntity<Utente> login(@RequestBody Utente utente) {
		
		System.out.println("si sta provamdo a loggare "+ username + " con password:  " + password);
		Utente utenteLoggato = loginService.validaLogin(username, password);
		

		if(utenteLoggato!=null) {
			System.out.println("login success true");
			
			return new ResponseEntity<>(utenteLoggato, HttpStatus.OK);

			//return ResponseEntity.ok("loggato");
			//return new ResponseEntity<>(HttpStatus.OK);
//		}else {
//			System.out.println("login success false");
//
//			return new ResponseEntity<>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
//		}
		
			
	}else {
		System.out.println("login success false");
		return new ResponseEntity<>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
		
		//utenteLoggato= null;
	}
		
		//return new ResponseEntity<>(utenteLoggato, HttpStatus.OK);
		
	}
	
	@PutMapping("/checkPass")
		public ResponseEntity<String> checkPasswordFirstAccess(@RequestParam("password") String password, @RequestParam("idUtenteLoggato") Long idUtenteLoggato){

		
		boolean isDefaultPass = utenteService.checkPasswordFirstAccess(idUtenteLoggato);


        if (isDefaultPass) {
        	System.out.println("la password è quella di default");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Password da cambiare\"}");
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"message\": \"Password ok\"}");
        }
		
		

	}

	 */
}




