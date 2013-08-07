package smarthouse.fmk.driver.definition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import smarthouse.fmk.driver.definition.types.ParamType;


/**
 * Réalisée le 26 sept. 2012
 * 
 * Annotation à utiliser en complément de EquipmentAction
 * Elle permet de définir les caractéristiques de chaque paramètre.
 * 
 * @author Florent
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface DefParam {
	/**
	 * Nom destiné à l'IHM du paramètre
	 */
	String name() default "Not specified";

	/**
	 * Description destinée à l'IHM du paramètre
	 */
	String description() default "Not specified";

	/**
	 * Type du paramètre qui est utilisé
	 * Chaque constante correspond à un type en annotation.
	 */
	ParamType type();
}


