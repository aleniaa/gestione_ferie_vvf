package vigilidelfuoco.verona.gestioneferie.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.UUID;

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
	
	public List<Permesso> getFilteredPermessi(Permesso permesso, String dataAssenza){
		
		System.out.println("l'id dell'utente approvatore due è:" + permesso.getIdUtenteApprovazioneDue());
		List<Permesso> permessiTot= new ArrayList<Permesso>();
		List<Permesso> permessiAppDue= new ArrayList<Permesso>();
		
		Permesso filtroPermesso= new Permesso();
		System.out.println("filtro permesso iniziale : "+filtroPermesso.toString());
		
		filtroPermesso.setDataApprovazione(permesso.getDataApprovazione());
		filtroPermesso.setIdUtenteApprovazione(permesso.getIdUtenteApprovazione());
		
		filtroPermesso.setIdUtenteRichiedente(permesso.getIdUtenteRichiedente()); //per l'id del richiedente
		filtroPermesso.setStatus(3);
 
		
		if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
			System.out.println("tipo permesso uguale stringa vuota");
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAll(permessoExample);
			System.out.println("filtro permesso dentro stringa vuota : "+permessoExample.toString());
			System.out.println("permessi totali id 1: "+permessiTot);
			//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();

			
		}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
			
			filtroPermesso.setTipoPermesso(null);
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			permessiTot= permessoRepo.findAltriPermessiByOrderByDataApprovazioneDesc(permessoExample);
			
		}else { // se il permesso è congedo o recupero ore o permeso breve
			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")) {
				filtroPermesso.setTipoPermesso(null);

			}else {
				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());

			}
			
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
			Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
			
			
			permessiTot = permessoRepo.findAll(permessoExample);

		}
		
		if(permesso.getIdUtenteApprovazioneDue()!=null) {
			filtroPermesso.setIdUtenteApprovazione(null);
			filtroPermesso.setIdUtenteApprovazioneDue(permesso.getIdUtenteApprovazioneDue());

			System.out.println("Sono dentro il secondo if l'id dell'utente approvatore due è:" + permesso.getIdUtenteApprovazioneDue());
			
			if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("")) {
				System.out.println("tipo permesso uguale stringa vuota");
				filtroPermesso.setTipoPermesso(null);
				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
				
				System.out.println("dentro stringa vuota secondo id: "+filtroPermesso.toString());
				permessiAppDue= permessoRepo.findAll(permessoExample);
				
				
				//permessiTot= permessoRepo.findAllByOrderByDataApprovazioneDesc();

				
			}else if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("altri permessi")) {
				
				filtroPermesso.setTipoPermesso(null);
				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
				permessiAppDue= permessoRepo.findAltriPermessiByOrderByDataApprovazioneDesc(permessoExample);
				
			}else if(permesso.getTipoPermesso()!=null && (permesso.getTipoPermesso().contains("Malattia") || permesso.getTipoPermesso().contains("Terapia") ) ) {	
				
				filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());
				System.out.println("Il tipo permesso dentro malattia o terapia è " + permesso.getTipoPermesso());
				
				
			}else { // se il permesso è congedo o recupero ore o permeso breve
				if(permesso.getTipoPermesso()!=null && permesso.getTipoPermesso().equals("tutti i permessi")  ) {
				//if(permesso.getTipoPermesso()!=null && (permesso.getTipoPermesso().equals("tutti i permessi") || permesso.getTipoPermesso().equals("Malattia") ) ) {	
					filtroPermesso.setTipoPermesso(null);

				}else {
					filtroPermesso.setTipoPermesso(permesso.getTipoPermesso());

				}
				
				ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
				Example<Permesso> permessoExample = Example.of(filtroPermesso, matcher);
				
				
				permessiAppDue= permessoRepo.findAll(permessoExample);
				
				
			}
		}
		
		if(!permessiAppDue.isEmpty()) {
			System.out.println("PermessiDue non è vuoto");

			permessiTot.addAll(permessiAppDue);
		}

		//System.out.println(filtroPermesso.toString());
		if(!dataAssenza.equals("") && dataAssenza!=null) {
			List<Permesso> toRemove = new ArrayList<>();
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
		
		
		Collections.sort(permessiTot, Comparator.comparing(Permesso::getDataApprovazione).reversed());
		
		return permessiTot;
	} 
	

	
	public Permesso aggiungiPermesso(Permesso permesso, Long idUtenteLoggato){
		
	
		if(permesso.getDataInizio()!=null && permesso.getDataFine()!=null){
			System.out.println("sono dentro permesso service setTotGiorni");

			permesso.setTotGiorni(permesso.getTotGiorni());
		}
		
		if(permesso.getTipoPermesso().contains("Malattia") || permesso.getTipoPermesso().contains("salvavita")) { // non deve essere approvato
			permesso.setStatus(3);
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
			
			
			//permesso.setUtenteRichiedente(utenteRichiedente);
			permesso.setIdUtenteRichiedente(idUtenteLoggato);
			permesso.setUtenteApprovazione(utenteApprovazione);
			
			permesso.setUtenteApprovazioneDue(utenteApprovazioneDue);
			System.out.println("utente approvazione DUE è"+ permesso.getUtenteApprovazioneDue());
			System.out.println("utente richiedente è"+ permesso.getUtenteRichiedente());
			
		}

		
		//permessoRepo.save(permesso);
		return permessoRepo.save(permesso);
	
	}
	
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
		return permessoRepo.save(permessoDaAggiornare);
	}
	
	
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deletePermessoById(Long id) throws IOException {
		
		List<FileEntity> filePermesso= new ArrayList<FileEntity>();
		
		
		filePermesso= fileRepo.findByIdPermessoAssociato(id);
		
		if(!filePermesso.isEmpty()) {
			for (FileEntity file : filePermesso) {
				fileService.deleteFile(id, file.getFilename());
			}	
		}

		
		fileRepo.deleteByIdPermessoAssociato(id);
		permessoRepo.deletePermessoById(id);
	}

}
