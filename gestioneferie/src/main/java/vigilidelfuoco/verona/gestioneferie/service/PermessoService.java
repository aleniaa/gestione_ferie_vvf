package vigilidelfuoco.verona.gestioneferie.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.repo.FileRepo;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.exception.UserNotFoundException;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;
import vigilidelfuoco.verona.gestioneferie.model.FileEntity;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class PermessoService {
	
	private final PermessoRepo permessoRepo;
	private final UtenteRepo utenteRepo;
	private final  FileRepo fileRepo;
	private final FileStorageService fileService;
	
	@Autowired
	public PermessoService(PermessoRepo permessoRepo, UtenteRepo utenteRepo, FileRepo fileRepo, FileStorageService fileService ) {
		super();
		this.permessoRepo = permessoRepo;
		this.utenteRepo= utenteRepo;
		this.fileRepo= fileRepo;
		this.fileService= fileService;
	}
	
	public List<Permesso> trovaPermessi(){
		return permessoRepo.findAll();
	}
	
	public List<Permesso> trovaTipoCongedo(){
		return permessoRepo.findPermessoBytipoPermesso("congedo");
	}
	
	public List<Permesso> findPermessoByStatus(int status){
		return permessoRepo.findPermessoByStatus(status);
	}
	
	public List<Permesso> findPermessoRichiedenteByStatus(int status, Long idUtente){
		return permessoRepo.findPermessoByStatusAndIdUtenteRichiedenteOrderByDataApprovazioneDesc(status, idUtente);
	}
	
	public List<Permesso> findPermessoRichiedente(Long idUtente){
		return permessoRepo.findPermessoByIdUtenteRichiedenteOrderByDataApprovazioneDesc(idUtente);
	}
	
	public List<Permesso> findPermessoApprovatoreByStatus(int status, Long idUtente){
		return permessoRepo.findPermessoByStatusAndIdUtenteApprovazioneOrIdUtenteApprovazioneDueOrderByDataApprovazioneDesc(status, idUtente, idUtente);

		//return permessoRepo.findPermessoByStatusAndIdUtenteApprovazioneOrderByDataApprovazioneDesc(status, idUtente);
	}
	
	public List<Permesso> findPermessoApprovatore(Long idUtente){
		return permessoRepo.findPermessoByIdUtenteApprovazioneOrIdUtenteApprovazioneDueOrderByDataApprovazioneDesc(idUtente, idUtente);

		//return permessoRepo.findPermessoByStatusAndIdUtenteApprovazioneOrderByDataApprovazioneDesc(status, idUtente);
	}
	
	public Permesso findPermessoById(Long id) {
		return permessoRepo.findPermessoById(id)
				.orElseThrow(() -> new UserNotFoundException("Permesso con id "+id +" non trovato"));
	}
	
	
	
//	public List<Permesso> getFilteredPermessi(Permesso permesso){
//		
//		List<Permesso> permessiTot= new ArrayList<Permesso>();
//		
//		Permesso filtroPermesso= new Permesso();
//		System.out.println("filtro permesso iniziale : "+filtroPermesso.toString());
//		
//		filtroPermesso.setDataApprovazione(permesso.getDataApprovazione());
//		filtroPermesso.setIdUtenteApprovazione(permesso.getIdUtenteApprovazione());
//		filtroPermesso.setIdUtenteRichiedente(permesso.getIdUtenteRichiedente()); //per l'id del richiedente
//		filtroPermesso.setStatus(3);
// 
//		
//		if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
//			System.out.println("tipo permesso uguale stringa vuota");
//			filtroPermesso.setTipoPermesso(null);
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			permessiTot= permessoRepo.findAll(permessoExample);
//			
//			//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();
//
//			
//		}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
//			
//			filtroPermesso.setTipoPermesso(null);
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			permessiTot= permessoRepo.findAltriPermessi(permessoExample);
//			
//		}else { // se il permesso è congedo o recupero ore o permeso breve
//			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")) {
//				filtroPermesso.setTipoPermesso(null);
//
//			}else {
//				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
//
//			}
//			
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			permessiTot= permessoRepo.findAll(permessoExample);
//
//		}
//		
//
//
//		System.out.println(filtroPermesso.toString());
//		System.out.println("permessi totali: "+permessiTot);
//		
//		return permessiTot;
//	} 
	
//	public List<Permesso> getFilteredPermessi(Permesso permesso, String dataAssenza, int status){
//	public List<Permesso> getFilteredPermessi(Permesso permesso, String dataAssenza){
//		
//		System.out.println("l'id dell'utente approvatore due è:" + permesso.getIdUtenteApprovazioneDue());
//		List<Permesso> permessiTot= new ArrayList<Permesso>();
//		List<Permesso> permessiAppDue= new ArrayList<Permesso>();
//		
//		Permesso filtroPermesso= new Permesso();
//		//System.out.println("filtro permesso iniziale : "+filtroPermesso.toString());
//		System.out.println("tipo permesso : "+ permesso.getTipoPermesso());
//		
//		filtroPermesso.setDataApprovazione(permesso.getDataApprovazione());
//		filtroPermesso.setIdUtenteApprovazione(permesso.getIdUtenteApprovazione());
//		
//		filtroPermesso.setIdUtenteRichiedente(permesso.getIdUtenteRichiedente()); //per l'id del richiedente
//		filtroPermesso.setStatus(null);
// 
//		
//		if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
//			System.out.println("tipo permesso uguale stringa vuota");
//			filtroPermesso.setTipoPermesso(null);
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			permessiTot= permessoRepo.findAll(permessoExample);
//			System.out.println("filtro permesso dentro stringa vuota : "+permessoExample.toString());
//			System.out.println("permessi totali id 1: "+permessiTot);
//			//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();
//
//			
//		}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
//			System.out.println("tipo permesso è ALTRI PERMESSI");
//			filtroPermesso.setTipoPermesso(null);
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			permessiTot= permessoRepo.findAltriPermessiByOrderByDataApprovazioneDesc(permessoExample);
//			for (Permesso permessoprova : permessiTot) {
//			    System.out.println("Status: " + permessoprova.getStatus());
//			    System.out.println("Descrizione: " + permessoprova.getTipoPermesso());
//			    //System.out.println("Utente approvazione 1: " + permessoprova.getUtenteApprovazione().getAccountDipvvf().toString());
//			    //System.out.println("Utente approvazione 2: " + permessoprova.getUtenteApprovazioneDue().getAccountDipvvf().toString());
//
//			    // Print other properties as needed
//			}
//			
//		}else { // se il permesso è congedo o recupero ore o permeso breve
//			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")) {
//				filtroPermesso.setTipoPermesso(null);
//
//			}else {
//				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
//
//			}
//			
//			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//			
//			
//			permessiTot = permessoRepo.findAll(permessoExample);
//
//		}
//		
//		if(permesso.getIdUtenteApprovazioneDue()!=null) {
//			filtroPermesso.setIdUtenteApprovazione(null);
//			filtroPermesso.setIdUtenteApprovazioneDue(permesso.getIdUtenteApprovazioneDue());
//
//			System.out.println("Sono dentro il secondo if l'id dell'utente approvatore due è:" + permesso.getIdUtenteApprovazioneDue());
//			
//			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
//				System.out.println("tipo permesso uguale stringa vuota");
//				filtroPermesso.setTipoPermesso(null);
//				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//				
//				System.out.println("dentro stringa vuota secondo id: "+filtroPermesso.toString());
//				permessiAppDue= permessoRepo.findAll(permessoExample);
//				
//				
//				//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();
//
//				
//			}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
//				
//				filtroPermesso.setTipoPermesso(null);
//				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//				permessiAppDue= permessoRepo.findAltriPermessiByOrderByDataApprovazioneDesc(permessoExample);
//				
//			}else if(permesso.getTipoPermesso()!=null && (permesso.getTipoPermesso().contains("Malattia") || permesso.getTipoPermesso().contains("Terapia") ) ) {	
//				
//				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
//				System.out.println("Il tipo permesso dentro malattia o terapia è " + permesso.getTipoPermesso());
//				
//				
//			}else { // se il permesso è congedo o recupero ore o permeso breve
//				if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")  ) {
//				//if(permesso.getTipoPermesso()!=null && (permesso.getTipoPermesso().equals("tutti i permessi") || permesso.getTipoPermesso().equals("Malattia") ) ) {	
//					filtroPermesso.setTipoPermesso(null);
//
//				}else {
//					filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
//
//				}
//				
//				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
//				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
//				
//				
//				permessiAppDue= permessoRepo.findAll(permessoExample);
//				
//				
//			}
//		}
//		
//		if(!permessiAppDue.isEmpty()) {
//			System.out.println("PermessiDue non è vuoto");
//
//			permessiTot.addAll(permessiAppDue);
//		}
//
//		List<Permesso> toRemove = new ArrayList<>();
//		//System.out.println(filtroPermesso.toString());
//		if(!dataAssenza.equals("") && dataAssenza!=null) {
//			
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			LocalDate dataRicercaAssenza = LocalDate.parse(dataAssenza, formatter);
//			System.out.println("sono dentro l'if e data assenza è:" + dataRicercaAssenza.toString() );
//			for(Permesso permessoUno : permessiTot) {
//				if(!(dataRicercaAssenza.isAfter(permessoUno.getDataInizio().minusDays(1)) && dataRicercaAssenza.isBefore(permessoUno.getDataFine().plusDays(1)))) {
//					toRemove.add(permessoUno);
//					System.out.println("Questo permesso va rimosso");
//
//				}
//			}
//			
//			permessiTot.removeAll(toRemove);
//		}
//		toRemove.clear();
//		
//		
//		for (Permesso permessoDaRimuovere : permessiTot) { //rimuovere i permessi con status 0 che non sono ancora approvati
//			if(permessoDaRimuovere.getStatus()==0) {
//				toRemove.add(permessoDaRimuovere);
//			}
//		}
//		
//		permessiTot.removeAll(toRemove);
//		
//		Collections.sort(permessiTot, Comparator.comparing(Permesso::getDataApprovazione).reversed());
//		
//		for (Permesso permessoprova : permessiTot) {
//		    System.out.println("Sono il print finale Status: " + permessoprova.getStatus());
//		    System.out.println("Descrizione: " + permessoprova.getTipoPermesso());
//		    // Print other properties as needed
//		}
//		return permessiTot;
//	} 
	

	
	public Permesso aggiungiPermesso(Permesso permesso, Long idUtenteLoggato){
		
		LocalDate dataRichiesta = LocalDate.now();
		permesso.setDataRichiestaPermesso(dataRichiesta);
		
		if(permesso.getDataInizio()!=null && permesso.getDataFine()!=null){
			System.out.println("sono dentro permesso service setTotGiorni");

			permesso.setTotGiorni(permesso.getTotGiorni());
		}
		
		if(permesso.getTipoPermesso().contains("Malattia") || permesso.getTipoPermesso().contains("salvavita")) { // non deve essere approvato
			permesso.setStatus(3); // va direttamente alla conferma dell'ufficio personale
			LocalDate dataApprovazione = LocalDate.now();
			permesso.setDataApprovazione(dataApprovazione);
		}else {
			permesso.setStatus(0);
			System.out.println("id utente approvazione = "+ permesso.getIdUtenteApprovazione());
			System.out.println("id utente approvazione DUE = "+ permesso.getIdUtenteApprovazioneDue());
			System.out.println("id utente richiedente = "+ idUtenteLoggato);
			Utente utenteApprovazione = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazione());
			Utente utenteApprovazioneDue = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazioneDue());
			Utente utenteRichiedente = utenteRepo.findUtenteByIdsenzaoptional(idUtenteLoggato);
//			Utente utenteApprovazione = permessoRepo.findUtenteByIdUtenteApprovazione();
			
			System.out.println("utente richiedente trovato: = "+ utenteRichiedente.toString());
			System.out.println("utente approvatore 2 trovato: = "+ utenteApprovazioneDue);
			System.out.println("utente utenteApprovazione trovato: = "+ utenteApprovazione.toString());
			
			
			//permesso.setUtenteRichiedente(utenteRichiedente);
			permesso.setIdUtenteRichiedente(idUtenteLoggato);
			permesso.setUtenteApprovazione(utenteApprovazione);
			System.out.println(" Sono dentro aggiungi permesso service e idutenteloggato è"+ idUtenteLoggato);
			permesso.setUtenteApprovazioneDue(utenteApprovazioneDue);
			System.out.println(" Sono dentro aggiungi permesso service utente approvazione DUE è"+ permesso.getUtenteApprovazioneDue());
			System.out.println("utente richiedente è"+ permesso.getUtenteRichiedente());
			
		}

		
		//permessoRepo.save(permesso);
		return permessoRepo.save(permesso);
	
	}
	
	public boolean checkStatusPermessoPersonale(int status) {
		boolean aggiornare= true;
		
		int[] statusPersonale = new int[]{6,7,8,9,30,31};
		for (int element : statusPersonale) {
			if (status == element) { // se il permesso ha uno di quegil stati
				aggiornare= false; // il permesso non si deve modificare perchè già approvato o rifiutato
			}
		}
		return aggiornare;
	}
	
	public Permesso aggiornaPermessoPersonale(Permesso permesso) {
		
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		System.out.println("IL PERMESSO DA AGGIORNARE HA STATUS: "+permessoDaAggiornare.getStatus().toString());
//		switch(permessoDaAggiornare.getStatus()) {  LO SWITCH NON GLI PIACE, A MENO CHE NON SI DEBBANO FARE TUTTI I CASI POSSIBILI
//			case 1: // peremsso approvato da approvatore 1
//				permessoDaAggiornare.setStatus(6);
//			case 2:  // peremsso approvato da approvatore 2
//				permessoDaAggiornare.setStatus(8);
//			case 3:  // malattia
//				permessoDaAggiornare.setStatus(6);				
//			default:
//				System.out.print("Errore nello switch");
//			
//		}
		
		boolean aggiornare= true;
		aggiornare = checkStatusPermessoPersonale(permessoDaAggiornare.getStatus());
		
		if(aggiornare) {
			if(permessoDaAggiornare.getStatus()==1) { // lo ha approvato l'approvatore 1
				permessoDaAggiornare.setStatus(6);
				System.out.print("STATUS 1");
			}
			else if(permessoDaAggiornare.getStatus()==2) { // lo ha approvato l'approvatore 2
				permessoDaAggiornare.setStatus(8);
				System.out.print("STATUS 2");

			}else if(permessoDaAggiornare.getStatus()==3) { 
				permessoDaAggiornare.setStatus(31); // malattia approvata da personale
				System.out.print("STATUS 3");

			}		
			
			LocalDate dataApprovazione = LocalDate.now();
			permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		}
		

		

		return permessoRepo.save(permessoDaAggiornare);
	}
	
	
	public void sendEmailPermessoModificato() {
		// Sender's email address
        String from = "informatica.verona@vigilfuoco.it";
        // Sender's password
        String password = "Ict-Nas2024";
        // Receiver's email address
        String to = "ilenia.mannino@vigilfuoco.it";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-s.vigilfuoco.it");
        props.put("mail.smtp.port", "465");

        // Get the Session object
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("Gestione Ferie"));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Email from Java");

            // Set the actual message
            message.setText("Hello, this is a test email from Java!");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
	}
	
	public boolean checkStatusPermessoApprovatoreFerie(int status) {
		boolean aggiornare= true;
		System.out.println("DENTRO CHECKSTATUS	 LO STATUS è :" + status);

		int[] statusFerie = new int[]{1,2,4,5};
		for (int element : statusFerie) {
			if (status == element) { // se il permesso ha uno di quegil stati
				System.out.println("Lo status è 1,2,4 o 5");
				aggiornare= false; // il permesso non si deve modificare perchè già approvato o rifiutato
				break;
			}else {
				System.out.println("Lo status NON è 1,2,4 o 5");

			}
		}
		return aggiornare;
	}
	
	
	// qua basta che uno solo approvi o rifiuti il permesso
	public Permesso aggiornaStatusPermesso2(Permesso permesso, Long idApprovatore) {
		
//		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
//		
//		permessoDaAggiornare.setStatus(statusPermesso);
//
//		LocalDate dataApprovazione = LocalDate.now();
//		permessoDaAggiornare.setDataApprovazione(dataApprovazione);
//		
//
//		return permessoRepo.save(permessoDaAggiornare);
		
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		boolean aggiornare= true;
		aggiornare = checkStatusPermessoApprovatoreFerie(permessoDaAggiornare.getStatus());
		
		if(aggiornare) {
			
			if(permessoDaAggiornare.getUtenteApprovazioneDue()==null) { // se c'è solo l'approvatore uno che deve approvare: 
				permessoDaAggiornare.setStatus(1); // approvato definitivamente
			}
			
			if(permessoDaAggiornare.getUtenteApprovazioneDue()!=null) {

				if(idApprovatore.equals(permessoDaAggiornare.getIdUtenteApprovazioneDue())){ // se l'approvatore loggato è il 2:
					permessoDaAggiornare.setStatus(2); //approvato da approvatore 2
					System.out.println("sono dentro caso 0 e l'approvatore è il 2");
				}else { //se è l'1
					permessoDaAggiornare.setStatus(1); //approvato da approvatore 1
					System.out.println("sono dentro caso 0 dentro l'else");
				};

			}
			
			
			this.sendEmailPermessoModificato();
			LocalDate dataApprovazione = LocalDate.now();
			permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		}
		

		

		return permessoRepo.save(permessoDaAggiornare);
		
	}
		

		
	
	
	// vengono considerati entrambi gli approvatori e il permesso deve essere approvato da entrambi per risultare approvato definitivamente
	public Permesso aggiornaStatusPermesso(Permesso permesso, Long idApprovatore) {
		
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		
		if(permessoDaAggiornare.getUtenteApprovazioneDue()==null) { // se c'è solo l'approvatore uno che deve approvare: 
			permessoDaAggiornare.setStatus(3); // approvato definitivamente
		}
		System.out.println("id approvatore loggato è"+ idApprovatore);
		System.out.println("id approvatore due del permesso è"+ permessoDaAggiornare.getIdUtenteApprovazioneDue());
		if(idApprovatore.equals(permessoDaAggiornare.getIdUtenteApprovazioneDue())) {
			System.out.println("che cazzoooo è verooo");

		}
		
		if(permessoDaAggiornare.getUtenteApprovazioneDue()!=null) {
			switch(permessoDaAggiornare.getStatus()) {
				case 0:
					if(idApprovatore.equals(permessoDaAggiornare.getIdUtenteApprovazioneDue())){ // se l'approvatore loggato è il 2:
						permessoDaAggiornare.setStatus(2); //approvato da approvatore 2
						System.out.println("sono dentro caso 0 e l'approvatore è il 2");
					}else { //se è l'1
						permessoDaAggiornare.setStatus(1); //approvato da approvatore 1
						System.out.println("sono dentro caso 0 dentro l'else");
					};
					break;
				case 1:
					permessoDaAggiornare.setStatus(3);
					break;
				case 2:
					permessoDaAggiornare.setStatus(3);
					break;
				default: System.out.println("qualce condizione dello status non va");
			}
		}
		
		
		
		LocalDate dataApprovazione = LocalDate.now();
		permessoDaAggiornare.setDataApprovazione(dataApprovazione);
		

		return permessoRepo.save(permessoDaAggiornare);
	}
	
	public Permesso respingiPermesso(String note, Permesso permesso, Long idApprovatore) {
		
		System.out.println("sono dentro aggiornastatuspermesso service e le note sono : "+ note);
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		boolean aggiornare= true;
		aggiornare = checkStatusPermessoApprovatoreFerie(permessoDaAggiornare.getStatus());
		
		if(aggiornare) {
			
			if(permessoDaAggiornare.getUtenteApprovazioneDue()==null) { // se c'è solo l'approvatore uno che deve approvare: 
				permessoDaAggiornare.setStatus(4); // respinto da approvatore 1
			}
			
			if(permessoDaAggiornare.getUtenteApprovazioneDue()!=null) {
				if(idApprovatore.equals(permessoDaAggiornare.getIdUtenteApprovazioneDue())){ // se l'approvatore loggato è il 2:
					permessoDaAggiornare.setStatus(5); //respinto da approvatore 2
				}else { //se è l'1
					permessoDaAggiornare.setStatus(4); //respinto da approvatore 1
				}
			}
			
			Utente utenteCheHaRespinto= utenteRepo.findUtenteByIdsenzaoptional(idApprovatore);
			LocalDate dataApprovazione = LocalDate.now();
			permessoDaAggiornare.setDataApprovazione(dataApprovazione);
			permessoDaAggiornare.setNote("La richiesta di permesso è stata respinta da "+ 
					utenteCheHaRespinto.getNome()+ " " + utenteCheHaRespinto.getCognome()
					+ ".<br>Motivo: "+note);
		}

		return permessoRepo.save(permessoDaAggiornare);
	}
	
	public Permesso respingiPermessoPersonale(String note, Permesso permesso, Long idApprovatore) {
		
		System.out.println("sono dentro aggiornastatuspermesso service e le note sono : "+ note);
		Permesso permessoDaAggiornare = permessoRepo.findPermessoByIdsenzaoptional(permesso.getId());
		
		boolean aggiornare= true;
		aggiornare = checkStatusPermessoPersonale(permessoDaAggiornare.getStatus());
		
		if(aggiornare) {
			if(permessoDaAggiornare.getStatus()==1) {
				permessoDaAggiornare.setStatus(7); //permsso approvato da approvatore 1 ma respinto da personale

			}else if(permessoDaAggiornare.getStatus()==2){ //permsso approvato da approvatore 2 ma respinto da personale
				permessoDaAggiornare.setStatus(9);
				
			}else if(permessoDaAggiornare.getStatus()==3){ //malattia
				permessoDaAggiornare.setStatus(30); //malattia respinta da personale
				
			}
			
			
			Utente utenteCheHaRespinto= utenteRepo.findUtenteByIdsenzaoptional(idApprovatore);
			LocalDate dataApprovazione = LocalDate.now();
			permessoDaAggiornare.setDataApprovazione(dataApprovazione);
			permessoDaAggiornare.setNote("La richiesta di permesso è stata respinta da "+ 
					utenteCheHaRespinto.getNome()+ " " + utenteCheHaRespinto.getCognome()
					+ ".<br>Motivo: "+note);
		}
		

		
		
		return permessoRepo.save(permessoDaAggiornare);
	}
	
	
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public boolean deletePermessoById(Long id) throws IOException {
		
		List<FileEntity> filePermesso= new ArrayList<FileEntity>();
		
		Permesso permessoDaCancellare= permessoRepo.findPermessoByIdsenzaoptional(id);
		boolean siPuoCancellare= false;
		switch(permessoDaCancellare.getStatus()) {
			case 0:
				siPuoCancellare= true;
			case 1:
				siPuoCancellare= true;
			case 2:
				siPuoCancellare= true;
			case 3:
				siPuoCancellare= true;				
		}
		
		if(siPuoCancellare) {
			filePermesso= fileRepo.findByIdPermessoAssociato(id);
			
			
			
			if(!filePermesso.isEmpty()) {
				for (FileEntity file : filePermesso) {
					fileService.deleteFile(id, file.getFilename());
				}	
			}

			
			fileRepo.deleteByIdPermessoAssociato(id);
			permessoRepo.deletePermessoById(id);
		}
		
		return siPuoCancellare;
	}

	
	public List<Permesso> getFilteredPermessiNew(String dataAssenza, String tipoPermesso, Long utenteRichiedente, String dataApprovazione, Long utenteApprovatore){
		

		System.out.println("l'id dell'utente approvatore è:" + utenteApprovatore);
		System.out.println("dataAssenza è:" + dataAssenza);
		System.out.println("tipoPermesso è:" + tipoPermesso);
		System.out.println("dataApprovazione è:" + dataApprovazione);
		System.out.println("l'id dell'utenteRichiedente è:" + utenteRichiedente);
		List<Permesso> permessiTot= new ArrayList<Permesso>();
		
		Permesso filtroPermesso= new Permesso();
		
		// UTENTE APPROVATORE E RICHIEDENTE 
		
		if(utenteRichiedente==-1) {
			filtroPermesso.setIdUtenteRichiedente(null);
		}else {
			filtroPermesso.setIdUtenteRichiedente(utenteRichiedente);
		}
		
		if(utenteApprovatore==-1) {
			filtroPermesso.setIdUtenteApprovazione(null);
		}else {
			filtroPermesso.setIdUtenteApprovazione(utenteApprovatore);
		}
		
		// STATUS
		
		filtroPermesso.setStatus(null);
		
		// TIPO PERMESSO
		
		if(tipoPermesso.equals("tutti i permessi"))
			filtroPermesso.setTipoPermesso(null);
		else {
			filtroPermesso.setTipoPermesso(tipoPermesso);
		}
		
		//DATA APPROVAZIONE
		
		if(dataApprovazione.equals("")) {
			filtroPermesso.setDataApprovazione(null);
		}else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dataRicercaApprovazione = LocalDate.parse(dataApprovazione, formatter);
			filtroPermesso.setDataApprovazione(dataRicercaApprovazione);
		}
		
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
		Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
		permessiTot= permessoRepo.findAll(permessoExample);
		
		//DATA ASSENZA
		
		List<Permesso> toRemove = new ArrayList<>();
		
		System.out.println("data assenza è " + dataAssenza);
		
		if(!dataAssenza.equals("") && dataAssenza!=null) {
			System.out.println(" SONO DENTRO L'IF DATAA ASSENZA data assenza è " + dataAssenza);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dataRicercaAssenza = LocalDate.parse(dataAssenza, formatter);
			System.out.println("sono dentro l'if e data assenza è:" + dataRicercaAssenza.toString() );
			for(Permesso permessoUno : permessiTot) {
				if(!(dataRicercaAssenza.isAfter(permessoUno.getDataInizio().minusDays(1)) && dataRicercaAssenza.isBefore(permessoUno.getDataFine().plusDays(1)))) {
					toRemove.add(permessoUno);
					System.out.println("Questo permesso va rimosso");

				}
			}
			
			permessiTot.removeAll(toRemove);
		}
		
		toRemove.clear();
		
		
		for(Permesso permessoUno : permessiTot) {
			System.out.println(permessoUno.toString());
		}
		return permessiTot;
	} 
}
