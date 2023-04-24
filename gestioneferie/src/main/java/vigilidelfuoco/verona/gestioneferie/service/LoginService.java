package vigilidelfuoco.verona.gestioneferie.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class LoginService {

	private final UtenteRepo utenteRepo;
	private boolean loginSuccess= false;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public LoginService(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();

	}
	
	public boolean validaLogin(Utente utenteLoggato) {
		
		
		Utente utenteInRepo = utenteRepo.findByaccountDipvvf(utenteLoggato.getAccountDipvvf());
		if(utenteInRepo!= null) {
			//String encoded_password= bCryptPasswordEncoder.encode(utenteLoggato.getPassword());
			//encoded_password.equals(utenteInRepo.getPassword())
			
			
			if(bCryptPasswordEncoder.matches(utenteLoggato.getPassword(), utenteInRepo.getPassword())) {
				loginSuccess=true;
			}else {
				System.out.println("utente diverso da null ma non corrispondono pass e username");
				System.out.println("utente loggato:"+ utenteLoggato.getPassword());
				System.out.println("utente in repo:"+ utenteInRepo.getPassword() );

				loginSuccess= false;
			}
			
			
		}else {
			System.out.println("utente non presente nel db");
		}
		return loginSuccess;
	}
}
