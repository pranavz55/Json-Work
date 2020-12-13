package tp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class finalclass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		JSONParser jsonparser1=new JSONParser();
		FileReader reader1;
		JSONParser jsonparser2=new JSONParser();
		FileReader reader2;
		
		try {
			//parsing of first .json file
			reader1 = new FileReader("example.json");
			Object obj1=jsonparser1.parse(reader1);
			JSONObject empjsonobj1=(JSONObject)obj1;
			Map<String,Object> map1=toMap(empjsonobj1);
			
			//parsing of second .json file
			reader2 = new FileReader("demo.json");
			Object obj2=jsonparser2.parse(reader2);
			JSONObject empjsonobj2=(JSONObject)obj2;
			
			Map<String,Object> map2=toMap(empjsonobj2);
			
			
			//System.out.println(map1.get("Name"));
			//System.out.println(map2.get("id"));
			
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			engine.eval(new FileReader("javascriptfunc.js"));
			Invocable invocable = (Invocable) engine;
			Object result;
			result = invocable.invokeFunction("concatname", map1.get("firstname"),map2.get("lastname"));
			
			//System.out.println(result);
			
			//mapping for 
			ObjectMapper mapperObject = new ObjectMapper();
			Map<String,Object> resultmap=new TreeMap<String,Object>();
			resultmap.put("concatname", result = invocable.invokeFunction("concatname", map1.get("firstname"),map2.get("lastname")));
			mapperObject.writeValue(new File("updateddata.json"), resultmap);
			System.out.println("Done");
			
			System.out.println(resultmap.get("concatname"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
