package vigilidelfuoco.verona.gestioneferie.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permesso")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Permesso {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Integer totGiorni; // settato a Integer perchè integer può essere null
	private String tipoPermesso;
	private Long idUtenteApprovazione;
	private Long idUtenteApprovazioneDue;

	private Long idUtenteRichiedente;
	private String dalleOre;
	private String alleOre;
	private LocalDate dataApprovazione;
	private String totOre;

	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status;
	//0 = in revisione, 1 = approvato da approvatore 1 , 2= approvato da app 2, 3= approvato definitivamente da entrambi, 4= respinto da approvatore 1; 5= respinto da approvatore 2;
	private String note;
	
	
	@ManyToOne
    @JoinColumn(name = "idUtenteRichiedente", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Utente utenteRichiedente;
	
	@ManyToOne
    @JoinColumn(name = "idUtenteApprovazione", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Utente utenteApprovazione;
	
	@ManyToOne
    @JoinColumn(name = "idUtenteApprovazioneDue", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Utente utenteApprovazioneDue;
	
	@OneToMany(mappedBy = "permessoAssociato",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<FileEntity> elencoFile;

	
}
