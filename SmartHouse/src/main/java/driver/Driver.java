package driver;

import java.io.Serializable;
import java.util.Properties;

@EquipmentPropertiesRequired(value = {})
public interface Driver extends Serializable {
	/**
	 * Récupération du paramétrage du driver pour l'instance en cours.
	 * 
	 * @return
	 */
	public Properties getParams();

	/**
	 * Identifiant de l'équipement configuré pour le driver Donnée configurée
	 * (dynamique)
	 * 
	 * @return
	 */
	public String getHardwareIdentifier();

	/**
	 * Nom de l'équipement correspondant au driver Donnée statique
	 * 
	 * @return
	 */
	public String getHardwareName();

	/**
	 * Description du driver Donnée statique
	 * 
	 * @return
	 */
	public String getHardwareDescription();

	/**
	 * Fabricant du matériel Donnée statique
	 * 
	 * @return
	 */
	public String getHardwareManufacturer();

	/**
	 * Version du driver Donnée statique
	 * 
	 * @return
	 */
	public String getDriverVersion();

	/**
	 * Retourne l'etat du driver
	 * 
	 * @return
	 */
	public String getState();

	/**
	 * Méthode de test de l'équipement permettant de vérifier un paramétrage
	 * d'équipement.
	 * 
	 * @return
	 */
	public boolean testEquipment();

}
