

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonParseTest {

    public static void main(String ar[]) throws Exception{
//        String url = "http://localhost:8081/member/list";
//    	String url="http://localhost:8081/menu/list";
//    	String url = "http://localhost:8081/meal/list";
    	String url = "http://localhost:8081/order/list";
    	
        JSONObject json = readJsonFromUrl(url);

        System.out.println(json.toString());
        JSONArray dataArray = (JSONArray)json.get("data");
        
        for(int i=0;i<dataArray.size();i++) {
        	JSONObject obj = (JSONObject) dataArray.get(i);
        	 System.out.println(obj.get("orderNo"));
        	 System.out.println(obj.get("menuNo"));
        }
       
        System.out.println(json.get("code"));
    }

    private static String jsonReadAll(Reader reader) throws IOException{
        StringBuilder sb = new StringBuilder();

        int cp;
        while((cp = reader.read()) != -1){
            sb.append((char) cp);
        }

        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException,JSONException{
        InputStream is = new URL(url).openStream();

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = jsonReadAll(br);
//            System.out.println(jsonText);
            JSONObject json = new JSONObject();
            JSONParser parser = new JSONParser();
            try {
            	Object obj = parser.parse(jsonText);
            	json = (JSONObject)obj;
            }catch(ParseException e) {
            	e.printStackTrace();
            }
            return json;
        } finally {
            is.close();
        }
    }
}