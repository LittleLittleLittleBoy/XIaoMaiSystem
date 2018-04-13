package cn.edu.nju.candleflame.tickets.util;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

    public static String toJson(HashMap<String,String> map){
        JSONObject jsonObject = new JSONObject();
        Set<String> set=map.keySet();
        for (String key : set) {
            jsonObject.put(key,map.get(key));
        }

        return jsonObject.toString();
    }

    public static Map<String,String> fromString(String string){
        JSONObject jsonObject=new JSONObject(string);

        Set<String> set=jsonObject.keySet();

        HashMap<String,String> result=new HashMap<>();

        for (String key:set){
            result.put(key,jsonObject.getString(key));
        }
        return result;
    }
}
