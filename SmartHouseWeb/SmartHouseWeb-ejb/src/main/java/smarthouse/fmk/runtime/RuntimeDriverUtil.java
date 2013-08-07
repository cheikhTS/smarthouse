package smarthouse.fmk.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.driver.definition.DefAction;
import smarthouse.fmk.driver.definition.DefParam;
import smarthouse.fmk.driver.definition.DefPropertiesRequired;
import smarthouse.fmk.driver.definition.types.DefTypeLimitedFloat;
import smarthouse.fmk.driver.definition.types.DefTypeLimitedInteger;
import smarthouse.fmk.driver.definition.types.DefTypeLimitedString;
import smarthouse.fmk.driver.definition.types.DefTypeMultiValues;
import smarthouse.fmk.runtime.definition.RuntimeActionDef;
import smarthouse.fmk.runtime.definition.RuntimeEquipmentInfos;
import smarthouse.fmk.runtime.definition.RuntimeParamDef;
import smarthouse.fmk.runtime.pilot.ActionRequest;
import smarthouse.fmk.runtime.pilot.ActionResponse;
import smarthouse.fmk.runtime.pilot.ActionResponse.RequestResult;

/**
 * Utilitaire destiné à la manipulation des drivers instanciés
 */
public class RuntimeDriverUtil {

	private static Logger logger = LoggerFactory.getLogger(RuntimeDriverUtil.class);

	/**
	 * Permet de récupérer la liste des actions pouvant être invoquées sur un
	 * équipement par son driver.
	 * 
	 * @param driver
	 *            Driver à traiter
	 * @return Liste de Method
	 */
	public static List<Method> getActionsAvailable(Driver driver) {
		List<Method> listEquipmentActions = new ArrayList<Method>(5);

		if (driver != null) {
			for (Method method : driver.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(DefAction.class)) {
					listEquipmentActions.add(method);
				}
			}
		} 
		else {
			logger.debug("Impossible de recuperer le driver");
		}

