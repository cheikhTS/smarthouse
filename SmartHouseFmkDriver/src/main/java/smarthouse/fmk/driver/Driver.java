package smarthouse.fmk.driver;

import java.util.Properties;

import smarthouse.fmk.driver.definition.DefAction;
import smarthouse.fmk.driver.definition.DefPropertiesRequired;

@DefPropertiesRequired(value = {})
public interface Driver {
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
	public String getHardwareIdentifier() throws DriverException;

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
	public String getState() throws DriverException;

	/**
	 * Méthode de test de l'équipement permettant de vérifier un paramétrage
	 * d'équipement.
	 * 
	 * @return
	 */
	@DefAction(name="Test de l'équipement", description="Permet de contrôler la configuration d'un équipement.")
	public boolean testEquipment() throws DriverException;

}
