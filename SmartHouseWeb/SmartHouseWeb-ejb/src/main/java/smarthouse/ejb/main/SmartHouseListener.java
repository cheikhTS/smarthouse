package smarthouse.ejb.main;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.fmk.runtime.DriverFactory;
import smarthouse.fmk.runtime.DriverFactoryException;

@Startup
@Singleton
public class SmartHouseListener {
	
	private Logger logger = LoggerFactory.getLogger(SmartHouseListener.class);
	
	@PostConstruct
	void atStartup() {
		logger.info("Démarrage de l'application SmartHouse");
		
		try {
			DriverFactory.getInstance().loadDrivers();
		} 
		catch (DriverFactoryException e) {
			logger.error("Erreur au chargement de l'application");
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	@PreDestroy
	void atShutdown() {
		logger.info("Arrêt de l'application SmartHouse");
	}
}