package vigilidelfuoco.verona.gestioneferie.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.repo.FileRepo;
import static java.nio.file.Files.copy;

@Service
public class FileStorageService {

	private final  FileRepo fileRepo;
	@Value("${app.directory.path}")
	private String upload_dir;
	private Path tempPath;
	
	
	
public FileStorageService(FileRepo fileRepo) {
		super();
		this.fileRepo = fileRepo; 
		//this.upload_dir = "C:/Users/milen/git/gestione_ferie_vvf/gestioneferie/uploadedFile/";
		
		
		this.tempPath= null;
	}


	
public List<String> uploadfileToPermesso(List<MultipartFile> multipartFiles, Permesso permesso) throws IOException {
//public void uploadfileToPermesso(MultipartFile file) {

	System.out.println("son odentro fielstorageservice");

	 List<String> filenames = new ArrayList<>();

	for(MultipartFile file : multipartFiles) {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); //necessita l'estensione del file per funzioanre tutto
        Path filePath = Paths.get(upload_dir + filename);
        copy(file.getInputStream(), filePath);
        filenames.add(filename);
        FileEntity fileEntity = new FileEntity(filename, file.getContentType(), filePath.toString(), permesso.getId(), permesso);
        fileRepo.save(fileEntity);

    }
	
	return filenames;

}
	
	

	public List<String> getFilePermesso(Long idPermesso) {
	    System.out.println("Sono dentro getFile in FileService");
	    
		System.out.println("son odentro fielstorageservice");

		 //List<String> filenames = new ArrayList<>();
		 //filenames= fileRepo.findFileNameByIdPermessoAssociato(idPermesso);
		 
		 List<String> filenames = fileRepo.findFileNameByIdPermessoAssociato(idPermesso);
		 
		
		 
		for(String filename : filenames) {

	        //filenames.add(filename);
		    System.out.println("Filename trovati: " + filename);

	    }
		return filenames;	
		

	}



	public Resource downloadFilePermesso(Long idPermesso, String fileName) throws MalformedURLException {

		Optional<FileEntity> fileTrovato = fileRepo.findByIdPermessoAssociatoAndFilename(idPermesso, fileName);
		Resource resourceTrovata = null;
		if(fileTrovato.isPresent()) {
	    	System.out.println("sono dentro download in file storage service e ho trovato il file:" + fileTrovato.get().toString());

			Path filePath = Paths.get(upload_dir + fileName);
			setPath(filePath);
	        Resource resource = new UrlResource(filePath.toUri());
	        if (resource.exists()) {
	        	resourceTrovata = resource;
	        }

		}
		
		return resourceTrovata;
	}
	
	public void setPath(Path path) {
		this.tempPath= path;
	}
	
	public Path getPath() {
		return tempPath;
	}


	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deleteAllFile(Long idPermessoAssociato) throws IOException {
		
		List<String> filenames = this.getFilePermesso(idPermessoAssociato);
		Path filePath;
		
		for(String filename : filenames) {

			filePath = Paths.get(upload_dir + filename);
			Files.delete(filePath);
	    }
		
		
		fileRepo.deleteByIdPermessoAssociato(idPermessoAssociato);
	}
	
	@Transactional //con i delete si deve mettere altrimenti da errore
	public void deleteFile(Long idPermessoAssociato, String filename) throws IOException {
		
		Path filePath = Paths.get(upload_dir + filename);
		
		Files.delete(filePath);
		fileRepo.deleteByIdPermessoAssociatoAndFilename(idPermessoAssociato, filename);
	}






}
