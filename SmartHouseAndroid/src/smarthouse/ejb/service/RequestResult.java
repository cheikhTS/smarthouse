
package smarthouse.ejb.service;

import org.jinouts.xml.bind.annotation.XmlEnum;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour requestResult.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="requestResult">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="ERR_ON_EXECUTE"/>
 *     &lt;enumeration value="ERR_EQUIPMENT_NOT_FOUND"/>
 *     &lt;enumeration value="ERR_DRIVER"/>
 *     &lt;enumeration value="ERR_ACTION_NOT_FOUND"/>
 *     &lt;enumeration value="ERR_INVALID_PARAMS"/>
 *     &lt;enumeration value="ERR_SERVER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "requestResult")
@XmlEnum
public enum RequestResult {

    OK,
    ERR_ON_EXECUTE,
    ERR_EQUIPMENT_NOT_FOUND,
    ERR_DRIVER,
    ERR_ACTION_NOT_FOUND,
    ERR_INVALID_PARAMS,
    ERR_SERVER;

    public String value() {
        return name();
    }

    public static RequestResult fromValue(String v) {
        return valueOf(v);
    }

}
