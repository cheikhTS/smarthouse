package smarthouse.android.utils;

import java.io.Serializable;

public class PairSerializable<F, S> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private F first;
	private S second;

	public PairSerializable ( F _first, S _second ) {
		setFirst(_first);
		setSecond(_second);
		// TODO Auto-generated constructor stub
	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "PairSerializable [first=" + first + ", second=" + second + "]";
	}

}
