package smarthouse.fmk.driver.definition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Réalisée le 26 sept. 2012
 * 
 * Annotation permettant d'identifier dans un driver les méthodes
 * correspondantes à des actions pouvant être exécutées sur un équipement.
 * 
 * @author Florent
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface DefAction {
	/**
	 * Nom destinée à l'IHM de l'action ciblée Le but étant de pouvoir
	 * internationaliser le driver
	 */
	String name() default "Not specified";

	/**
	 * Description destinée à l'IHM de l'action ciblée Le but étant de pouvoir
	 * internationaliser le driver
	 */
	String description() default "Not specified";
}
