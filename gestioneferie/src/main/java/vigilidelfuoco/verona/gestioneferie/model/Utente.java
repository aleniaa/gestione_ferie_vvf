package vigilidelfuoco.verona.gestioneferie.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity 
@Table(name = "utente")
//public class Utente implements UserDetails  {

public class Utente  {

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
		private String codiceFiscale;
		private String telefono;
		private String accountDipvvf; //username

		
		@Column(unique = true) private String emailVigilfuoco;
		private String password;
		private String ruolo;
		@Column(nullable= false, updatable = false)
		private String codiceUtente;
		
		@OneToMany(mappedBy = "utente",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		private List<Permesso> elencoPermessi;
		
		public Utente() {
			
		}
	 

	public Utente(Long id, String nome, String cognome, String codiceFiscale, String telefono, String emailVigilfuoco, String password,
			String ruolo, String codiceUtente) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale= codiceFiscale;
		this.telefono = telefono;
		this.accountDipvvf = nome +"."+ cognome;
		this.emailVigilfuoco = emailVigilfuoco;
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

	public String getEmailVigilfuoco() {
		return emailVigilfuoco;
	}

	public void setEmailVigilfuoco() {
		this.emailVigilfuoco = this.accountDipvvf+"@vigilfuoco.it";
	}
	
	public void setEmailVigilfuoco(String email) {
		this.emailVigilfuoco = email;
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

	public String getAccountDipvvf() {
		return accountDipvvf;
	}

	public void setAccountDipvvf() {
		this.accountDipvvf = this.nome +"." + this.cognome;
	}

	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


//	public List<Permesso> getElencoPermessi() {
//		return elencoPermessi;
//	}
//
//
//	public void setElencoPermessi(List<Permesso> elencoPermessi) {
//		this.elencoCongedi = elencoPermessi;
//	}

//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		
//		SimpleGrantedAuthority authority =
//				new SimpleGrantedAuthority(this.getRuolo());
//		return null;
//	}
//
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return accountDipvvf;
//	}
//
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return !locked;
//	}
//
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return enabled;
//	}

	
}
