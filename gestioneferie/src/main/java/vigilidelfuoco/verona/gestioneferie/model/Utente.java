package vigilidelfuoco.verona.gestioneferie.model;

import java.io.Serializable;
import java.util.Collection;

import lombok.*;
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

import java.util.List;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "utente")
//public class Utente implements UserDetails  {

public class Utente  {
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
		private int passwordChanged=0; //0 se la password non è stata cambiata, 1 altrimenti //porcheria!
		@Column(unique = true)
		private String emailVigilfuoco;
		@JsonIgnore
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

		@ManyToOne
	    @JoinColumn(name = "id_qualifica", insertable=false, updatable=false)
		@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
		private Qualifica qualifica;


	
}
