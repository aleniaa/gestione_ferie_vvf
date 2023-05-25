package vigilidelfuoco.verona.gestioneferie.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.service.FileStorageService;
import vigilidelfuoco.verona.gestioneferie.service.PermessoService;
import vigilidelfuoco.verona.gestioneferie.model.FileEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@RestController
@RequestMapping("/permesso")
public class PermessoController {

	private final PermessoService permessoService;
	
	  @Autowired
	  private FileStorageService storageService;
	  
	public PermessoController(PermessoService permessoService, FileStorageService storageService ) {
		super();
		this.permessoService = permessoService;
		this.storageService= storageService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Permesso>> getAllPermessi(){
		List<Permesso> permessi = permessoService.trovaPermessi();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Permesso>> getPermessoByStatus(@PathVariable("status") int status){
		List<Permesso> permessi = permessoService.findPermessoByStatus(status);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/statusRichiedente/{status}") //restituisce i permessi dell'utente loggato
	public ResponseEntity<List<Permesso>> getPermessoRichiedenteByStatus(@PathVariable("status") int status, @RequestParam("idRichiedente") Long idRichiedente ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idRichiedente);

		List<Permesso> permessi = permessoService.findPermessoRichiedenteByStatus(status, idRichiedente);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@GetMapping("/statusApprovatore/{status}") //restituisce i permessi relativi all'approvatore
	public ResponseEntity<List<Permesso>> getPermessiApprovatoreByStatus(@PathVariable("status") int status, @RequestParam("idApprovatore") Long idApprovatore ){
		System.out.println("l'id del richiedente dentro permesso controller è "+ idApprovatore);

		List<Permesso> permessi = permessoService.findPermessoApprovatoreByStatus(status, idApprovatore);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PutMapping("/updateStatus/{decisione}")
	public ResponseEntity<Permesso> changeStatusPermesso(@PathVariable("decisione") String decisione, @RequestBody Permesso permesso ){
		
		System.out.println("sono dentro changestatus permesso controller e le note sono : "+ permesso.getNote());
		System.out.println("sono dentro changestatus permesso controller e utente richiedente è : "+ permesso.getUtenteRichiedente());
		System.out.println("sono dentro changestatus permesso controller  e utente approvatore è : "+ permesso.getUtenteApprovazione());

		Permesso permessoAggiornato = permessoService.aggiornaStatusPermesso(decisione, permesso);

		return new ResponseEntity<>(permessoAggiornato, HttpStatus.OK);
	}
	
	
	@GetMapping("/allCongedo")
	public ResponseEntity<List<Permesso>> getAllCongedo(){
		List<Permesso> permessi = permessoService.trovaTipoCongedo();
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/search") 
	public ResponseEntity<List<Permesso>> getFilteredPermessi(@RequestBody Permesso permesso){
		List<Permesso> permessi = permessoService.getFilteredPermessi(permesso);
		return new ResponseEntity<>(permessi, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//public ResponseEntity<Permesso> aggiungiPermesso(@RequestBody Permesso permesso){
		public ResponseEntity<String> aggiungiPermesso(@RequestBody Permesso permesso){

		
		//storageService.store(file);
		//Permesso newPermesso= permessoService.aggiungiPermesso(permesso);
		permessoService.aggiungiPermesso(permesso);

		//return new ResponseEntity<>(newPermesso, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body("Permesso aggiunto correttamente!");

	}
	
	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("permesso") String permessoJson ) {

	//public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

		JSONObject jsonObject = new JSONObject(permessoJson);
        jsonObject.remove("utenteRichiedente");
        jsonObject.remove("utenteApprovazione");

        String updatedPermessoJson = jsonObject.toString();
		
		System.out.println(updatedPermessoJson);
		// Deserialize the permessoJson to a Permesso object
	    ObjectMapper objectMapper = JsonMapper.builder()
	    	    .addModule(new JavaTimeModule())
	    	    .build();
	    Permesso permesso;
	    
	    try {
	        permesso = objectMapper.readValue(updatedPermessoJson, Permesso.class);
	    	System.out.println("il permesso è:" + permesso.toString());

	    } catch (JsonProcessingException e) {
	        // Handle deserialization error
	    	 System.out.println("non ha funzionato");
	    	 e.printStackTrace();
	        return ResponseEntity.badRequest().body("Invalid permesso JSON");
	    }
	    
	    System.out.println("son dentro permesso controller");
		//System.out.println("son odentro permesso controller"+permesso.toString());
		permessoService.uploadfileToPermesso(file, permesso);
		//permessoService.uploadfileToPermesso(file);

		// Perform validation checks on the file
//		String upload_dir ="/path/to/uploaded/files";
//	    try {
//	        // Generate a unique filename
//	        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//	        // Save the file to the upload directory
//	        Path filePath = Paths.get(upload_dir + filename);
//	        Files.copy(file.getInputStream(), filePath);
//	        // Save the file reference in the database
//	        FileEntity fileEntity = new FileEntity(filename, file.getContentType(), filePath.toString(), permesso);
//	        fileRepository.save(fileEntity);
//
//	        return ResponseEntity.ok("File uploaded successfully");
//	    } catch (Exception e) {
//	        // Handle any exceptions that occur during file upload
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
//	    }
		
		
		return ResponseEntity.ok("File uploaded successfully");
	}

	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> cancellaPermesso(@PathVariable("id") Long id){
		permessoService.deletePermessoById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
}
