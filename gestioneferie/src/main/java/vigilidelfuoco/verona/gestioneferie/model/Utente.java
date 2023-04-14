package vigilidelfuoco.verona.gestioneferie.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Utente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
		private Long id;
		private String nome;
		private String cognome;
		private String telefono;
		private String username;
		private String email;
		private String password;
		private String ruolo;
		@Column(nullable= false, updatable = false)
		private String codiceUtente;
		
		public Utente() {
			
		}
	 

	public Utente(Long id, String nome, String cognome, String telefono, String username, String email, String password,
			String ruolo, String codiceUtente) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.username = username;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
		this.codiceUtente = codiceUtente;
	}




	public String getCodiceUtente() {
			return codiceUtente;
		}

		public void setCodiceUtente(String codiceUtente) {
			this.codiceUtente = codiceUtente;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = nome + "." + cognome;
	}

	
}
