package smarthouse.ejb.util;

public interface ObjectSerialization {

	public Object serialize(Object toserialize);

	public Object deserialize(Object todeserialize);
}
