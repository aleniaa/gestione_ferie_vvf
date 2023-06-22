package vigilidelfuoco.verona.gestioneferie.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "permesso")
@AllArgsConstructor
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
	private int status; //0 = in revisione, 1 = approvato da approvatore 1 , 2= approvato da app 2, 3= approvato definitivamente da entrambi, 4= respinto;
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
	
	
	
	public Permesso(){
		//this.totGiorni= null;
	}
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		
		
	}
	
	
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine,  String tipoPermesso,
			Long idUtenteApprovazione, String dalleOre, String alleOre, LocalDate dataApprovazione, Utente utenteRichiedente) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		
		this.tipoPermesso = tipoPermesso;
		this.idUtenteApprovazione = idUtenteApprovazione;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.dataApprovazione = dataApprovazione;
		this.utenteRichiedente = utenteRichiedente;
		
	}
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine, Integer totGiorni, String tipoPermesso,
			Utente utenteApprovazione, String dalleOre, String alleOre, LocalDate dataApprovazione, Utente utenteRichiedente, String totOre) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		
		this.tipoPermesso = tipoPermesso;
		this.utenteApprovazione = utenteApprovazione;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.dataApprovazione = dataApprovazione;
		this.utenteRichiedente = utenteRichiedente;
		this.totOre= totOre;
		
	}
	

	
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine, Integer totGiorni, String tipoPermesso,
			Long idUtenteApprovazione, Long idUtenteRichiedente, String dalleOre, String alleOre,
			LocalDate dataApprovazione, String totOre, int status, String note) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = totGiorni;
		this.tipoPermesso = tipoPermesso;
		this.idUtenteApprovazione = idUtenteApprovazione;
		this.idUtenteRichiedente = idUtenteRichiedente;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.dataApprovazione = dataApprovazione;
		this.totOre = totOre;
		this.status = status;
		this.note = note;
	}



	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine, Integer totGiorni, String tipoPermesso,
			Long idUtenteApprovazione, Long idUtenteRichiedente, String dalleOre, String alleOre,
			LocalDate dataApprovazione, String totOre, Integer status, String note, Utente utenteRichiedente,
			Utente utenteApprovazione) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = totGiorni;
		this.tipoPermesso = tipoPermesso;
		this.idUtenteApprovazione = idUtenteApprovazione;
		this.idUtenteRichiedente = idUtenteRichiedente;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.dataApprovazione = dataApprovazione;
		this.totOre = totOre;
		this.status = status;
		this.note = note;
		this.utenteRichiedente = utenteRichiedente;
		this.utenteApprovazione = utenteApprovazione;
	}
	
	

	public Permesso(LocalDate dataInizio, LocalDate dataFine, Integer totGiorni, String tipoPermesso,
			Long idUtenteApprovazione, Long idUtenteApprovazioneDue, Long idUtenteRichiedente, String dalleOre,
			String alleOre, LocalDate dataApprovazione, String totOre, int status, String note,
			Utente utenteRichiedente, Utente utenteApprovazione, Utente utenteApprovazioneDue,
			List<FileEntity> elencoFile) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = totGiorni;
		this.tipoPermesso = tipoPermesso;
		this.idUtenteApprovazione = idUtenteApprovazione;
		this.idUtenteApprovazioneDue = idUtenteApprovazioneDue;
		this.idUtenteRichiedente = idUtenteRichiedente;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.dataApprovazione = dataApprovazione;
		this.totOre = totOre;
		this.status = status;
		this.note = note;
		this.utenteRichiedente = utenteRichiedente;
		this.utenteApprovazione = utenteApprovazione;
		this.utenteApprovazioneDue = utenteApprovazioneDue;
		this.elencoFile = elencoFile;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getTotGiorni() {
		return totGiorni;
	}

	public void setTotGiorni(Integer totGiorni) {
		this.totGiorni = totGiorni;
	}
	
	public void setTotGiorni() {
		this.totGiorni = (int) ChronoUnit.DAYS.between(this.dataInizio, this.dataFine);
	}

	public String getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(String tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public Long getIdUtenteApprovazione() {
		return idUtenteApprovazione;
	}

	public void setIdUtenteApprovazione(Long idUtenteApprovazione) {
		this.idUtenteApprovazione = idUtenteApprovazione;
	}

	public String getDalleOre() {
		return dalleOre;
	}

	//public void setDalleOre(LocalTime dalleOre) {
	public void setDalleOre(String dalleOre) {
//		DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");
//		LocalTime localTime = LocalTime.parse(dalleOre, parser);
		this.dalleOre = dalleOre;
	}

	public String getAlleOre() {
		return alleOre;
	}

//	public void setAlleOre(LocalTime alleOre) {
	public void setAlleOre(String alleOre) {

//		DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm");
//		LocalTime localTime = LocalTime.parse(alleOre, parser);
		//this.alleOre = localTime;
		this.alleOre = alleOre;
	}

	public LocalDate getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(LocalDate dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}


	public String getTotOre() {
		return totOre;
	}

	public void setTotOre(String totOre) {
		this.totOre= totOre;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Utente getUtenteApprovazione() {
		return utenteApprovazione;
	}

	public void setUtenteApprovazione(Utente utenteApprovazione) {
		this.utenteApprovazione = utenteApprovazione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdUtenteRichiedente() {
		return idUtenteRichiedente;
	}

	public void setIdUtenteRichiedente(Long idUtenteRichiedente) {
		this.idUtenteRichiedente = idUtenteRichiedente;
	}

	public Utente getUtenteRichiedente() {
		return utenteRichiedente;
	}

	public void setUtenteRichiedente(Utente utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}


	public List<FileEntity> getElencoFile() {
		return elencoFile;
	}

	public void setElencoFile(List<FileEntity> elencoFile) {
		this.elencoFile = elencoFile;
	}
	
	

	public Long getIdUtenteApprovazioneDue() {
		return idUtenteApprovazioneDue;
	}

	public void setIdUtenteApprovazioneDue(Long idUtenteApprovazioneDue) {
		this.idUtenteApprovazioneDue = idUtenteApprovazioneDue;
	}

	public Utente getUtenteApprovazioneDue() {
		return utenteApprovazioneDue;
	}

	public void setUtenteApprovazioneDue(Utente utenteApprovazioneDue) {
		this.utenteApprovazioneDue = utenteApprovazioneDue;
	}

	@Override
	public String toString() {
		return "Permesso [id=" + id + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + ", totGiorni="
				+ totGiorni + ", tipoPermesso=" + tipoPermesso + ", idUtenteApprovazione=" + idUtenteApprovazione
				+ ", idUtenteApprovazioneDue=" + idUtenteApprovazioneDue + ", idUtenteRichiedente="
				+ idUtenteRichiedente + ", dalleOre=" + dalleOre + ", alleOre=" + alleOre + ", dataApprovazione="
				+ dataApprovazione + ", totOre=" + totOre + ", status=" + status + ", note=" + note
				+ ", utenteRichiedente=" + utenteRichiedente + ", utenteApprovazione=" + utenteApprovazione
				+ ", utenteApprovazioneDue=" + utenteApprovazioneDue + ", elencoFile=" + elencoFile + "]";
	}



	
	
	
	
	
	
	


	
	

	/*
	 * public Long getIdUtente() { return idUtente; }
	 * 
	 * public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
	 */

	
	
	
	
	
	
}
