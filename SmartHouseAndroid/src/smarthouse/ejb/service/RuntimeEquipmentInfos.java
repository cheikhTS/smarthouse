
package smarthouse.ejb.service;

import java.util.ArrayList;
import java.util.List;
import org.jinouts.xml.bind.annotation.XmlAccessType;
import org.jinouts.xml.bind.annotation.XmlAccessorType;
import org.jinouts.xml.bind.annotation.XmlElement;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour runtimeEquipmentInfos complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="runtimeEquipmentInfos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actions" type="{http://service.ejb.smarthouse/}runtimeActionDef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="config" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driverVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hardwareDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hardwareIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hardwareManufacturer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hardwareName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "runtimeEquipmentInfos", propOrder = {
    "actions",
    "config",
    "driverVersion",
    "hardwareDescription",
    "hardwareIdentifier",
    "hardwareManufacturer",
    "hardwareName",
    "id",
    "name",
    "state"
})
public class RuntimeEquipmentInfos {

    @XmlElement(nillable = true)
    protected List<RuntimeActionDef> actions;
    protected String config;
    protected String driverVersion;
    protected String hardwareDescription;
    protected String hardwareIdentifier;
    protected String hardwareManufacturer;
    protected String hardwareName;
    protected int id;
    protected String name;
    protected String state;

    /**
     * Gets the value of the actions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RuntimeActionDef }
     * 
     * 
     */
    public List<RuntimeActionDef> getActions() {
        if (actions == null) {
            actions = new ArrayList<RuntimeActionDef>();
        }
        return this.actions;
    }

    /**
     * Obtient la valeur de la propri�t� config.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfig() {
        return config;
    }

    /**
     * D�finit la valeur de la propri�t� config.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfig(String value) {
        this.config = value;
    }

    /**
     * Obtient la valeur de la propri�t� driverVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverVersion() {
        return driverVersion;
    }

    /**
     * D�finit la valeur de la propri�t� driverVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverVersion(String value) {
        this.driverVersion = value;
    }

    /**
     * Obtient la valeur de la propri�t� hardwareDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareDescription() {
        return hardwareDescription;
    }

    /**
     * D�finit la valeur de la propri�t� hardwareDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareDescription(String value) {
        this.hardwareDescription = value;
    }

    /**
     * Obtient la valeur de la propri�t� hardwareIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareIdentifier() {
        return hardwareIdentifier;
    }

    /**
     * D�finit la valeur de la propri�t� hardwareIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareIdentifier(String value) {
        this.hardwareIdentifier = value;
    }

    /**
     * Obtient la valeur de la propri�t� hardwareManufacturer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareManufacturer() {
        return hardwareManufacturer;
    }

    /**
     * D�finit la valeur de la propri�t� hardwareManufacturer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareManufacturer(String value) {
        this.hardwareManufacturer = value;
    }

    /**
     * Obtient la valeur de la propri�t� hardwareName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHardwareName() {
        return hardwareName;
    }

    /**
     * D�finit la valeur de la propri�t� hardwareName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHardwareName(String value) {
        this.hardwareName = value;
    }

    /**
     * Obtient la valeur de la propri�t� id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * D�finit la valeur de la propri�t� id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propri�t� name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * D�finit la valeur de la propri�t� name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtient la valeur de la propri�t� state.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * D�finit la valeur de la propri�t� state.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

	public void setActions(List<RuntimeActionDef> actions) {
		this.actions = actions;
	}

}
