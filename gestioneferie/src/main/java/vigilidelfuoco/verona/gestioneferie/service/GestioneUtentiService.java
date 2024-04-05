package vigilidelfuoco.verona.gestioneferie.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
//import vigilidelfuoco.verona.gestioneferie.exception.UserAlreadyExistsException;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@AllArgsConstructor
@Service
public class GestioneUtentiService {

	private final UtenteRepo utenteRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	public GestioneUtentiService(UtenteRepo utenteRepo) {
		super();
		this.utenteRepo = utenteRepo;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}
	
	
	public void aggiungiUtente(Utente utente){
		System.out.println("sono dentro agiungi utente");
		System.out.println("sono dentro agiungi utente e la qualifica è: "+utente.getId_qualifica());
		if(utente.getId_qualifica()==null) {
			utente.setId_qualifica(Long.valueOf(999));
		}else {
			utente.setId_qualifica(utente.getId_qualifica());
		}
		
		utente.setCodiceUtente(UUID.randomUUID().toString());
		String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
		utente.setPassword(encoded_password);
		
		//utente.setAccountDipvvf();
		utenteRepo.save(utente);
		
		
	}
	
//	public Utente aggiungiPiuUtenti(List<Utente> utente){
//		for(int i=0; i<= utente.size();i++) {
//			
//		}
//		utente.setCodiceUtente(UUID.randomUUID().toString());
//		return utenteRepo.save(utente);
//		
//		
//	}
	
	public List<Utente> trovaUtenti(){
		return utenteRepo.findAll();
	}
	
	public Utente aggiornaUtente(Utente utente) {
		Utente utenteDaModificare =  utenteRepo.findUtenteByIdsenzaoptional(utente.getId());
		String passUtenteDaModificare = utenteDaModificare.getPassword();
		
		System.out.println("La password dal modal è :" + utente.getPassword());
		System.out.println("La password dal database è :" + passUtenteDaModificare);
		
//		if(utenteDaModificare!=null) {
//			
//
//			if(!bCryptPasswordEncoder.matches(utente.getPassword(), utenteDaModificare.getPassword())) {
//				String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
//				utenteDaModificare.setPassword(encoded_password);
//				utenteDaModificare.setPasswordChanged(1);
//				System.out.println("Sono dentro aggiorna utente e la password è stata modificata perchè non matchano");
//
//			}
//		}
		
		System.out.println("PASSWORD CHANGED: " + utente.getPasswordChanged());
		
		if(utente.getPassword()==null) {
			utente.setPassword(passUtenteDaModificare);
			utente.setPasswordChanged(1);
		}else
		{
			System.out.println("La password non è null e la sto cambiando");
			String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
			utente.setPassword(encoded_password);
			utente.setPasswordChanged(0);
		}
		
//		if(!utente.getPassword().equals(passUtenteDaModificare)) { //se la pass è stata modificata
//			String encoded_password= bCryptPasswordEncoder.encode(utente.getPassword());
//			utenteDaModificare.setPassword(encoded_password);
//			System.out.println("Password modificata");
//		}
			
		if(utente.getId_qualifica()==null) {
			utente.setId_qualifica(Long.valueOf(999));
		}
//			else {
//			utenteDaModificare.setId_qualifica(utente.getId_qualifica());
//		}
		
		
			
		utente.setAccountDipvvf();
		return utenteRepo.save(utente);
	}
	
	public Utente findUtenteById(Long id) {
		return utenteRepo.findUtenteById(id)
				.orElseThrow(() -> new UserNotFoundException("Utente con id "+id +" non trovato"));
	}
	
	public List<Utente> findUtenteByRuoloFerie() {
		return utenteRepo.findUtenteByRuolo("FERIE")
				.orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}
	
	public List<Utente> findUtenteByRuoloAdmin() {
		return utenteRepo.findUtenteByRuolo("ADMIN")
				.orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}
	
	public List<Utente> findUtenteByRuoloUtente() {
		return utenteRepo.findUtenteByRuolo("UTENTE")
				.orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deleteUtenteById(Long id) {
		utenteRepo.deleteUtenteById(id);
	}
	
	public boolean checkUtenteIfExists(Utente utente) {
		
		if(utenteRepo.existsUtenteByemailVigilfuoco(utente.getEmailVigilfuoco()) || utenteRepo.existsUtenteByAccountDipvvf(utente.getAccountDipvvf())) {
			return false;
		}else {
			 System.out.println(" sono dentro checutenteifexists and L'utente non esiste");
			 return true;
		}
		
		
	}
	
	public boolean changePass(String oldPass, String newPass, Long idUtenteLoggato) {
		
		boolean matchingPass= false;
		Utente utenteLoggato= utenteRepo.findUtenteByIdsenzaoptional(idUtenteLoggato);
		System.out.println(utenteLoggato.toString());
		if(utenteLoggato!=null) {
			System.out.println("l'utente è diverso da null");

			if(bCryptPasswordEncoder.matches(oldPass, utenteLoggato.getPassword())) {
				String encoded_password= bCryptPasswordEncoder.encode(newPass);
				utenteLoggato.setPassword(encoded_password);
				utenteLoggato.setPasswordChanged(1);
				matchingPass= true;
			}else {
				System.out.println("le password non corrisponono");
				
			}
		}
		utenteRepo.save(utenteLoggato);
		return matchingPass;
	}
	
	public boolean checkPasswordFirstAccess(Long idUtenteLoggato) {
		
		boolean isDefaultPassw= false;
		
		Utente utenteLoggato= utenteRepo.findUtenteByIdsenzaoptional(idUtenteLoggato);
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
