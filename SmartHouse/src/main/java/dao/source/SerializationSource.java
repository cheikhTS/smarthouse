package dao.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.domain.Home;
import pojo.pattern.Scenario;

public class SerializationSource extends DaoSource {

	private static final long serialVersionUID = 1105996289309239096L;
	private static Logger logger = LoggerFactory.getLogger(SerializationSource.class);

	/**
	 * Source de données (read/write)
	 */
	private File source;

	/**
	 * Données effectives en jvm
	 */
	private SerializationData data;

	/**
	 * Sous classe conteneur des objets devant être serialisés par la source.
	 */
	public class SerializationData implements Serializable {

		private static final long serialVersionUID = -9088311987427401818L;

		// Centralisation de tout les objets a manipuler.
		private ArrayList<Home> homes = new ArrayList<Home>();
		private ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

		public SerializationData () {
		}

		public ArrayList<Home> getHomes() {
			return homes;
		}

		public void setHomes(ArrayList<Home> homes) {
			this.homes = homes;
		}

		public ArrayList<Scenario> getScenarios() {
			return scenarios;
		}

		public void setScenarios(ArrayList<Scenario> scenarios) {
			this.scenarios = scenarios;
		}

		@Override
		public String toString() {
			int nbHomes = (homes != null) ? homes.size() : 0;
			int nbScenarios = (scenarios != null) ? scenarios.size() : 0;
			return nbHomes + " object Home, " + nbScenarios + " object Scenario.";
		}
	}

	public SerializationSource ( Properties props ) {
		super(props);
	}

	public void initialize(Properties prop) {
		source = new File(prop.getProperty("source.serialization.file"));
		load();
	}

	public SerializationData getData() {
		return data;
	}

	/**
	 * Fonction de sauvegarde par serialization java vers le fichier
	 */
	public void save() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			// flux fichier
			fos = new FileOutputStream(source);

			// flux objet imbriqué dans flux fichier
			oos = new ObjectOutputStream(fos);

			// sérialisation : écriture de l'objet dans le flux de sortie
			oos.writeObject(data);

			// on vide le tampon pour forcer l'écriture
			oos.flush();
		} catch (IOException ioe) {
			logger.error("Error while saving serialization source file " + source.getName());
			ioe.printStackTrace();
		} finally {
			// fermeture flux
			try {
				oos.close();
				fos.close();
			}
			// qq soit le pb, osef dans notre cas
			catch (Exception e) {
			}
		}

	}

	/**
	 * Fonction de chargement par deserialisation java depuis un fichier
	 */
	public void load() {
		ObjectInputStream ois = null;
		FileInputStream fis = null;

		try {
			// flux fichier
			fis = new FileInputStream(source);

			// création d'un "flux objet" avec le flux fichier
			ois = new ObjectInputStream(fis);

			// désérialisation : lecture de l'objet depuis le flux d'entrée
			data = (SerializationData) ois.readObject();
			logger.debug("SerializationData loaded from " + source.getName() + " : " + data);

			// màj de l'état de la source
			setInitialized(true);
		} catch (IOException ioe) {
			logger.warn("Serialization source file " + source.getName() + " missing or corrupted");
			ioe.printStackTrace();
			initializeSource();
		} catch (ClassNotFoundException e) {
			logger.warn("Serialization source file " + source.getName() + " corrupted : " + e.getMessage());
			e.printStackTrace();
			initializeSource();
		} finally {
			// fermeture des flux
			try {
				ois.close();
				fis.close();
			}
			// qq soit le pb, osef dans notre cas
			catch (Exception e) {
			}
		}
	}

	/**
	 * Fonction d'initialisation dans le cas où le fichier source n'existerait
	 * pas encore ou que le fichier serait corrompu. Attention, l'appel à cette
	 * méthode dans le cas d'un fichier source existant, entrainera son
	 * renommage avec l'extension .bak et la creation d'un nouveau fichier vide.
	 */
	public void initializeSource() {
		logger.debug("Initialization of serialization source " + source.getName());

		try {
			String path = source.getAbsolutePath();

			// on backup l'ancien s'il existe (on admet qu'il y a un pb avec
			// si cette méthode est appelée)
			if ( source.exists() ) {
				source.renameTo(new File(path + ".bak"));
			}

			// création nouveau fichier
			source = new File(path);
			source.createNewFile();

			// première init du serializable
			data = new SerializationData();

			// màj de l'état de la source
			setInitialized(true);
			// majidAI();
			// premier record
			save();
		} catch (IOException e) {
			logger.error("Error while initializing serialization source file");
		}

		logger.debug("Intialization of serialization source succeed");
	}

}
