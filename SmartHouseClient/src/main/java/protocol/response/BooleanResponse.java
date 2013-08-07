/**
 * 
 */
package protocol.response;

/**
 * @author Florent
 * 
 */
public class BooleanResponse implements Response {
	boolean response = false;

	public BooleanResponse ( boolean response ) {
		this.response = response;
	}

	public boolean getResponse() {
		return response;
	}
}
