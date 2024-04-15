package vigilidelfuoco.verona.gestioneferie.service;

import java.util.List;
import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.model.Qualifica;
import vigilidelfuoco.verona.gestioneferie.repo.QualificaRepo;

@Service
public class QualificaService {
	
	private final QualificaRepo qualificaRepo;

	public QualificaService(QualificaRepo qualificaRepo) {
		super();
		this.qualificaRepo = qualificaRepo;
	}
	
	public boolean checkQualificaIfExists(Qualifica qualifica) {
		
		if(qualificaRepo.existsByNome(qualifica.getNome())) {
			return false;
		}else {
			 System.out.println("La qualifica esiste già");
			 return true;
		}
		
		
	}
	
	public Qualifica aggiungiQualifica(Qualifica qualifica){
		
		
		//utente.setAccountDipvvf();
		return qualificaRepo.save(qualifica);
		
		
	}
	
	public List<Qualifica> getAllQualifiche(){
		return qualificaRepo.findAll();
	}

}
