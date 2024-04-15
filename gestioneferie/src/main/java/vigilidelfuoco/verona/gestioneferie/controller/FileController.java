package vigilidelfuoco.verona.gestioneferie.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	  private final ResourceLoader resourceLoader;

	public FileController(FileStorageService storageService, ResourceLoader resourceLoader) {
		super();
		this.storageService = storageService;
		this.resourceLoader = resourceLoader;
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
	
	@GetMapping("/getFile")
	public ResponseEntity<List<String>> getFile(@RequestParam("idPermesso") Long idPermesso ) {
		
	    System.out.println("Sono dentro getFile in controller");
	    
	    List<String> filenames = new ArrayList<>();
	    filenames = storageService.getFilePermesso(idPermesso);

		
		return ResponseEntity.ok().body(filenames);
		

	}
	
	
	
    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename, @RequestParam("idPermesso") Long idPermesso) throws IOException {
        
    	Resource resource = storageService.downloadFilePermesso(idPermesso, filename);
    	Path filePath = storageService.getPath();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("filename", filename);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }
    
    @GetMapping("show/{filename}")
    public ResponseEntity<Resource> showFiles(@PathVariable("filename") String filename, @RequestParam("idPermesso") Long idPermesso) throws IOException {
        
    	try {
            Resource fileResource = resourceLoader.getResource("classpath:/uploadedFile/" + filename);

            if (fileResource.exists() && fileResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                        .body(fileResource);
            } else {
                // Handle the case when the file doesn't exist or is not readable.
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur while loading the file.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    
    @DeleteMapping("deleteAllFile/{filename}")
    public ResponseEntity<?> deleteAllFile(@PathVariable("filename") String filename, @RequestParam("idPermesso") Long idPermesso) throws IOException {

    	storageService.deleteAllFile(idPermesso);
    	
		return new ResponseEntity<>(HttpStatus.OK);

    	
    }
    
    @DeleteMapping("deleteFile/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable("filename") String filename, @RequestParam("idPermesso") Long idPermesso) throws IOException {
        System.out.println("Il file da eliminare è: "+ filename);
        System.out.println("l'id del permesso associato al file da eliminare è: "+ idPermesso.toString());

    	storageService.deleteFile(idPermesso, filename);
    	
		return new ResponseEntity<>(HttpStatus.OK);

    	
    }
	


}
