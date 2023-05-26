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
	
	public Utente validaLogin(Utente utenteLoggato) {
		
		
		Utente utenteInRepo = utenteRepo.findByaccountDipvvf(utenteLoggato.getAccountDipvvf());
		if(utenteInRepo!= null) {
//			System.out.println("La password in chiaro è: "+utenteLoggato.getPassword() );
//			System.out.println("La password codificata è: "+ bCryptPasswordEncoder.encode(utenteLoggato.getPassword() ));
//			System.out.println("La password codificata nel database è: "+ utenteInRepo.getPassword());
//			
			if(bCryptPasswordEncoder.matches(utenteLoggato.getPassword(), utenteInRepo.getPassword())) {
				loginSuccess=true;
				return utenteInRepo;
			}else {
				System.out.println("Login service: utente diverso da null ma non corrispondono pass e username");
				System.out.println("utente loggato paasword:"+ utenteLoggato.getPassword());
				System.out.println("utente in repo:"+ utenteInRepo.getPassword() );

				loginSuccess= false;
				return null;
			}
			
			
		}else {
			System.out.println("utente non presente nel db");
			return null;
		}
		
	}

	public boolean checkPass(String password) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
