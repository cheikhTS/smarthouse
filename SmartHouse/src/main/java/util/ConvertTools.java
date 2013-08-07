package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ConvertTools {

	public static ArrayList<Integer> str2IntArray(String str){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		String [] values = str.split(";");
		for (String s : values){
			try{
				ret.add(Integer.parseInt(s));
			}catch(Exception e ){
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public static String intArray2str(ArrayList<Integer> arr){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();
		for (int i : ret){
			sb.append(i+";");
		}
		return sb.toString();
	}
	
	public static String properties2Str (Properties prop){
		StringBuilder ret  = new StringBuilder();
		for (Entry<Object, Object> e : prop.entrySet()){
			ret.append(e.getKey() + "=" + e.getValue() +";");
		}
		return ret.toString();
	}
	
	public static Properties str2Properties (String str){
		Properties ret  = new Properties();
		String [] values = str.split(";");
		for (String value : values){
			if (!value.trim().isEmpty()){
				String [] pair = value.split("=");
				if ( pair.length == 2)
					ret.put(pair[0], pair[1]);
			}			
		}
		return ret;
	}
	
}
