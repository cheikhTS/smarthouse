/**
 * 
 */
package main;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Florent
 * 
 */
public class LogExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure(LogExample.class.getResource("/log4j.xml"));

		Logger logger = LoggerFactory.getLogger(Main.class);
		logger.debug("TEST DEBUG LOG");
		logger.info("TEST INFO LOG");
		logger.warn("TEST WARN LOG");
		logger.trace("TEST TRACE LOG");
		logger.error("TEST ERROR LOG");
	}

}
