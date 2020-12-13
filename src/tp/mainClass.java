package tp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.type.TypeReference;


public class mainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSONParser jsonparser=new JSONParser();
		FileReader reader;
		try {
			reader = new FileReader("example.json");
			Object obj=jsonparser.parse(reader);
			JSONObject empjsonobj=(JSONObject)obj;
			
			Map<String,Object> map1=toMap(empjsonobj);
			for (String key : map1.keySet()) {
				   System.out.println(key + " : " + map1.get(key).toString());
				}
			
			System.out.println("\n\n");
			/*Set set=map1.entrySet();//Converting to Set so that we can traverse  
		    Iterator itr=set.iterator();  
		    while(itr.hasNext()){  
		        //Converting to Map.Entry so that we can get key and value separately  
		        Map.Entry entry=(Map.Entry)itr.next();  
		        
		    }  */
			List<String> keys = new ArrayList<>(map1.keySet());
			List<Object> values = new ArrayList<>(map1.values());
			
			System.out.println("keys are="+keys);
			System.out.println("values are="+values);
			
			System.out.println(map1.get("Name"));
			
			//JSONObject mainObject = new JSONObject(j);

			
			
			//decoder(keys,values);
			//System.out.println(values);
			//value iterator
			/*Iterator itr=values.iterator();
			while(itr.hasNext()) {
				Object str=(Object) itr.next();
				System.out.println(str);
				System.out.println(str instanceof List);
				}*/
			//decodevalues(values);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void decoder(List<String> keys, List<Object> values) {
		// TODO Auto-generated method stub
		String key;
		String value;
		Map<String,String> newlist=new HashMap<String,String>();
		for(int i=0;i<keys.size();i++) {
			
			if((values.get(i) instanceof List)) {
				Object str=(Object)values.get(i);
				onlyforarraylist(str);
				//break;
			}
			else {
				key=keys.get(i);
				 value=values.get(i).toString();
				newlist.put(key, value);
			}
		}
		System.out.println("finally newlist---"+newlist);
	}
	
	private static void onlyforarraylist(Object str) {
		List<Object> arraylist=new ArrayList<Object>();
		arraylist.add(str);
		System.out.println(arraylist);
	}

	public static void decodevalues(List l){
		List<Object> list1=l;
		Iterator itr=l.iterator();
		while(itr.hasNext()) {
			Object str=(Object) itr.next();
			System.out.println(str);
			//System.out.println(str instanceof List);
			}
		//return null;
	}
	public static Map<String, Object> toMap(JSONObject jsonobj) {
        Map<String, Object> map = new TreeMap<String, Object>();
        Set itr = jsonobj.keySet();
        //System.out.println(itr);
        Iterator keys=itr.iterator();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }   
            map.put(key, value);
        }   return map;
    }

    public static List<Object> toList(JSONArray array){
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }   return list;
}

		

}


