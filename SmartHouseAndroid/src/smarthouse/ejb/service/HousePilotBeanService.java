package smarthouse.ejb.service;

import java.net.MalformedURLException;
import java.net.URL;
import org.jinouts.xml.namespace.QName;
import org.jinouts.xml.ws.WebEndpoint;
import org.jinouts.xml.ws.WebServiceClient;
import org.jinouts.xml.ws.WebServiceFeature;
import org.jinouts.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.3
 * 2013-03-06T17:27:11.012+01:00
 * Generated source version: 2.7.3
 * 
 */
@WebServiceClient(name = "HousePilotBeanService", 
                  wsdlLocation = "http://192.168.25.1:8080/SmartHouseWeb-ejb-1.0.0/HousePilot?wsdl",
                  targetNamespace = "http://service.ejb.smarthouse/") 
public class HousePilotBeanService extends org.jinouts.ws.JinosService {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://service.ejb.smarthouse/", "HousePilotBeanService");
    public final static QName HousePilotPort = new QName("http://service.ejb.smarthouse/", "HousePilotPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.25.1:8080/SmartHouseWeb-ejb-1.0.0/HousePilot?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HousePilotBeanService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.25.1:8080/SmartHouseWeb-ejb-1.0.0/HousePilot?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public HousePilotBeanService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HousePilotBeanService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HousePilotBeanService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns HousePilot
     */
    @WebEndpoint(name = "HousePilotPort")
    public HousePilot getHousePilotPort() {
        return super.getPort(HousePilotPort, HousePilot.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HousePilot
     */
    @WebEndpoint(name = "HousePilotPort")
    public HousePilot getHousePilotPort(WebServiceFeature... features) {
        return super.getPort(HousePilotPort, HousePilot.class, features);
    }

}