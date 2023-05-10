package vigilidelfuoco.verona.gestioneferie.service;
import java.util.*;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class PermessoService {
	
	private final PermessoRepo permessoRepo;
	private final UtenteRepo utenteRepo;

	@Autowired
	public PermessoService(PermessoRepo permessoRepo, UtenteRepo utenteRepo) {
		super();
		this.permessoRepo = permessoRepo;
		this.utenteRepo= utenteRepo;
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
	
//	public List<Permesso> findPermessoByStatusAndIdUtenteApprovazione(){
//	return
//}
	
//	public List<Permesso> findPermessiByIdUtente(Long idUtente){
//		return permessoRepo.findPermessoByIdUtente(idUtente);
//	}
	
	
	public List<Permesso> getFilteredPermessi(Permesso permesso){
		
		List<Permesso> permessiTot= new ArrayList<Permesso>();
		
//		if(permesso.getUtente().getAccountDipvvf()!=null) {
//			System.out.println("permesso utente = "+ permesso.getUtente());
//		}
		
		if(permesso.getTipoPermesso()!= null) {
			
//			switch(permesso.getTipoPermesso()) {
//			case "tutti i permessi":
//				permessiTot.add(0, permesso); permessoRepo.findAll();
//				break;
//			case "congedo ordinario":
//				
//
//			}
			
			System.out.println("permesso tipoPermesso = "+ permesso.getTipoPermesso());
			
			permessiTot= permessoRepo.findPermessoBytipoPermesso(permesso.getTipoPermesso());
		}
		
		return permessiTot;
	} 
	
	public Permesso aggiungiPermesso(Permesso permesso){
		if(permesso.getDalleOre()!=null) { // se ci sono delle ore di permesso e non dei giorni
			permesso.setTotOre();
		}else {
			permesso.setTotGiorni();
		}
		System.out.println("id utente approvazione = "+ permesso.getIdUtenteApprovazione());
		Utente utenteApprovazione = utenteRepo.findUtenteByIdsenzaoptional(permesso.getIdUtenteApprovazione());
		
//		Utente utenteApprovazione = permessoRepo.findUtenteByIdUtenteApprovazione();
//		System.out.println("account dipvvf utente approvazione = "+ utenteApprovazione.getAccountDipvvf());
		permesso.setUtenteApprovazione(utenteApprovazione);
		
		
		return permessoRepo.save(permesso);
	
	}
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deletePermessoById(Long id) {
		permessoRepo.deletePermessoById(id);
	}

}
