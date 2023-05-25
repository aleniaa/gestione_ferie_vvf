package vigilidelfuoco.verona.gestioneferie.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

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
	public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> file, @RequestParam("permesso") String permessoJson ) throws IOException {

		List<String> filenames = new ArrayList<>();
		
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
        permesso = objectMapper.readValue(updatedPermessoJson, Permesso.class);

	    System.out.println("son dentro permesso controller");

	    filenames = storageService.uploadfileToPermesso(file, permesso);

		
		return ResponseEntity.ok().body(filenames);
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
	
//	@GetMapping("/getFile")
//	public ResponseEntity<List<File>> getFile(@RequestParam("idPermesso") Long idPermesso ) {
//		
//	    System.out.println("Sono dentro getFile in controller");
//
//	    List<File> resource = storageService.getFilePermesso(idPermesso);
//		return new ResponseEntity<>(resource, HttpStatus.OK);
//		
//
//	}
	
	
    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename, @RequestParam("idPermesso") Long idPermesso) throws IOException {
        
    	System.out.println("sono dentro download e l'id permesso è:" + idPermesso.toString());
    	Resource resource = storageService.downloadFilePermesso(idPermesso, filename);
    	Path filePath = storageService.getPath();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }
	


}
