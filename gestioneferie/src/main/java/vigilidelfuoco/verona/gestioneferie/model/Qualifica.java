package vigilidelfuoco.verona.gestioneferie.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity 
public class Qualifica {

	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String descrizione;
	
//    @OneToOne(mappedBy = "qualifica")
//    private Utente utente;
    
	@OneToMany(mappedBy = "qualifica",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Utente> elencoUtenti;
	
	public Qualifica() {
		super();
	}
	
	public Qualifica(Long id, String nome, String descrizione) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public Qualifica(String nome, String descrizione) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	
	
	
}
