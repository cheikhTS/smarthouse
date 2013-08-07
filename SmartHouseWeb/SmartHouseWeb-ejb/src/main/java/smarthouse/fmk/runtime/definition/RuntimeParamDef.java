package smarthouse.fmk.runtime.definition;

import java.io.Serializable;
import java.util.Map;

import smarthouse.fmk.driver.definition.types.ParamType;

public class RuntimeParamDef implements Serializable {
	/**
	 * Nom IHM du paramètre
	 */
	private String name;
	
	/**
	 * Description du paramètre
	 */
	private String description;
	
	/**
	 * Type du paramètre
	 */
	private String type;
	
	/**
	 * Liste des valeurs à retourner (synchronisées avec la tableau labels[])
	 * MULTI_VALUES
	 */
	private String[] values;
	
	/**
	 * Liste des libellés
	 * MULTI_VALUES
	 */
	private String[] labels;
	
	/**
	 * Borne entière minimale
	 * LIMITED_STRING : taille
	 * LIMITED_INTEGER : nombre
	 */
	private Integer min;
	
	/**
	 * Borne entière maximale
	 * LIMITED_STRING : taille
	 * LIMITED_INTEGER : nombre
	 */
	private Integer max;
	
	/**
	 * Borne minimale 
	 * LIMITED_FLOAT : nombre
	 */
	private Float floatMin;
	
	/**
	 * Borne maximale
	 * LIMITED_FLOAT : nombre
	 */
	private Float floatMax;
	
	/**
	 * Pas
	 * LIMITED_FLOAT : nombre
	 */
	private Float step;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Float getFloatMin() {
		return floatMin;
	}

	public void setFloatMin(Float floatMin) {
		this.floatMin = floatMin;
	}

	public Float getFloatMax() {
		return floatMax;
	}

	public void setFloatMax(Float floatMax) {
		this.floatMax = floatMax;
	}

	public Float getStep() {
		return step;
	}

	public void setStep(Float step) {
		this.step = step;
	}
	
	
}
