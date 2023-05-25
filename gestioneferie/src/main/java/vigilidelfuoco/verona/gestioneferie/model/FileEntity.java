package vigilidelfuoco.verona.gestioneferie.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "uploadedFile")
@AllArgsConstructor
public class FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String filename;
	private String fileType;
	private String fileUrl;
	private Long idPermessoAssociato; //al fine della creazione della colonna nel db e dell'avvio dell'applicazione non è necessario avere questa variabile.
	
	@ManyToOne
    @JoinColumn(name = "idPermessoAssociato", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Permesso permessoAssociato;
	
	@Lob
	private byte[] data;
	
	

	public FileEntity() {
		super();
	}

	
	
	public FileEntity(String filename, String fileType, String fileUrl) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
	}

	


	public FileEntity(String filename, String fileType, String fileUrl, Long idPermessoAssociato,
			Permesso permessoAssociato) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.idPermessoAssociato = idPermessoAssociato;
		this.permessoAssociato = permessoAssociato;
	}



	public FileEntity(Long id, String filename, String fileType, String fileUrl, Long idPermessoAssociato,
			Permesso permessoAssociato) {
		super();
		this.id = id;
		this.filename = filename;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.idPermessoAssociato = idPermessoAssociato;
		this.permessoAssociato = permessoAssociato;
	}



	public FileEntity( String filename, String fileType, String fileUrl, Permesso permessoAssociato,
			byte[] data) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.permessoAssociato = permessoAssociato;
		this.data = data;
	}
	
	
	
	

	public FileEntity(String filename, String fileType, String fileUrl, Permesso permessoAssociato) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.permessoAssociato = permessoAssociato;
	}

	public FileEntity(String filename, String fileType, byte[] data) {
		super();
		this.filename = filename;
		this.fileType = fileType;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Permesso getPermessoAssociato() {
		return permessoAssociato;
	}

	public void setPermessoAssociato(Permesso permessoAssociato) {
		this.permessoAssociato = permessoAssociato;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	

	public Long getIdPermessoAssociato() {
		return idPermessoAssociato;
	}

	public void setIdPermessoAssociato(Long idPermessoAssociato) {
		this.idPermessoAssociato = idPermessoAssociato;
	}

	@Override
	public String toString() {
		return "FileEntity [id=" + id + ", filename=" + filename + ", fileType=" + fileType + ", fileUrl=" + fileUrl
				+ ", permessoAssociato=" + permessoAssociato + "]";
	}



	
}
