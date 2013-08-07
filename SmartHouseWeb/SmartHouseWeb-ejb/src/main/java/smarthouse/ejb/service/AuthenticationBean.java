package smarthouse.ejb.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.entity.third.User;
import smarthouse.ejb.service.remote.Authentication;
import smarthouse.ejb.util.CryptUtil;

@Stateless
@WebService(name="Authentication")
public class AuthenticationBean implements Authentication {

	private Logger logger = LoggerFactory.getLogger(AuthenticationBean.class);
	private DAOFactory daoFactory = DAOFactory.getFactory();
	
	@Resource
	private WebServiceContext wsContext;

	public String ping() {
		logger.info("Ping on SmartHouse system.");
		return "PONG";
	}
	
	public String login(String username, String password) {
		
		/*if(wsContext != null) {
			HttpServletRequest request = (HttpServletRequest) wsContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
			logger.info("External login with " + username+" from "+request.getRemoteAddr());
		}
		else {
			logger.info("Internal login with " + username);
		}*/
		
		try {
			User user = daoFactory.getUserDAO().findByUsername(username);
			
			
			// L'utilisateur existe
			// L'utilisateur a un mot de passe
			// Le mot de passe correspond
			return (user != null && user.getPassword() != null
					&& user.getPassword().equals(CryptUtil.sha1(password))) ? "true" : "false";
		}
		catch(PersistenceException e) {
			return "false";
		}
		
	}

}
