package smarthouse.fmk.driver.definition.types;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface DefTypeLimitedFloat {
	/**
	 * Valeur minimale
	 */
	float min();
	
	/**
	 * Valeur maximale
	 */
	float max();
	
	/**
	 * Pas
	 */
	float step();
}
