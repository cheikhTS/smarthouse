package smarthouse.android.webservices;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jinouts.xml.namespace.QName;

import smarthouse.ejb.service.ActionRequest;
import smarthouse.ejb.service.ActionResponse;
import smarthouse.ejb.service.Authentication;
import smarthouse.ejb.service.AuthenticationBeanService;
import smarthouse.ejb.service.Equipment;
import smarthouse.ejb.service.Home;
import smarthouse.ejb.service.HouseExplorer;
import smarthouse.ejb.service.HouseExplorerBeanService;
import smarthouse.ejb.service.HousePilot;
import smarthouse.ejb.service.HousePilotBeanService;
import smarthouse.ejb.service.RuntimeEquipmentInfos;
import android.os.AsyncTask;
import android.util.Log;

public class WSAccess {

	private final static String NAMESPACE = "http://service.ejb.smarthouse/";
	private String base_URL = "http://192.168.214.56:8080/SmartHouseWeb-ejb-1.0.0/";
	private final static QName SERVICE_AUTHENTICATION = new QName(NAMESPACE, "Authentication");
	private final static QName SERVICE_HOUSE = new QName(NAMESPACE, "HouseExplorer");
	private final static QName SERVICE_PILOT = new QName(NAMESPACE, "HousePilot");
	
	private static Map<String, WSAccess> wsaccessMap = new HashMap<String, WSAccess>();

	private WSAccess (String base_url) {
		base_URL = base_url;
	}

	public class HomeTask extends AsyncTask<Void, Void, Home>{
		private Exception except = null;
		
		@Override
		protected Home doInBackground(Void... params) {
			URL wsdlURL;
			try {
				wsdlURL = new URL(base_URL + "HouseExplorer?wsdl");
				HouseExplorerBeanService ss = new HouseExplorerBeanService(wsdlURL, SERVICE_HOUSE);
				HouseExplorer port = ss.getHouseExplorerPort();
				return port.getHome();
			} catch (Exception e) {
				setExcept(e);
				e.printStackTrace();
			}

			return null;
		}

		public Exception getExcept() {
			return except;
		}

		public void setExcept(Exception except) {
			this.except = except;
		}
	}

	public class EquipmentInfoTask extends AsyncTask<Equipment, Void, RuntimeEquipmentInfos>{
		private Exception except = null;
		
		@Override
		protected RuntimeEquipmentInfos doInBackground(Equipment... params) {
			URL wsdlURL;
			try {
				wsdlURL = new URL(base_URL + "HouseExplorer?wsdl");
				HouseExplorerBeanService ss = new HouseExplorerBeanService(wsdlURL, SERVICE_HOUSE);
				HouseExplorer port = ss.getHouseExplorerPort();
				return port.getEquipmentInfos(params[0].getId());
			} catch (Exception e) {
				except = e;
				e.printStackTrace();
			}

			return null;
		}

		public Exception getExcept() {
			return except;
		}

		public void setExcept(Exception except) {
			this.except = except;
		}
	}
	
	public class ExecuteActionTask extends AsyncTask<ActionRequest, Void, ActionResponse>{
		private Exception except = null;
		
		@Override
		protected ActionResponse doInBackground(ActionRequest... params) {
			URL wsdlURL;
			try {
				wsdlURL = new URL(base_URL + "HousePilot?wsdl");
				HousePilotBeanService ss = new HousePilotBeanService(wsdlURL, SERVICE_PILOT);
				HousePilot port = ss.getHousePilotPort();
				return port.executeAction(params[0]);
			} catch (Exception e) {
				except = e;
				e.printStackTrace();
			}

			return null;
		}

		public Exception getExcept() {
			return except;
		}

		public void setExcept(Exception except) {
			this.except = except;
		}
		
	}
	
	public class AuthenticationTask extends AsyncTask<String, Void, Boolean>{
		private Exception except = null;
		
		@Override
		protected Boolean doInBackground(String... params) {
			URL wsdlURL;
			try {
				wsdlURL = new URL(base_URL + "Authentication?wsdl");
				AuthenticationBeanService ss = new AuthenticationBeanService(wsdlURL, SERVICE_AUTHENTICATION);
				Authentication port = ss.getAuthenticationPort();
				String b = port.login(params[0], params[1]);
				return Boolean.parseBoolean(b);
			} catch (Exception e) {
				except = e;
				e.printStackTrace();
			}
			return null;
		}

		public Exception getExcept() {
			return except;
		}

		public void setExcept(Exception except) {
			this.except = except;
		}
		
	}
	
	public class PingTask extends AsyncTask<Void, Void, Boolean>{
		private Exception except = null;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			URL wsdlURL;
			try {
				wsdlURL = new URL(base_URL + "Authentication?wsdl");
				AuthenticationBeanService ss = new AuthenticationBeanService(wsdlURL, SERVICE_AUTHENTICATION);
				Authentication port = ss.getAuthenticationPort();
				String rep = port.ping();
				if ( rep.equalsIgnoreCase("pong") ) { return true; }
			} catch (Exception e) {
				except = e;
				e.printStackTrace();
			}
			return false;
		}

		public Exception getExcept() {
			return except;
		}

		public void setExcept(Exception except) {
			this.except = except;
		}
	}

	public static WSAccess getInstance(String base_url) {
		WSAccess ws = wsaccessMap.get(base_url);
        if (ws == null) {
        	ws = new WSAccess(base_url);
        	wsaccessMap.put(base_url, ws);
        }
		return ws;
	}

	public String getBase_URL() {
		return base_URL;
	}

	public void setBase_URL(String base_URL) {
		this.base_URL = base_URL;
	}

}
