package smarthouse.fmk.runtime;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.fmk.Config;
import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;

/**
 * Singleton factory of driver equipment
 * 
 * @author Florent
 * 
 */
public class DriverFactory implements FileFilter {
	
	private Logger logger = LoggerFactory.getLogger(DriverFactory.class);
	
	/**
	 * Class loader ayant les bundles de nos drivers
	 */
	private ClassLoader classLoader = null;
	
	/**
	 * Liste des drivers disponibles 
	 */
	private List<String> listDrivers = null;
	
	private Map<Integer, Driver> listDriverInstances = null;
	
	/**
	 * Instance singleton
	 */
	private static DriverFactory instance;

	private DriverFactory() {
		listDriverInstances = new HashMap<Integer, Driver>();
	}

	/**
	 * Singleton access
	 * 
	 * @return
	 */
	public static DriverFactory getInstance() {
		if (instance == null) {
			instance = new DriverFactory();
		}

		return instance;
	}

	/**
	 * Renvoi le driver correspondant
	 * @param className Nom complet de la classe implémentant Driver
	 * @param prop Paramètres de configuration
	 * @return Instance du driver correspondant.
	 * @throws Exception
	 */
	public Driver getDriver(String className, Properties prop) throws DriverFactoryException, DriverException {
		if(listDrivers == null || listDrivers.isEmpty()) {
			logger.error("No driver available in driver factory !");
			throw new DriverFactoryException("No driver available in driver factory !");
		}
		
		Driver instance = listDriverInstances.get(prop.hashCode());
		
		if(instance == null) {
			logger.info("Instanciation du driver "+className);
			try {
				// Chargement de la classe du driver
				Class<?> driver = Class.forName(className, true, classLoader);
				
				// Instanciation du driver avec les paramètres passés
				instance = (Driver) driver.getDeclaredConstructor(Properties.class).newInstance(prop);
				
				// ajout en cache
				listDriverInstances.put(prop.hashCode(), instance);
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new DriverException("Error while instanciating driver " + className + " : "+e.getClass().getSimpleName()+" / "+ e.getMessage());
			}
		}
		else {
			logger.info("Récupération d'une instance du driver "+className);
		}

		return instance;
	}

	/**
	 * Fonction de chargement des drivers en bundle contenus dans le dossier des
	 * drivers défini dans la configuration du framework.
	 * @throws DriverFactoryException
	 */
	public void loadDrivers() throws DriverFactoryException {
		// Réinitialisation de notre liste de drivers disponible
		listDrivers = new ArrayList<String>();
		
		String jarsDirPath = Config.getDriverDirectory();
		if(jarsDirPath == null || jarsDirPath.trim().isEmpty()) {
			throw new DriverFactoryException("No drivers directory configured.");
		}
		
		// Test de la configuration du dossier de drivers
		File jarsDir = new File(jarsDirPath);
		if(!jarsDir.exists()) {
			logger.warn("Drivers directory ("+jarsDirPath+") does not exist, mkdir try.");
			jarsDir.mkdirs();
		}
		else {
			// récupération des jars à traiter
			File[] jars = jarsDir.listFiles(this);
			
			// class loading process si au moins un jar est dans le dossier.
			if(jars != null && jars.length > 0) {
				// passage en liste
				List<URL> listJarsUrlized = new LinkedList<URL>();

				// filtrage des jars ne contenant pas de drivers.
				// et passage en URL
				for(int i = 0; i < jars.length; i++) {
					try {
						// Chargement temporaire dans un classloader pour tester les classes
						URL urlJar = jars[i].toURI().toURL();
						URLClassLoader tmpLoader = new URLClassLoader(new URL[] { urlJar }, getClass().getClassLoader());
						
						// passage en jar => exception si ce n'est pas le cas (puis skip)
						JarFile jar = new JarFile(jars[i]);
						
						// Listage des drivers disponibles dans le jar (sert aussi à savoir si le jar va être gardé en class loader ou non)
						List<String> listJarDrivers = new ArrayList<String>();
						
						// Est-ce qu'un driver est dans le jar ?
						Enumeration<JarEntry> ite = jar.entries();
						while(ite.hasMoreElements()) {
							JarEntry entry = ite.nextElement();
							
							// Test des classes seulement
							if(entry.getName().endsWith(".class")) {
								String className = entry.getName().replace('/', '.').replace(".class", "");
								
								// Test de non conflit avec un autre bundle
								if(listDrivers.contains(className)) {
									logger.error("Driver "+className+" from "+jars[i].getName()+" is already in another bundle, bundle skipped.");
									
									// annulation du chargement des drivers valides du jar
									listJarDrivers.clear();
									
									// passage au bundle suivant
									break;
								}
								else {
									// Passage sur chaque classe pour vérifier l'implémentation de l'interface Driver.
									Class<?> classObj = Class.forName(className, true, tmpLoader);
									for(Class<?> interfaces : classObj.getInterfaces()) {
										// Test de l'implémentation de l'interface
										if(Driver.class.hashCode() == interfaces.hashCode()) {
											logger.info("SmartHouse driver "+className+" found in "+jars[i].getName()+".");
											listJarDrivers.add(className);
										}
										/* hashCode test tjr vrai
										else if(Driver.class.getName().equals(interfaces.getSimpleName())) {
											logger.error("Bundle driver "+jars[i].getName()+" has obsolete driver "+className+", bundle skipped.");
											
											// annulation du chargement des drivers valides du jar
											listJarDrivers.clear();
											
											// arrêt boucle.
											break;
										}*/
									}
								}
							}
						}
						
						if(!listJarDrivers.isEmpty()) {
							// on garde le jar pour qu'il soit charger en next step.
							listJarsUrlized.add(urlJar);
							// on met en cache la liste des drivers
							listDrivers.addAll(listJarDrivers);
							logger.info("Drivers bundle "+jars[i].getName()+" will be load.");
						}
					} 
					catch (IOException e) {
						logger.error("Error on "+jars[i].getName()+" archive loading, bundle skipped (exception : "+e.getClass().getSimpleName()+" / "+e.getMessage()+").");
					} 
					catch (ClassNotFoundException e) {
						logger.error("Error while testing classes from "+jars[i].getName()+" archive, bundle skipped (exception : "+e.getClass().getSimpleName()+" / "+e.getMessage()+").");
					}
					catch (NoClassDefFoundError e) {
						logger.error("Error while testing classes from "+jars[i].getName()+" archive, bundle skipped (exception : "+e.getClass().getSimpleName()+" / "+e.getMessage()+").");
					}
				}
				
				// des jars sont à chargés ?
				if(!listJarsUrlized.isEmpty()) {
					// Passage en tableau pour l'appel de la méthode en next step.
					URL[] jarsUrlized = listJarsUrlized.toArray(new URL[0]);
			
					// création du classloader étendu avec nos drivers.
					classLoader = new URLClassLoader(jarsUrlized, getClass().getClassLoader());
					logger.info("Driver factory ready with "+listDrivers.size()+" drivers.");
				}
			}
		}
		
		// Pas de drivers.
		if(listDrivers == null || listDrivers.isEmpty()) {
			logger.warn("Driver factory ready but no driver available.");
		}
	}
	
	/**
	 * Filtre des bundle de drivers
	 */
	@Override
	public boolean accept(File pathname) {
		return pathname.isFile() && pathname.getName().endsWith(".jar");
	}
}
