package main;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

	public static String DB_BASE = "db.sqlite";

	private static Properties props = null;

	/**
	 * Initialisation static de la classe. Permet de charger les attribus classe
	 * a partir du fichier Config. En cas d'erreur on laisse les parametres par
	 * defaut.
	 */
	static {
		FileInputStream configFile = null;
		try {
			configFile = new FileInputStream("config.properties");
			props = new Properties();
			props.load(configFile);
			configFile.close();
			DB_BASE = props.getProperty("DB_BASE") != null ? props.getProperty("DB_BASE") : DB_BASE;
		} catch (Exception e) {
			System.out.println("Lancement de la configuration par defaut");
		}
	}

	public static Properties getProps() {
		return props;
	}
}
