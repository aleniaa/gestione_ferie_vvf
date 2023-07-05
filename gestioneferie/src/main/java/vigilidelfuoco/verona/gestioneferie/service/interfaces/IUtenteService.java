package vigilidelfuoco.verona.gestioneferie.service.interfaces;

import vigilidelfuoco.verona.gestioneferie.model.Utente;
import vigilidelfuoco.verona.gestioneferie.model.UtenteRuolo;

import java.util.List;

public interface IUtenteService {

    Utente aggiungiUtente(Utente utente);

    List<Utente> trovaUtenti();

    Utente aggiornaUtente(Utente utente);

    Utente findUtenteById(Long id);

    List<Utente> findUtenteByRuolo(UtenteRuolo ruolo);

    void deleteUtenteById(Long id);
    boolean checkUtenteIfExists(Utente utente);

    boolean changePass(String oldPass, String newPass, Long idUtenteLoggato);

    boolean checkPasswordFirstAccess(Long idUtenteLoggato);


}
