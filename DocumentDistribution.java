package json.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DocumentDistribution{

		public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
	        String content = new String(Files.readAllBytes(Paths.get(filename)));
	        return new JSONObject(content);
	    }
		
		public static void statusWiseDocumentCount(String filename) throws JSONException, IOException {
				
			int cntRsolvedStatDoc = 0;
	        int cntValidatedStatDoc = 0;
	        
	        JSONObject jsonObject = parseJSONFile(filename);
	        
	        JSONObject payload = jsonObject.getJSONObject("payload");
	        
	        JSONArray itemArray = payload.getJSONArray("items");
	        
	        for (int i = 0; i < itemArray.length(); ++i) {
	        	
	        	JSONObject record = itemArray.getJSONObject(i);
	            String status = record.getString("status");
	            
	            if(status.equals("REOPENED"))
	            {
	            	cntRsolvedStatDoc = cntRsolvedStatDoc + 1;
	            }
	            else if (status.equals("VALIDATED"))
	            {
	            	cntValidatedStatDoc = cntValidatedStatDoc + 1;
	            } 
	        }
	        System.out.print("REOPENED state Documents: " +cntRsolvedStatDoc + "\n" );
	        
            System.out.print("VALIDATED state Documents: " +cntValidatedStatDoc);
                      	
		}
		
		public static Map<String, String> getDocumentDetail(String filename, String expStatus) throws JSONException, IOException {
			JSONObject jsonObject = parseJSONFile(filename);
			Map<String,String>map = new HashMap<>();
	        JSONObject payload = jsonObject.getJSONObject("payload");
	        
	        JSONArray itemArray = payload.getJSONArray("items");
	        
	        for (int i = 0; i < itemArray.length(); ++i) {
	        	
	        JSONObject rec = itemArray.getJSONObject(i);
	        String status = rec.getString("status");
            if(status.equals(expStatus))
            {            			        
		        {	        	 
		        	map.put("document_id", rec.getString("document_id"));
		        	map.put("collection_id", rec.getString("collection_id"));	
		        	map.put(" file_name", rec.getString("file_name"));	
		        	map.put(" created_date", rec.getString("created_date"));
		        	//map.put(" revision_number", rec.getString("revision_number"));
				}
            } 
	 }
			return map ;			
  }
		
		public static Map<String, String> getDocumentDetail_File(String filename) throws JSONException, IOException {
			JSONObject jsonObject = parseJSONFile(filename);
			Map<String,String>map = new HashMap<>();
	        JSONObject payload = jsonObject.getJSONObject("payload");
	        
	        JSONArray itemArray = payload.getJSONArray("items");
	        
	        for (int i = 0; i < itemArray.length(); ++i) {
	        	
	        JSONObject rec = itemArray.getJSONObject(i);        			        
		        {	        	
		        	map.put("document_id", rec.getString("document_id"));
		        	map.put("collection_id", rec.getString("collection_id"));	
		        	map.put(" file_name", rec.getString("file_name"));	
		        	map.put(" created_date", rec.getString("created_date"));
		        	map.put(" revision_number", (rec.get("revision_number").toString()));
				}
            } 
			return map ;
		}	
	        
         public static void main(String[] args) throws IOException, JSONException {
        	String filename = "D:\\response.json";
	        statusWiseDocumentCount(filename);
	        getDocumentDetail(filename, "REOPENED");
	        getDocumentDetail_File(filename); 
         }
}
        