		return listEquipmentActions;
	}

	/**
	 * Permet de connaître les clés requises dans le properties passé à
	 * l'instanciation du driver
	 * 
	 * @param driverClass
	 *            Classe du driver à traiter
	 * @return Liste des clés
	 */
	public static List<String> getPropertiesRequired(Class<?> driverClass) {
		DefPropertiesRequired annotation = driverClass.getAnnotation(DefPropertiesRequired.class);
		
		if (annotation != null) {
			return Arrays.asList(annotation.value());
		}
		else {
			return new LinkedList<String>();
		}
	}
	
	/**
	 * Retourne la definition d'un équipement afin de l'utiliser.
	 * L'objet retourné permet de d'adapter une IHM en fontion des actions disponibles.
	 * @param eqp Equipement à traiter
	 * @return Bundle d'informations
	 */
	public static RuntimeEquipmentInfos makeRuntimeEquipmentInfos(Equipment eqp) throws DriverException {
		Driver driver = eqp.getDriver();
		if(driver == null) {
			throw new DriverException("Equipment "+eqp.getId()+"-"+eqp.getName()+" has no driver instance !");
		}
		
		RuntimeEquipmentInfos infos = new RuntimeEquipmentInfos();
		infos.setId(eqp.getId());
		infos.setName(eqp.getName());
		infos.setConfig(eqp.getProperties());
		infos.setState(driver.getState());
		infos.setActions(makeRuntimeActionDefs(driver));
		infos.setHardwareDescription(driver.getHardwareDescription());
		infos.setHardwareIdentifier(driver.getHardwareIdentifier());
		infos.setHardwareManufacturer(driver.getHardwareManufacturer());
		infos.setHardwareName(driver.getHardwareName());
		infos.setDriverVersion(driver.getDriverVersion());
		
		return infos;
	}

	/**
	 * Retourne la liste des actions disponibles pour le driver donné.
	 * @param driver
	 * @return
	 * @throws DriverException
	 */
	private static RuntimeActionDef[] makeRuntimeActionDefs(Driver driver) throws DriverException {
		// Liste des actions
		List<RuntimeActionDef> listRuntimeActions = new LinkedList<RuntimeActionDef>();
		
		for (Method method : driver.getClass().getDeclaredMethods()) {

			// C'est une action à publier à l'IHM
			if (method.isAnnotationPresent(DefAction.class)) {
				DefAction defAction = method.getAnnotation(DefAction.class);
				logger.debug("Action "+defAction.name()+" found (@DefAction).");				
				
				// Remplissage de notre container de définition de l'action
				RuntimeActionDef runtimeAction = new RuntimeActionDef();
				runtimeAction.setActionName(method.getName());
				runtimeAction.setName(defAction.name());
				runtimeAction.setDescription(defAction.description());
				runtimeAction.setParams(new RuntimeParamDef[method.getParameterTypes().length]);
				
				// Itération sur les annotations des paramètres
				Annotation[][] params = method.getParameterAnnotations();
				for(int i = 0; i < method.getParameterAnnotations().length; i++) {
					// Remplissage de notre container de définition de paramètre
					RuntimeParamDef runtimeParam = new RuntimeParamDef();
					
					// On va garder la ref de la définition pour la suite
					DefParam defParam = null;
					
					// Récupération de la définition du paramètre
					for(Annotation annotation : params[i]) {
						if(annotation.annotationType() == DefParam.class) {
							defParam = (DefParam) annotation;
							break;
						}
					}
					
					// Récupération de la définition du type
					if(defParam != null) {
						logger.debug("@DefParam found");
						
						// Affectation des valeurs
						runtimeParam.setName(defParam.name());
						runtimeParam.setDescription(defParam.description());
						runtimeParam.setType(defParam.type().name());
						
						boolean typeFound = false;
						for(Annotation annotation : params[i]) {

							if(annotation.annotationType() == DefTypeMultiValues.class) {
								DefTypeMultiValues defType = (DefTypeMultiValues) annotation;
								runtimeParam.setLabels(defType.labels());
								runtimeParam.setValues(defType.values());
								
								typeFound = true;
								break;
							}
							else if(annotation.annotationType() == DefTypeLimitedString.class) {
								DefTypeLimitedString defType = (DefTypeLimitedString) annotation;
								runtimeParam.setMin(defType.minSize());
								runtimeParam.setMax(defType.maxSize());
								typeFound = true;
								break;
							}
							else if(annotation.annotationType() == DefTypeLimitedInteger.class) {
								DefTypeLimitedInteger defType = (DefTypeLimitedInteger) annotation;
								runtimeParam.setMin(defType.min());
								runtimeParam.setMax(defType.max());
								typeFound = true;
								break;
							}
							else if(annotation.annotationType() == DefTypeLimitedFloat.class) {
								DefTypeLimitedFloat defType = (DefTypeLimitedFloat) annotation;
								runtimeParam.setFloatMin(defType.min());
								runtimeParam.setFloatMax(defType.max());
								runtimeParam.setStep(defType.step());
								typeFound = true;
								break;
							}
						}
						
						// On a bien trouvé l'annotation pour le type du paramètre
						if(typeFound) {
							runtimeAction.setParam(runtimeParam, i);
						}
						else {
							logger.warn("Invalid action (param type not found) in driver "+driver.getClass().getSimpleName()+" : "+method);
						}
					}
					else {
						logger.warn("Invalid action (param definition not found) in driver "+driver.getClass().getSimpleName()+" : "+method);
					}
				}
				
				// Ajout de l'action à notre liste
				listRuntimeActions.add(runtimeAction);
				logger.debug("Action "+runtimeAction.getActionName()+" referenced");
			}
			
		}
		
		return listRuntimeActions.toArray(new RuntimeActionDef[0]);
	}
	
	/**
	 * Fonction d'exécution d'une action sur une équipement instancié
	 * par son driver.
	 * @param request Requête encapsulant l'action à exécuter
	 * @return Réponse encapsulant le résultat et son état de l'exécution 
	 * @throws DriverException
	 */
	public static ActionResponse executeAction(ActionRequest request) {
		ActionResponse response = new ActionResponse();
		
		// factory de l'équipement
		EquipmentDAO equipmentDao = DAOFactory.getFactory().getEquipmentDAO();
		
		Equipment equipment =  null;
		try {
			equipment = equipmentDao.find(request.getIdEquipment());
			
			// Cas équipement qui n'existe pas
			if(equipment == null) {
				logger.error("Action on unexisting equipment "+request.getIdEquipment());
				response.setResult(ActionResponse.RequestResult.ERR_EQUIPMENT_NOT_FOUND.name());
				return response;
			}
		}
		catch(PersistenceException e) {
			// Cas pb bdd
			logger.error("Error while fetching equipment "+request.getIdEquipment());
			response.setResult(ActionResponse.RequestResult.ERR_SERVER.name());
			return response;
		}
		
		Driver driver = equipment.getDriver();
		if(driver == null) {
			logger.error("Driver not instancied on equipment "+request.getIdEquipment());
			response.setResult(ActionResponse.RequestResult.ERR_DRIVER.name());
			return response;
		}
		
		// Recherche de l'action ciblée
		Method actionMethod = null;
		for(Method m : driver.getClass().getMethods()) {
			// Match sur nom
			if(m.getName().equalsIgnoreCase(request.getAction()) 
				// ça correspond niveua nbre de param
				&& m.getParameterTypes().length == (request.getParams() != null ? request.getParams().size() : 0)
				// c'est une action
				&& m.getAnnotation(DefAction.class) != null) {

				// on note
				actionMethod = m;
				break;
			}
		}
		
		if(actionMethod == null)  {
			logger.error("Action request on unexisting method "+request.getAction()+" with "+request.getParams().size()+" parameters");
			response.setResult(RequestResult.ERR_ACTION_NOT_FOUND.name());
			return response;
		}
		
		// conversion des entrées par rapport au paramètres attendus
		Object[] paramsMethod = actionMethod.getParameterTypes();
		Object[] paramsConverted = new Object[paramsMethod.length];
		
		try {
			for(int i = 0; i < paramsMethod.length; i++) {
				String param = request.getParams().get(i);
				
				if(paramsMethod[i] == String.class) {
					paramsConverted[i] = param;
				}
				else if(paramsMethod[i] ==  Integer.class) {
					paramsConverted[i] = Integer.valueOf(param);
				}
				else if(paramsMethod[i] == Float.class) {
					paramsConverted[i] = Float.valueOf(param);
				}
				else if(paramsMethod[i] == Boolean.class) {
					paramsConverted[i] = Boolean.valueOf(param);
				}
				else {
					logger.error("Parameter type "+paramsMethod[i]+" is not supported, action aborted !!");
					response.setResult(RequestResult.ERR_DRIVER.name());
					return response;
				}
			}
		}
		catch(NumberFormatException e) {
			logger.error("Error while converting parameters : "+e.getClass().getSimpleName()+" / "+e.getMessage());
			response.setResult(RequestResult.ERR_INVALID_PARAMS.name());
			return response;
		}
		
		// Execution
		Object resultValue = null;
		try {
			resultValue = actionMethod.invoke(driver, paramsConverted);
		} 
		catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException while invoking action method "+actionMethod);
			response.setResult(RequestResult.ERR_INVALID_PARAMS.name());
			return response;
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getClass().getSimpleName()+" while invoking action method "+actionMethod+" : "+e.getMessage());
			response.setResult(RequestResult.ERR_ON_EXECUTE.name());
			return response;
		}
		
		// fill response wrapper
		response.setResult(RequestResult.OK.name());
		response.setValue((resultValue == null) ? "" : resultValue.toString());
		
		return response;
	}
	
}
