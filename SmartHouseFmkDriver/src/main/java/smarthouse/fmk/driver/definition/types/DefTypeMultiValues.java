package smarthouse.fmk.driver.definition.types;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface DefTypeMultiValues  {
	/**
	 * Liste des valeurs attendues en paramètre
	 */
	String[] values();
	
	/**
	 * Libellé des valeurs destiné à l'IHM
	 */
	String[] labels();
}
