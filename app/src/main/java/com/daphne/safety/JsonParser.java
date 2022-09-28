package com.daphne.safety;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String,String> parseJsonObject(JSONObject object){
        //Initialize Hash Map
        HashMap<String,String> datalist = new HashMap<>();
        try {
            //Get name from object
            String name = object.getString("name");
            //Get Latitude from object
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            //Get Longitude from object
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            //Put all value in Hash map
            datalist.put("name",name);
            datalist.put("lat",latitude);
            datalist.put("lng",longitude);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return hash map
        return datalist;
    }
    private List<HashMap<String,String>>parseJsonArray(JSONArray jsonArray){
    //Initialize hash map List
        List<HashMap<String,String>> dataList = new ArrayList<>();
        for( int i = 0; i<jsonArray.length();i++){
            try {
                //Initialize hash map
                HashMap<String,String> data = parseJsonObject((JSONObject)jsonArray.get(i));
                //Add data in hash map list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Return hash map list
        return dataList;

         }
         public List<HashMap<String,String>>parseResult(JSONObject object){
             //Initialize Json Array
             JSONArray jsonArray = null;
             //Get result array
             try {
                 jsonArray = object.getJSONArray ("results");
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             //Return array
             return parseJsonArray(jsonArray);

         }
}
