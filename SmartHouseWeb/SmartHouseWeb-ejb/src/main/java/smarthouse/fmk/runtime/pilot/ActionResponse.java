package smarthouse.fmk.runtime.pilot;

import java.io.Serializable;

public class ActionResponse implements Serializable {

	public enum RequestResult { 
		/**
		 * L'action s'est bien exécutée
		 */
		OK,
		
		/**
		 * Une exception s'est déclenchée à l'exécution de l'action
		 */
		ERR_ON_EXECUTE,
		
		/**
		 * L'équipement sur lequel l'action est exécutée n'existe pas
		 */
		ERR_EQUIPMENT_NOT_FOUND,
		
		/**
		 * L'équipement sur lequel l'action est exécutée a un problème avec son driver
		 * Cas connu :
		 * - Driver non instancié car exception à son initialisation
		 * - Action avec un type de paramètre non géré (autre que String,Integer,Float,Boolean)
		 */
		ERR_DRIVER,
		
		/**
		 * L'action demandée n'existe pas
		 */
		ERR_ACTION_NOT_FOUND,
		
		/**
		 * Les paramètres sont invalides
		 */
		ERR_INVALID_PARAMS,
		
		/**
		 * Erreur interne provenant du serveur
		 * Cette erreur peut intervenir quand la base de données n'est plus accessible
		 * ou que celle-ci n'est pas configurée.
		 */
		ERR_SERVER;
	}

	/**
	 * Etat de réponse suite à la requête envoyée
	 */ 
	private String result;
	
	/**
	 * Valeur retournée par l'exécution de l'action
	 */
	private String value;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
