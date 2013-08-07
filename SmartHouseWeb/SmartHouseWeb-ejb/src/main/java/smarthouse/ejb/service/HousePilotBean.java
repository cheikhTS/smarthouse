package smarthouse.ejb.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.service.remote.HousePilot;
import smarthouse.fmk.runtime.RuntimeDriverUtil;
import smarthouse.fmk.runtime.pilot.ActionRequest;
import smarthouse.fmk.runtime.pilot.ActionResponse;

@Stateless
@WebService(name="HousePilot")
public class HousePilotBean implements HousePilot {
	
	private Logger logger = LoggerFactory.getLogger(HouseExplorerBean.class);
	
	@Resource
	private WebServiceContext wsContext;
	
	/**
	 * Méthode d'exécution d'une action sur un équipement
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public ActionResponse executeAction(ActionRequest actionRequest) throws Exception {
		if(wsContext != null) {
			HttpServletRequest request = (HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
			logger.info("External action request received from "+request.getRemoteAddr()+" : "+actionRequest);
		}
		else {
			logger.info("Internal action request received : "+actionRequest);
		}
		
		return RuntimeDriverUtil.executeAction(actionRequest);
	}
}
