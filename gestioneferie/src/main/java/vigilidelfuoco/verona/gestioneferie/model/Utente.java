package vigilidelfuoco.verona.gestioneferie.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
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
		private Long id_qualifica;
		private int passwordChanged; //0 se la password non è stata cambiata, 1 altrimenti

		
		@Column(unique = true) private String emailVigilfuoco;
		private String password;
		private String ruolo;
		@Column(nullable= false, updatable = false)
		private String codiceUtente;
		
		@OneToMany(mappedBy = "utenteRichiedente",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonIgnore
		private List<Permesso> elencoPermessi;
		
		@OneToMany(mappedBy = "utenteApprovazione",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonIgnore
		private List<Permesso> elencoPermessiApprovati;
		
		@OneToMany(mappedBy = "utenteApprovazioneDue",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonIgnore
		private List<Permesso> elencoPermessiApprovatiDue;
		
//	    @OneToOne(cascade = CascadeType.ALL)
//	    @JoinColumn(name = "id_qualifica", referencedColumnName = "id", insertable=false, updatable=false)
//	    private Qualifica qualifica;
		
		@ManyToOne
	    @JoinColumn(name = "id_qualifica", insertable=false, updatable=false)
		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		private Qualifica qualifica;
		
		public Utente() {
			
		}
	 

	public Utente(Long id, String nome, String cognome, String codiceFiscale, String telefono, String emailVigilfuoco, String password,
			String ruolo, String codiceUtente, int passwordChanged ) {
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
		this.passwordChanged = passwordChanged;
	}
	
	public Utente(Long id, String nome, String cognome, String codiceFiscale, String telefono, String emailVigilfuoco, String password,
			String ruolo, String codiceUtente, int passwordChanged, Qualifica qualifica ) {
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
		this.passwordChanged = passwordChanged;
		this.qualifica= qualifica;
	}
	
	public Utente(Long id, String nome, String cognome, String codiceFiscale, String telefono, String accountDipvvf,
			Long id_qualifica, int passwordChanged, String emailVigilfuoco, String password, String ruolo,
			String codiceUtente, Qualifica qualifica) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.telefono = telefono;
		this.accountDipvvf = accountDipvvf;
		this.id_qualifica = id_qualifica;
		this.passwordChanged = passwordChanged;
		this.emailVigilfuoco = emailVigilfuoco;
		this.password = password;
		this.ruolo = ruolo;
		this.codiceUtente = codiceUtente;
		this.qualifica = qualifica;
	}
	

	
	
	
	
	public Qualifica getQualifica() {
		return qualifica;
	}


	public void setQualifica(Qualifica qualifica) {
		this.qualifica = qualifica;
	}


	public int getPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(int passwordChanged) {
		this.passwordChanged = passwordChanged;
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
		String account= this.nome +"." + this.cognome;
		String stringaSenzaSpazi = account.replace(" ", "");
		this.accountDipvvf = stringaSenzaSpazi;
	}

	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
	

	public Long getId_qualifica() {
		return id_qualifica;
	}


	public void setId_qualifica(Long id_qualifica) {
		this.id_qualifica = id_qualifica;
	}


	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", telefono=" + telefono + ", accountDipvvf=" + accountDipvvf + ", emailVigilfuoco=" + emailVigilfuoco
				+ ", password=" + password + ", ruolo=" + ruolo + ", codiceUtente=" + codiceUtente + "]";
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
