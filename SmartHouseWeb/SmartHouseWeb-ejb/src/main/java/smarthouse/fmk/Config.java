package smarthouse.fmk;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private static Logger logger = LoggerFactory.getLogger(Config.class);
	private static Properties props = null;

	/**
	 * Initialisation static de la classe. Permet de charger les attribus classe
	 * a partir du fichier Config. En cas d'erreur on laisse les parametres par
	 * defaut.
	 */
	static {

		try {
			InputStream input = Config.class.getResourceAsStream("/config.properties");
			
			if(input != null) {
				props = new Properties();
				props.load(input);
				input.close();
			}
			else {
				logger.warn("Lancement de la configuration par defaut");
				props = null;
			}
		} 
		catch (Exception e) {
			logger.error("Lancement de la configuration par defaut");
			props = null;
		}
		
		// Config par défaut
		if(props == null) {
			props = new Properties();
			props.put("source.jpa.persistenceName", "SmartHouse");
			props.put("smarthouse.drivers.dir", "/usr/jboss/smarthouse/");
		}
		
	}

	public static Properties getProps() {
		return props;
	}
	
	/**
	 * Retourne le dossier des drivers répertorié dans la liste et existant sur le système.
	 * Si aucun n'est trouvé, il renvoi le 1er répertoire défini.
	 * @return
	 */
	public static String getDriverDirectory() {
		for(int i = 1; i < 10; i++) {
			String path = props.getProperty("smarthouse.drivers.dir."+i);
			if(path != null && new File(path).exists()) {
				return path;
			}
		}
		
		return props.getProperty("smarthouse.drivers.dir.1");
	}
}
