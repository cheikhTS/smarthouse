package smarthouse.ejb.service.remote;

import javax.ejb.Remote;

@Remote
public interface Authentication {
	/**
	 * Méthode de test de contact avec le serveur.
	 * @return
	 */
	public String ping();
	
	/**
	 * Méthode d'authentification avec le serveur.
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password);
}
