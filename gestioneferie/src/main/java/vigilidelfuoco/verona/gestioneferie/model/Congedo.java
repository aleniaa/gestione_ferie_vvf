package vigilidelfuoco.verona.gestioneferie.model;

import java.sql.Date;
import java.time.LocalDate;
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

@Entity
@Table(name = "congedo")
public class Congedo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private int totGiorni;
	
	@ManyToOne
    @JoinColumn(name = "idUtente")
    private Utente utente;
	
	
	
	public Congedo(){}
	
	public Congedo(Long id, LocalDate dataInizio, LocalDate dataFine) {
		super();
		this.id = id;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.totGiorni = (int) ChronoUnit.DAYS.between(dataInizio, dataFine);
		
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

	/*
	 * public Long getIdUtente() { return idUtente; }
	 * 
	 * public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
	 */

	
	
	
	
	
	
}
