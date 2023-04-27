package vigilidelfuoco.verona.gestioneferie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;

@AllArgsConstructor
@Service
public class PermessoService {
	
	private final PermessoRepo permessoRepo;

	@Autowired
	public PermessoService(PermessoRepo permessoRepo) {
		super();
		this.permessoRepo = permessoRepo;
	}
	
	public List<Permesso> trovaPermessi(){
		return permessoRepo.findAll();
	}
	

}
