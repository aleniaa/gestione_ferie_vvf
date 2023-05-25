package vigilidelfuoco.verona.gestioneferie.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;
import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.repo.FileRepo;

@Service
public class FileStorageService {

	private final  FileRepo fileRepo;
	private String upload_dir;
	
	
public FileStorageService(FileRepo fileRepo) {
		super();
		this.fileRepo = fileRepo;
		this.upload_dir = "C:/Users/ilenia.mannino/git/gestione_ferie_vvf/gestioneferie/uploadedFile/";

	}



//	@Autowired
//	private FileRepo fileDBRepository;
//	
//	  public FileEntity store(MultipartFile file) throws IOException {
//		    //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//	        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//	        FileEntity FileDB = new FileEntity(fileName, file.getContentType(), file.getBytes());
//
//		    return fileDBRepository.save(FileDB);
//		  }
//	  
//	  public FileEntity getFile(Long id) {
//		    return fileDBRepository.findById(id).get();
//		  }
//		  
//		  public Stream<FileEntity> getAllFiles() {
//		    return fileDBRepository.findAll().stream();
//		  }


	public void uploadfileToPermesso(MultipartFile file, Permesso permesso) {
	//public void uploadfileToPermesso(MultipartFile file) {
	
		System.out.println("son odentro fielstorageservice");

	    try {
	        // Generate a unique filename
	        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	        // Save the file to the upload directory
	        Path filePath = Paths.get(upload_dir + filename);
	        Files.copy(file.getInputStream(), filePath);
	        // Save the file reference in the database
	        FileEntity fileEntity = new FileEntity(filename, file.getContentType(), filePath.toString(), permesso.getId(), permesso);
	        //FileEntity fileEntity = new FileEntity(filename, file.getContentType(), filePath.toString());

	        System.out.println(fileEntity.toString());
	        fileRepo.save(fileEntity);

	    } catch (Exception e) {
	        // Handle any exceptions that occur during file upload
	    	e.printStackTrace();
	    }
	}
	
//	public Resource getFilePermesso(Long idPermesso) {
//	    System.out.println("Sono dentro getFile in FileService");
//
//		Optional<FileEntity> fileTrovato = fileRepo.findByIdPermessoAssociato(idPermesso);
//		Resource resourceTrovata = null;
//		
//		if(fileTrovato.isPresent()) {
//			
//		    System.out.println("Qualche risorsa è presente");
//
//			
//		    try {
//		    	
//		    	
//		        Path filePath = Paths.get(upload_dir + fileTrovato.get().getFilename());
//		        Resource resource = new UrlResource(filePath.toUri());
//
//		        if (resource.exists()) {
////		            return ResponseEntity.ok()
////		                    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
////		                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
////		                    .body(resource);
//		        	resourceTrovata=resource;
//		        	System.out.println("Trovato file");
//		        } else {
//		        	System.out.println("Niente file");
//
////		            return ResponseEntity.notFound().build();
//		        }
//		        
//				
//
//		    } catch (IOException e) {
//		        // Handle any exceptions that occur during file retrieval
////		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		    }
//		}
//		
//		return resourceTrovata;
//		
//
//	}
	
	

	public List<File> getFilePermesso(Long idPermesso) {
	    System.out.println("Sono dentro getFile in FileService");
	    
	    Optional<FileEntity> fileTrovato = fileRepo.findByIdPermessoAssociato(idPermesso);
	    List<File> imageFiles = new ArrayList<>();
	    
	    if(fileTrovato.isPresent()) {
			 
		        
		        File directory = new File(upload_dir);
		        
		        if (directory.exists() && directory.isDirectory()) {
		            File[] files = directory.listFiles();
		            
		            if (files != null) {
		                for (File file : files) {
		                    if (file.getName().equals(fileTrovato.get().getFilename())) {
		                    	System.out.println("nome del file nella cartella: " + file.getName());
		                        
		                    	System.out.println("nome del file nel database: " + fileTrovato.get().getFilename());

		                    	imageFiles.add(file);
		                    }
		                }
		            }
		        }
	    }

	    
		        
		return imageFiles;
		    
	    
		
		

	}









}
