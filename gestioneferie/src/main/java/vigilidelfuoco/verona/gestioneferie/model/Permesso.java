package vigilidelfuoco.verona.gestioneferie.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
	private int totGiorni;
	private String tipoPermesso;
	private Long idUtenteApprovazione;
	private LocalTime dalleOre;
	private LocalTime alleOre;
	private LocalDate delGiorno;
	private int totOre;
	
	@ManyToOne
    @JoinColumn(name = "idUtente")
    private Utente utente;
	
	
	
	public Permesso(){}
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = (int) ChronoUnit.DAYS.between(dataInizio, dataFine);
		
	}
	
	public Permesso(Long id, LocalDate dataInizio, LocalDate dataFine, int totGiorni, String tipoPermesso,
			Long idUtenteApprovazione, LocalTime dalleOre, LocalTime alleOre, LocalDate delGiorno, Utente utente) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = (int) ChronoUnit.DAYS.between(dataInizio, dataFine);
		this.tipoPermesso = tipoPermesso;
		this.idUtenteApprovazione = idUtenteApprovazione;
		this.dalleOre = dalleOre;
		this.alleOre = alleOre;
		this.delGiorno = delGiorno;
		this.utente = utente;
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

	public int getTotGiorni() {
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

	public LocalDate getDelGiorno() {
		return delGiorno;
	}

	public void setDelGiorno(LocalDate delGiorno) {
		this.delGiorno = delGiorno;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public int getTotOre() {
		return totOre;
	}

	public void setTotOre() {
		this.totOre = (int) ChronoUnit.HOURS.between(this.dalleOre, this.alleOre);
	}
	
	
	
	


	
	

	/*
	 * public Long getIdUtente() { return idUtente; }
	 * 
	 * public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
	 */

	
	
	
	
	
	
}
