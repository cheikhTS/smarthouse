
package smarthouse.ejb.service;

import org.jinouts.xml.bind.JAXBElement;
import org.jinouts.xml.bind.annotation.XmlElementDecl;
import org.jinouts.xml.bind.annotation.XmlRegistry;
import org.jinouts.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the smarthouse.ejb.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExecuteAction_QNAME = new QName("http://service.ejb.smarthouse/", "executeAction");
    private final static QName _ExecuteActionResponse_QNAME = new QName("http://service.ejb.smarthouse/", "executeActionResponse");
    private final static QName _GetEquipmentInfos_QNAME = new QName("http://service.ejb.smarthouse/", "getEquipmentInfos");
    private final static QName _GetHomeResponse_QNAME = new QName("http://service.ejb.smarthouse/", "getHomeResponse");
    private final static QName _GetEquipmentInfosResponse_QNAME = new QName("http://service.ejb.smarthouse/", "getEquipmentInfosResponse");
    private final static QName _GetHome_QNAME = new QName("http://service.ejb.smarthouse/", "getHome");
    private final static QName _Exception_QNAME = new QName("http://service.ejb.smarthouse/", "Exception");
    private final static QName _Login_QNAME = new QName("http://service.ejb.smarthouse/", "login");
    private final static QName _Ping_QNAME = new QName("http://service.ejb.smarthouse/", "ping");
    private final static QName _PingResponse_QNAME = new QName("http://service.ejb.smarthouse/", "pingResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://service.ejb.smarthouse/", "loginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: smarthouse.ejb.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link Ping }
     * 
     */
    public Ping createPing() {
        return new Ping();
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ping }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "ping")
    public JAXBElement<Ping> createPing(Ping value) {
        return new JAXBElement<Ping>(_Ping_QNAME, Ping.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "pingResponse")
    public JAXBElement<PingResponse> createPingResponse(PingResponse value) {
        return new JAXBElement<PingResponse>(_PingResponse_QNAME, PingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }
    
    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ExecuteAction }
     * 
     */
    public ExecuteAction createExecuteAction() {
        return new ExecuteAction();
    }

    /**
     * Create an instance of {@link ExecuteActionResponse }
     * 
     */
    public ExecuteActionResponse createExecuteActionResponse() {
        return new ExecuteActionResponse();
    }

    /**
     * Create an instance of {@link ActionResponse }
     * 
     */
    public ActionResponse createActionResponse() {
        return new ActionResponse();
    }

    /**
     * Create an instance of {@link ActionRequest }
     * 
     */
    public ActionRequest createActionRequest() {
        return new ActionRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteAction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "executeAction")
    public JAXBElement<ExecuteAction> createExecuteAction(ExecuteAction value) {
        return new JAXBElement<ExecuteAction>(_ExecuteAction_QNAME, ExecuteAction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteActionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "executeActionResponse")
    public JAXBElement<ExecuteActionResponse> createExecuteActionResponse(ExecuteActionResponse value) {
        return new JAXBElement<ExecuteActionResponse>(_ExecuteActionResponse_QNAME, ExecuteActionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link GetHome }
     * 
     */
    public GetHome createGetHome() {
        return new GetHome();
    }

    /**
     * Create an instance of {@link GetHomeResponse }
     * 
     */
    public GetHomeResponse createGetHomeResponse() {
        return new GetHomeResponse();
    }

    /**
     * Create an instance of {@link GetEquipmentInfos }
     * 
     */
    public GetEquipmentInfos createGetEquipmentInfos() {
        return new GetEquipmentInfos();
    }

    /**
     * Create an instance of {@link GetEquipmentInfosResponse }
     * 
     */
    public GetEquipmentInfosResponse createGetEquipmentInfosResponse() {
        return new GetEquipmentInfosResponse();
    }

    /**
     * Create an instance of {@link RuntimeActionDef }
     * 
     */
    public RuntimeActionDef createRuntimeActionDef() {
        return new RuntimeActionDef();
    }

    /**
     * Create an instance of {@link Home }
     * 
     */
    public Home createHome() {
        return new Home();
    }

    /**
     * Create an instance of {@link Task }
     * 
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link Equipment }
     * 
     */
    public Equipment createEquipment() {
        return new Equipment();
    }

    /**
     * Create an instance of {@link DriverIdentifier }
     * 
     */
    public DriverIdentifier createDriverIdentifier() {
        return new DriverIdentifier();
    }

    /**
     * Create an instance of {@link Area }
     * 
     */
    public Area createArea() {
        return new Area();
    }

    /**
     * Create an instance of {@link Scenario }
     * 
     */
    public Scenario createScenario() {
        return new Scenario();
    }

    /**
     * Create an instance of {@link Timer }
     * 
     */
    public Timer createTimer() {
        return new Timer();
    }

    /**
     * Create an instance of {@link Action }
     * 
     */
    public Action createAction() {
        return new Action();
    }

    /**
     * Create an instance of {@link RuntimeEquipmentInfos }
     * 
     */
    public RuntimeEquipmentInfos createRuntimeEquipmentInfos() {
        return new RuntimeEquipmentInfos();
    }

    /**
     * Create an instance of {@link RuntimeParamDef }
     * 
     */
    public RuntimeParamDef createRuntimeParamDef() {
        return new RuntimeParamDef();
    }

    /**
     * Create an instance of {@link Room }
     * 
     */
    public Room createRoom() {
        return new Room();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEquipmentInfos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "getEquipmentInfos")
    public JAXBElement<GetEquipmentInfos> createGetEquipmentInfos(GetEquipmentInfos value) {
        return new JAXBElement<GetEquipmentInfos>(_GetEquipmentInfos_QNAME, GetEquipmentInfos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHomeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "getHomeResponse")
    public JAXBElement<GetHomeResponse> createGetHomeResponse(GetHomeResponse value) {
        return new JAXBElement<GetHomeResponse>(_GetHomeResponse_QNAME, GetHomeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEquipmentInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "getEquipmentInfosResponse")
    public JAXBElement<GetEquipmentInfosResponse> createGetEquipmentInfosResponse(GetEquipmentInfosResponse value) {
        return new JAXBElement<GetEquipmentInfosResponse>(_GetEquipmentInfosResponse_QNAME, GetEquipmentInfosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHome }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ejb.smarthouse/", name = "getHome")
    public JAXBElement<GetHome> createGetHome(GetHome value) {
        return new JAXBElement<GetHome>(_GetHome_QNAME, GetHome.class, null, value);
    }


}
