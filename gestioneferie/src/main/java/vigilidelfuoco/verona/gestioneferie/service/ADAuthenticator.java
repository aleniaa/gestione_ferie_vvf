

package vigilidelfuoco.verona.gestioneferie.service;


import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.stereotype.Service;
import javax.naming.directory.Attribute;


@Service
public class ADAuthenticator {



	public ADAuthenticator() throws Exception {}

	public String authenticateUser(String username, String password)
			throws Exception {

		String searchFilter = "(&(objectClass=user)(" + "userPrincipalName" + "=" + username + "))";
		SearchControls searchCtls = new SearchControls();

		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchBase = "DC=dipvvf,DC=it";
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		environment.put(Context.PROVIDER_URL, "ldap://gc-lan-ced.dipvvf.it:389");
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, username);
		environment.put(Context.SECURITY_CREDENTIALS, password);

		try {
			
			String accountDipvvf="";
			LdapContext ctx = new InitialLdapContext(environment, null);

			NamingEnumeration<SearchResult> answer = ctx.search(searchBase,searchFilter, searchCtls);

			if (answer.hasMoreElements()) {

				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					NamingEnumeration<? extends Attribute> attrEnum = attrs.getAll();
					while (attrEnum.hasMore()) {
				        Attribute attribute = attrEnum.next();
				        String attributeName = attribute.getID(); // Get attribute name
				        // Get all attribute values for the current attribute
				        NamingEnumeration<?> attrValues = attribute.getAll();
				        while (attrValues.hasMore()) {
				            Object value = attrValues.next();
				            //System.out.println(attributeName + ": " + value); // Print attribute name and value
				            if(attributeName.equals("userPrincipalName")) {
					            System.out.println(attributeName + ": " + value); // Print attribute name and value
					            accountDipvvf= value.toString();
				            }
				        }
					}
					//return "" + 0;
					System.out.println("Msono dentro asuthenticatoro e accountdipvvf: "+accountDipvvf);
					return accountDipvvf;
				}
			}

			return "" + -3;

		}

		catch (Exception e) {

			//Debug.error(this, e.getMessage() + " - UTENTE: " + username + " "  , null);
			if (e.getClass().equals(new AuthenticationException().getClass())) {
				AuthenticationException eee = (AuthenticationException) e;
				// Debug.error(this,eee.getMessage(),null);
				// codici Errore autenticazione ldap
				/*
				 * 525 user not found 52e invalid credentials 530 not permitted
				 * to logon at this time 531 not permitted to logon at this
				 * workstation 532 password expired 533-534 account disabled.The
				 * user has not been granted the requested logon type at this
				 * machine 701 account expired 773 user must reset password 775
				 * user account locked
				 */

				String errorCode = "-1";
				System.out.println("errorcode -1");
				try {
					errorCode = eee.getMessage().substring(eee.getMessage().indexOf("error, data ") + 12,eee.getMessage().lastIndexOf(","));
					System.out.println(errorCode);

				} catch (Exception e2) {
					return errorCode;
				}

				return errorCode;
			} else if (e.getClass().equals(new javax.naming.CommunicationException().getClass())) {
				return "" + -2;
			} else {
				return "" + -1;
			}
		}

	}
}
