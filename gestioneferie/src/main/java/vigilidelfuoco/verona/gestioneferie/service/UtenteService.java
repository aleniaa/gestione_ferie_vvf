package vigilidelfuoco.verona.gestioneferie.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.model.UtenteRuolo;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;
import vigilidelfuoco.verona.gestioneferie.service.interfaces.IUtenteService;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class UtenteService implements IUtenteService {

	private final UtenteRepo utenteRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UtenteService(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	public Utente aggiungiUtente(Utente utente){
		utente.setCodiceUtente(UUID.randomUUID().toString());
		utente.setAccountDipvvf(utente.getNome() + "." +utente.getCognome());

		String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
		utente.setPassword(encoded_password);
		
		utente.setAccountDipvvf(utente.getNome() + "." +utente.getCognome());
		return utenteRepo.save(utente);
	}

	public List<Utente> trovaUtenti(){
		return utenteRepo.findAll();
	}

	public Utente aggiornaUtente(Utente utente) {
		Utente utenteDaModificare =  utenteRepo.findUtenteById(utente.getId());
		if(isNull(utenteDaModificare)){
			throw new IllegalStateException("Utente non trovato");
		}
		String passUtenteDaModificare = utenteDaModificare.getPassword();

		//se la pass è stata modificata allora la cripto
		if(!utente.getPassword().equals(passUtenteDaModificare)) {
			String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
			utente.setPassword(encoded_password);
			System.out.println("Password modificata");
		}

		utente.setAccountDipvvf(utente.getNome() + "." +utente.getCognome());
		return utenteRepo.save(utente);
	}
	
	public Utente findUtenteById(Long id) {
		return utenteRepo.findUtenteById(id);
	}
	
	public List<Utente> findUtenteByRuolo(UtenteRuolo ruolo) {
        return utenteRepo.findUtenteByRuolo(ruolo.toString());
	}

	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deleteUtenteById(Long id) {
		utenteRepo.deleteUtenteById(id);
	}
	
	public boolean checkUtenteIfExists(Utente utente) {
		Utente userByAccountDipvvf = utenteRepo.findByaccountDipvvf(utente.getAccountDipvvf());
		Utente userByEmailVigilfuoco = utenteRepo.findUtenteByemailVigilfuoco(utente.getEmailVigilfuoco());
		if(userByAccountDipvvf!=null || userByEmailVigilfuoco!=null) {
			return true;
		}
		return false;
	}
	
	public boolean changePass(String oldPass, String newPass, Long idUtenteLoggato) {
		
		boolean matchingPass= false;
		Utente utenteLoggato= utenteRepo.findUtenteById(idUtenteLoggato);
		if(utenteLoggato!=null) {
			if(bCryptPasswordEncoder.matches(oldPass, utenteLoggato.getPassword())) {
				String encoded_password= bCryptPasswordEncoder.encode(newPass);
				utenteLoggato.setPassword(encoded_password);
				utenteLoggato.setPasswordChanged(1);
				utenteRepo.save(utenteLoggato);
				matchingPass= true;
			}
		}
		return matchingPass;
	}
	
	public boolean checkPasswordFirstAccess(Long idUtenteLoggato) {
		
		boolean isDefaultPassw= false;
		
		Utente utenteLoggato= utenteRepo.findUtenteById(idUtenteLoggato);
		System.out.println(utenteLoggato.toString());
		if(utenteLoggato!=null) {
			System.out.println("l'utente è diverso da null");
			System.out.println("password changed è = "+ utenteLoggato.getPasswordChanged());
			if(utenteLoggato.getPasswordChanged()==0) {
				isDefaultPassw= true;
			}else {
				System.out.println("la password non è quella di default");
				
			}
		}
		return isDefaultPassw;
	}


	
	
	
}
