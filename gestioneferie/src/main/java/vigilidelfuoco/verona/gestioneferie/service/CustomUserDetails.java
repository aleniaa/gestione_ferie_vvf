
package vigilidelfuoco.verona.gestioneferie.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vigilidelfuoco.verona.gestioneferie.model.Utente;


public class CustomUserDetails implements UserDetails {

	private Utente utente;

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(Utente utente) {
		super();

		this.utente = utente;
	}

	@Override public Collection<? extends GrantedAuthority> getAuthorities() { //
		return Collections.singleton(new SimpleGrantedAuthority(utente.getRuolo())); 
		}

	@Override
	public String getPassword() { // TODO Auto-generated method stub
		return utente.getPassword();
	}

	@Override
	public String getUsername() { // TODO Auto-generated method stub
		return utente.getAccountDipvvf();
	}

	@Override public boolean isAccountNonExpired() { // TODO Auto-generated
		return true; }

	@Override public boolean isAccountNonLocked() { // TODO Auto-generated method
		return true; }

	@Override public boolean isCredentialsNonExpired() { // TODO Auto-generated
		 return true; }

	@Override
	public boolean isEnabled() { // TODO Auto-generated method stub
		return true;
	}

}
