
package vigilidelfuoco.verona.gestioneferie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.ADAuthenticator;
import vigilidelfuoco.verona.gestioneferie.service.GestioneUtentiService;
import vigilidelfuoco.verona.gestioneferie.service.LoginService;


@RestController
@RequestMapping("/login")
public class WelcomeController {

	private final LoginService loginService;
	private final GestioneUtentiService utenteService;
	private final ADAuthenticator aDAuthenticator ;

	
	public WelcomeController(LoginService loginService, GestioneUtentiService utenteService, ADAuthenticator aDAuthenticator ) {
		super();
		this.loginService = loginService;
		this.utenteService = utenteService;
		this.aDAuthenticator = aDAuthenticator;
		
	}
	
	@PostMapping
	public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
	
	//public ResponseEntity<Utente> login(@RequestBody Utente utente) {
		
		//System.out.println("si sta provamdo a loggare "+ username + " con password:  " + password);
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
	
	@PostMapping("/loginDipvvf")
	public ResponseEntity<?> loginDipvvf(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
	
	//public ResponseEntity<Utente> login(@RequestBody Utente utente) {
		
		System.out.println("si sta provamdo a loggare "+ username + " con password:  " + password);
		
		String utente = aDAuthenticator.authenticateUser(username+"@dipvvf.it", password);
		Utente utenteLoggato= null;
		
		System.out.println("stringa utente:" +utente);

		
		if(utente.contains("@dipvvf.it")) {
			System.out.println("Mi sono loggato");
			//questo controlla semplicemente che l'utente sia nel database locale, se non è presente bisogna aggiungerlo
			utenteLoggato= loginService.validaLoginDipvvf(username);
			if(utenteLoggato!=null) {
				return new ResponseEntity<>(utenteLoggato, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
			}
			
		
		}else {
			
			System.out.println("Si sta provando a loggare personale.verona e username: "+ username);
			if(username.contentEquals("personale.verona") || username.contentEquals("admin.admin") || username.contentEquals("ferie.ferie") ) {
				System.out.println("Sono dentro l'if");

				utenteLoggato= loginService.validaLogin(username, password);
				if(utenteLoggato!=null) {
					System.out.println("Si è loggato personale.verona");
					return new ResponseEntity<>(utenteLoggato, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
				}
				
				
			}
			
			System.out.println("NON mi sono loggato");
			return new ResponseEntity<>("LOGIN FALLITO", HttpStatus.NOT_FOUND);
		}
		
		
		
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
}




