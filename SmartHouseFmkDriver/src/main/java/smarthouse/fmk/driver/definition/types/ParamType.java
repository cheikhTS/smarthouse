package smarthouse.fmk.driver.definition.types;


/**
 * Les types de paramètre autorisés
 * 
 * @TODO Il faudrait en vrai revoir les paramètres pour avoir qqch de plus
 *       restrictif, par exemple: - un paramètre String multi-valeurs avec
 *       valeur IHM - un paramètre Integer avec borne min/max - un paramètre
 *       Float avec borne min/max et pas.
 */
public enum ParamType {
	MULTI_VALUES,
	LIMITED_STRING,
	LIMITED_INTEGER,
	LIMITED_FLOAT;
	
	public static ParamType valueOf(Class<?> type) {
		int hashCode = type.hashCode();
		
		// correspondance des types.
		if(hashCode == DefTypeLimitedString.class.hashCode()) {
			return LIMITED_STRING;
		}
		else if(hashCode == DefTypeMultiValues.class.hashCode()) {
			return MULTI_VALUES;
		}
		else if(hashCode == DefTypeLimitedFloat.class.hashCode()) {
			return LIMITED_FLOAT;
		}
		else if(hashCode == DefTypeLimitedInteger.class.hashCode()) {
			return LIMITED_INTEGER;
		}
		else {
			return null;
		}
	}
}