
package smarthouse.ejb.service;

import java.io.Serializable;

import org.jinouts.xml.bind.annotation.XmlAccessType;
import org.jinouts.xml.bind.annotation.XmlAccessorType;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour driverIdentifier complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="driverIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="driverName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driverPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "driverIdentifier", propOrder = {
    "driverName",
    "driverPath"
})
public class DriverIdentifier implements Serializable{

    protected String driverName;
    protected String driverPath;

    /**
     * Obtient la valeur de la propri�t� driverName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * D�finit la valeur de la propri�t� driverName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverName(String value) {
        this.driverName = value;
    }

    /**
     * Obtient la valeur de la propri�t� driverPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverPath() {
        return driverPath;
    }

    /**
     * D�finit la valeur de la propri�t� driverPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverPath(String value) {
        this.driverPath = value;
    }

}
