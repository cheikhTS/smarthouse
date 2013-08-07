/**
 * 
 */
package driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Réalisée le 30 sept. 2012
 * 
 * Annotation permettant de connaître les différentes clés devant être présentes
 * dans le Properties passé à l'instanciation du driver.
 * 
 * @author Florent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface EquipmentPropertiesRequired {
	String[] value();
}
