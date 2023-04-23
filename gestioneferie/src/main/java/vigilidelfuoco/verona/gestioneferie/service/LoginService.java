package vigilidelfuoco.verona.gestioneferie.service;

import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class LoginService {

	private final UtenteRepo utenteRepo;
	private boolean loginSuccess= false;
	
	public LoginService(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
	}
	
	public boolean validaLogin(Utente utenteLoggato) {
		
		
		Utente utenteInRepo = utenteRepo.findByaccountDipvvf(utenteLoggato.getAccountDipvvf());
		if(utenteInRepo!= null) {
			if(utenteLoggato.getPassword().equals(utenteInRepo.getPassword())) {
				loginSuccess=true;
			}else {
				System.out.println("utente diverso da null ma non corrispondono pass e username");
				System.out.println("utente loggato:"+ utenteLoggato.getPassword() );
				System.out.println("utente in repo:"+ utenteInRepo.getPassword() );

				loginSuccess= false;
			}
			
			
		}else {
			System.out.println("utente non presente nel db");
		}
		return loginSuccess;
	}
}
