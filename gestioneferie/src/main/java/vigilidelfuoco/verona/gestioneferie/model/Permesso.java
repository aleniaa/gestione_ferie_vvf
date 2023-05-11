package vigilidelfuoco.verona.gestioneferie.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Integer totGiorni;
	private String tipoPermesso;
	private Long idUtenteApprovazione;
	private LocalTime dalleOre;
	private LocalTime alleOre;
	private LocalDate dataApprovazione;
	private Integer totOre;
	private Integer status; //0 = in revisione, 1 = approvato, 2= respinto;
	private String note;
	
	
	@ManyToOne
    @JoinColumn(name = "idUtente")
    private Utente utenteRichiedente;
	
	@ManyToOne
    @JoinColumn(name = "idUtenteApprovazione", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Utente utenteApprovazione;
	
	
	
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
			Long idUtenteApprovazione, LocalTime dalleOre, LocalTime alleOre, LocalDate dataApprovazione, Utente utenteRichiedente) {
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
			Utente utenteApprovazione, LocalTime dalleOre, LocalTime alleOre, LocalDate dataApprovazione, Utente utenteRichiedente) {
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

	public void setTotGiorni(int totGiorni) {
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

	public LocalTime getDalleOre() {
		return dalleOre;
	}

	public void setDalleOre(LocalTime dalleOre) {
		this.dalleOre = dalleOre;
	}

	public LocalTime getAlleOre() {
		return alleOre;
	}

	public void setAlleOre(LocalTime alleOre) {
		this.alleOre = alleOre;
	}

	public LocalDate getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(LocalDate dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public Utente getutenteRichiedente() {
		return utenteRichiedente;
	}

	public void setutenteRichiedente(Utente utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}

	public Integer getTotOre() {
		return totOre;
	}

	public void setTotOre(int totOre) {
		this.totOre= totOre;
	}
	
	public void setTotOre() {
		this.totOre = (int) ChronoUnit.HOURS.between(this.dalleOre, this.alleOre);
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

	@Override
	public String toString() {
		return "Permesso [id=" + id + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine + ", totGiorni="
				+ totGiorni + ", tipoPermesso=" + tipoPermesso + ", idUtenteApprovazione=" + idUtenteApprovazione
				+ ", dalleOre=" + dalleOre + ", alleOre=" + alleOre + ", dataApprovazione=" + dataApprovazione
				+ ", totOre=" + totOre + ", status=" + status + ", note=" + note + ", utenteRichiedente="
				+ utenteRichiedente + ", utenteApprovazione=" + utenteApprovazione + "]";
	}

	
	
	
	
	
	
	


	
	

	/*
	 * public Long getIdUtente() { return idUtente; }
	 * 
	 * public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
	 */

	
	
	
	
	
	
}
