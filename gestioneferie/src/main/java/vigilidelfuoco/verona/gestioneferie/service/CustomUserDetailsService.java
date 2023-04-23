
package vigilidelfuoco.verona.gestioneferie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {


	@Autowired 
	private UtenteRepo utenteRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Utente utente = utenteRepo.findByaccountDipvvf(username);
		if (utente == null) {
			throw new UsernameNotFoundException("Utente non trovato");
		}

		return new CustomUserDetails(utente);
	}
}
