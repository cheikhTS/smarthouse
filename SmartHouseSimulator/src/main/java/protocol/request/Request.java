package protocol.request;

public class Request {
	private String idDestination;
	private String action;
	private Object data;

	public Request ( String idDestination, String action, Object data ) {
		this.idDestination = idDestination;
		this.action = action;
		this.data = data;
	}

	public String getIdDestination() {
		return idDestination;
	}

	public String getAction() {
		return action;
	}

	public Object getData() {
		return data;
	}

}
