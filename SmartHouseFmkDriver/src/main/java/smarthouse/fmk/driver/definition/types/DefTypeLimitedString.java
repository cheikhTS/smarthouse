package smarthouse.fmk.driver.definition.types;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface DefTypeLimitedString {
	/**
	 * Taille minimale de la chaîne (nbre de caractères)
	 */
	int minSize();
	
	/**
	 * Taille maximale de la chaîne (nbre de caractères)
	 */
	int maxSize();
}
