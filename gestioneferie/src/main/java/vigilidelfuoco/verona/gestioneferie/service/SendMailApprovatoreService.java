package vigilidelfuoco.verona.gestioneferie.service;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import vigilidelfuoco.verona.gestioneferie.model.Permesso;
import vigilidelfuoco.verona.gestioneferie.repo.PermessoRepo;
import vigilidelfuoco.verona.gestioneferie.repo.UtenteRepo;

@Service
public class SendMailApprovatoreService {

	private final PermessoRepo permessoRepo;
	private final UtenteRepo utenteRepo;

	
	public SendMailApprovatoreService(PermessoRepo permessoRepo, UtenteRepo utenteRepo) {
		super();
		this.permessoRepo = permessoRepo;
		this.utenteRepo= utenteRepo;
	}

	@Scheduled(cron = "0 30 8 * * ?") // primo numero: secondi, secondo numero: minuti; terzo numero: ora; *: giorno del mese; *: mese; ? : giorno della settimana
	//@Scheduled(fixedRate = 10000) // 10 secondi
	public void performTasksPeriodically() {
        System.out.println("TASK SCHEDULED");
        
        this.sendEmailApprovatori();
        this.sendEmailUffPersonale();
        
        
	}
	
	public void sendEmailUffPersonale() {
		boolean esistePermessoStatus1= permessoRepo.existsByStatus(1);
		boolean esistePermessoStatus2= permessoRepo.existsByStatus(2);
		boolean esistePermessoStatus3= permessoRepo.existsByStatus(3);
		
		if(esistePermessoStatus1 || esistePermessoStatus2 || esistePermessoStatus3) {
			System.out.println("emailPersonale trovata: ");

			List<String> emailUffPersonale = utenteRepo.findEmailVigilfuocoByRuolo("PERSONALE");
			for(String emailPersonale : emailUffPersonale) {
				System.out.println("emailPersonale trovata: " + emailPersonale);
		        CompletableFuture.runAsync(() -> {
		            try {
		            	this.sendEmailPermessiDaApprovare(emailPersonale);
		            } catch (Exception e) {
		                System.err.println("Failed to send email: " + e.getMessage());
		            }
		        });

			}
		}

	}
	
	public void sendEmailApprovatori() {
		List<Permesso> permessiInAttesa = permessoRepo.findPermessoByStatus(0);
        ArrayList<String> emailApprovatoriTrovate = new ArrayList<>();
        
        for (Permesso permesso : permessiInAttesa) {
            System.out.println("Element: " + permesso.toString());
            String emailApprovatore = permesso.getUtenteApprovazione().getEmailVigilfuoco();
            

            if (contains(emailApprovatoriTrovate, emailApprovatore)) {
                System.out.println("The array contains " + emailApprovatore);
            } else {
                System.out.println("The array does not contain " + emailApprovatore);
                emailApprovatoriTrovate.add(emailApprovatore);
                
            }
            
            if(permesso.getUtenteApprovazioneDue()!=null) {
            	String emailApprovatoreDue = permesso.getUtenteApprovazioneDue().getEmailVigilfuoco();
                if (contains(emailApprovatoriTrovate, emailApprovatoreDue)) {
                    System.out.println("The array contains " + emailApprovatoreDue);
                } else {
                    emailApprovatoriTrovate.add(emailApprovatoreDue);
                    
                }
            }

        }
        
        for (String emailApprovatore: emailApprovatoriTrovate) {
        	
	        CompletableFuture.runAsync(() -> {
	            try {
	            	this.sendEmailPermessiDaApprovare(emailApprovatore);
	            } catch (Exception e) {
	                System.err.println("Failed to send email: " + e.getMessage());
	            }
	        });
	        
        	System.out.println("Email trovata : "+emailApprovatore);
        }
	}
	
    public static boolean contains(ArrayList<String> emailApprovatori, String emailTarget) {
        for (String email : emailApprovatori) {
            if (email == emailTarget) {
                return true;
            }
        }
        return false;
    }
    
	public void sendEmailPermessiDaApprovare(String emailApprovatore) {
		// Sender's email address
        String from = "informatica.verona@vigilfuoco.it";
        // Sender's password
        String password = "Ict-Nas2024";
        // Receiver's email address
        //String to = "ilenia.mannino@vigilfuoco.it";
        String to = emailApprovatore;

        // Setup mail server properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.host", "smtp-s.vigilfuoco.it");
        props.setProperty("mail.smtp.port", "465");




        try {
        	
            // Get the Session object
            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
            
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("no-reply@vigilfuoco.it"));
            


            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
            String oggetto= "GestioneFerie - hai permessi da approvare!" ;
            message.setSubject(oggetto);

            // Set the actual message
            String messaggio = "Hai nuovi permessi da approvare, controlla su GestioneFerie!";
            message.setText(messaggio);


            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
	}
	
    
}
