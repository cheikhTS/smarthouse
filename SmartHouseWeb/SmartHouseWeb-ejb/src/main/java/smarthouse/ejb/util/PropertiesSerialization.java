package smarthouse.ejb.util;

import java.util.Enumeration;
import java.util.Properties;

public class PropertiesSerialization implements ObjectSerialization {

	@Override
	public Object serialize(Object toserialize) {
		StringBuilder ret = new StringBuilder();
		Properties prop = (Properties) toserialize;
		Enumeration<Object> enumKey = prop.keys();
		String key;
		String value;
		while (enumKey.hasMoreElements()) {
			key = enumKey.nextElement().toString();
			value = prop.get(key).toString();
			ret.append(key + "=" + value + ",");
		}
		ret.delete(ret.lastIndexOf(","), ret.length());
		return ret.toString();
	}

	@Override
	public Object deserialize(Object todeserialize) {
		Properties ret = new Properties();
		String propStr = (String) todeserialize;
		String[] keysandValues = propStr.split(",");
		for (String keyandvalue : keysandValues) {
			ret.put(keyandvalue.substring(0, keyandvalue.indexOf("=")),
					keyandvalue.substring(keyandvalue.indexOf("=") + 1));
		}
		return ret;
	}

}
