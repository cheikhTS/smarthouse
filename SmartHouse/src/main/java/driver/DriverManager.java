package driver;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarFile;

import main.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.domain.DriverIdentifier;

public class DriverManager {
	public final static Class DRIVERCLASS = Driver.class;
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	public static List<DriverIdentifier> driversIdentifier = new ArrayList<DriverIdentifier>();

	/**
	 * Chargement du driver dans le clathPath
	 * 
	 * @param driverPath chemin du fichier pilote (.jar)
	 * @return
	 */
	public static void loadDriver(String driverPath) {
		File f = new File(driverPath);
		if ( !f.exists() ) {
			System.err.println("Impossible de trouver : " + driverPath);
			return;
		}

		// Chargement du jar dans la classpath
		try {
			URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			URL urls[] = sysLoader.getURLs();
			for ( int i = 0; i < urls.length; i++ ) {
				if ( urls[i].toString().equalsIgnoreCase(f.toURI().toURL().toString()) ) { return; }
			}
			Class sysclass = URLClassLoader.class;

			Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysLoader, new Object[] { f.toURI().toURL() });

			// Referencement des differents drivers
			JarFile jar = new JarFile(f.getAbsolutePath());
			String tmp = "";
			Enumeration enumeration = jar.entries();
			Class tmpClass = null;
			URL u = f.toURI().toURL();
			URLClassLoader loader = new URLClassLoader(new URL[] { u });
			while ( enumeration.hasMoreElements() ) {
				tmp = enumeration.nextElement().toString();
				if ( tmp.length() > 6 && tmp.substring(tmp.length() - 6).compareTo(".class") == 0 ) {
					tmp = tmp.substring(0, tmp.length() - 6);
					tmp = tmp.replaceAll("/", ".");
					tmpClass = Class.forName(tmp, true, loader);
					for ( Class cl : tmpClass.getInterfaces() ) {
						if ( cl.hashCode() == DRIVERCLASS.hashCode() ) {
							DriverIdentifier drivId = new DriverIdentifier(tmp, driverPath, tmpClass);
							driversIdentifier.add(drivId);
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de récupérer la liste des actions pouvant être invoquées sur un
	 * équipement par son driver.
	 * 
	 * @param driver Driver à traiter
	 * @return Liste de Method
	 */
	public static List<Method> getActionsAvailable(Driver driver) {
		List<Method> listEquipmentActions = new ArrayList<Method>(5);

		if ( driver != null ) {
			for ( Method method : driver.getClass().getDeclaredMethods() ) {
				if ( method.isAnnotationPresent(EquipmentAction.class) ) {
					listEquipmentActions.add(method);
				}
			}
		} else {
			logger.debug("Impossible de recuperer le driver");
		}

		return listEquipmentActions;
	}

	/**
	 * Permet de connaître les clés requises dans le properties passé à
	 * l'instanciation du driver
	 * 
	 * @param driverClass Classe du driver à traiter
	 * @return Liste des clés
	 */
	public static List<String> getPropertiesRequired(Class<?> driverClass) {
		EquipmentPropertiesRequired annotation = driverClass.getAnnotation(EquipmentPropertiesRequired.class);
		if ( annotation != null ) {
			return Arrays.asList(annotation.value());
		} else {
			return new LinkedList<String>();
		}
	}
}
