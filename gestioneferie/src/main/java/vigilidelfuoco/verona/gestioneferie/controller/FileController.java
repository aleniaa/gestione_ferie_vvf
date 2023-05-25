package vigilidelfuoco.verona.gestioneferie.controller;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.service.FileStorageService;

@RestController
@RequestMapping("/upload")
public class FileController {

	  @Autowired
	  private final FileStorageService storageService;

	public FileController(FileStorageService storageService) {
		super();
		this.storageService = storageService;
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

	    storageService.uploadfileToPermesso(file, permesso);

		
		return ResponseEntity.ok("File uploaded successfully");
	}
	
//	@GetMapping("/getFile")
//	public ResponseEntity<Resource> getFile(@RequestParam("idPermesso") Long idPermesso ) {
//		
//	    System.out.println("Sono dentro getFile in controller");
//
//		Resource resource = storageService.getFilePermesso(idPermesso);
//		return new ResponseEntity<>(resource, HttpStatus.OK);
//		
//
//	}
	
	@GetMapping("/getFile")
	public ResponseEntity<List<File>> getFile(@RequestParam("idPermesso") Long idPermesso ) {
		
	    System.out.println("Sono dentro getFile in controller");

	    List<File> resource = storageService.getFilePermesso(idPermesso);
		return new ResponseEntity<>(resource, HttpStatus.OK);
		

	}
	
	
	
	


}
