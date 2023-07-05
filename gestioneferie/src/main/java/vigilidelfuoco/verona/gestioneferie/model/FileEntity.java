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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uploadedFile")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

}
