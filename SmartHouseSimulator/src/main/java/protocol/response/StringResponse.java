/**
 * 
 */
package protocol.response;

/**
 * @author Florent
 * 
 */
public class StringResponse implements Response {
	String response = "";

	public StringResponse ( String response ) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}
}
