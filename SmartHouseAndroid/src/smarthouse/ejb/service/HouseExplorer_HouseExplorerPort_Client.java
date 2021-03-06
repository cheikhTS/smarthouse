
package smarthouse.ejb.service;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.jinouts.xml.namespace.QName;
import org.jinouts.jws.WebMethod;
import org.jinouts.jws.WebParam;
import org.jinouts.jws.WebResult;
import org.jinouts.jws.WebService;
import org.jinouts.xml.bind.annotation.XmlSeeAlso;
import org.jinouts.xml.ws.RequestWrapper;
import org.jinouts.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.3
 * 2013-03-06T10:54:19.133+01:00
 * Generated source version: 2.7.3
 * 
 */
public final class HouseExplorer_HouseExplorerPort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.ejb.smarthouse/", "HouseExplorerBeanService");

    private HouseExplorer_HouseExplorerPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = HouseExplorerBeanService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        HouseExplorerBeanService ss = new HouseExplorerBeanService(wsdlURL, SERVICE_NAME);
        HouseExplorer port = ss.getHouseExplorerPort();  
        
        {
        System.out.println("Invoking getHome...");
        try {
            smarthouse.ejb.service.Home _getHome__return = port.getHome();
            System.out.println("getHome.result=" + _getHome__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getEquipmentInfos...");
        int _getEquipmentInfos_arg0 = 0;
        try {
            smarthouse.ejb.service.RuntimeEquipmentInfos _getEquipmentInfos__return = port.getEquipmentInfos(_getEquipmentInfos_arg0);
            System.out.println("getEquipmentInfos.result=" + _getEquipmentInfos__return);

        } catch (Exception_Exception e) { 
            System.out.println("Expected exception: Exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
