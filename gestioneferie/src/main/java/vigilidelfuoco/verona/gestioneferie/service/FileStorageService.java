package vigilidelfuoco.verona.gestioneferie.service;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vigilidelfuoco.verona.gestioneferie.model.FileEntity;
import vigilidelfuoco.verona.gestioneferie.repo.FileRepo;

@Service
public class FileStorageService {

	@Autowired
	private FileRepo fileDBRepository;
	
	  public FileEntity store(MultipartFile file) throws IOException {
		    //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

	        FileEntity FileDB = new FileEntity(fileName, file.getContentType(), file.getBytes());

		    return fileDBRepository.save(FileDB);
		  }
	  
	  public FileEntity getFile(Long id) {
		    return fileDBRepository.findById(id).get();
		  }
		  
		  public Stream<FileEntity> getAllFiles() {
		    return fileDBRepository.findAll().stream();
		  }
}
